package com.ds.zxm.service;

import com.alibaba.fastjson.JSON;
import com.ds.zxm.controller.LotteryController;
import com.ds.zxm.mapper.BetDAO;
import com.ds.zxm.mapper.BetRecordDAO;
import com.ds.zxm.model.*;
import com.ds.zxm.util.DsUtil;
import com.ds.zxm.util.HttpUtil;
import com.ds.zxm.util.LotteryUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BetService {

    @Autowired
    private BetDAO betDAO;
    @Autowired
    private BetRecordDAO betRecordDAO;
    HttpClient  httpClient = new HttpClient();
    org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(BetService.class);

    public  static  final String JSESSIONID = "F773BAD454F620420BB40292D4C0A570";
    public  static final String UUID = "f54f9769c3e0-8af4-4cad-889a-8c3976cf8cc8";

    public List<BetDO> queryBetList(BetDOCondition betDOCondition) {
        return betDAO.selectByExampleWithBLOBs(betDOCondition);
    }
    //查询当期curNo内的计划
    public List<BetDO> queryBetUnprizeInfo(BetDO betDO, String curNo) {
        BetDOCondition betDOCondition = new BetDOCondition();
        betDOCondition.createCriteria().andBetTypeEqualTo(betDO.getBetType()).andStartNoLessThanOrEqualTo(curNo)
                .andEndNoGreaterThanOrEqualTo(curNo).andLotteryCodeEqualTo(betDO.getLotteryCode()).andStatusEqualTo(betDO.getStatus())
                ;
        return betDAO.selectByExampleWithBLOBs(betDOCondition);
    }

    public void insert(BetDO bet) {
        betDAO.insert(bet);
    }
    public  static  void main(String[] args) {

    }

    public  String getCurNoAlias(String curNO) {
        String loadConf = query198conf();
        System.out.println(loadConf);
        String postConf = loadConf.replace("[", "").replace("]", "").replace("\"", "");
        String[] arr = postConf.split(",");
        if("0".equals(arr[0])){
            System.out.println(arr[1]);
            if( curNO.equals(arr[2])) {
                return arr[1];
            } else if( curNO.equals(arr[5])) {
                return arr[4];
            }
        }
        return  "";
    }
    public String query198conf() {
        String loginUrl = "";
        try {
            double random = Math.random();
            loginUrl = "http://www.198good.com:88/loadconf.do?p=" + URLEncoder.encode("[0]", "utf-8") + "&r=" + URLEncoder.encode("" + random, "utf-8");
            //loginUrl = URLEncoder.encode(loginUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

        // 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
        GetMethod postMethod = new GetMethod(loginUrl);
        postMethod.setRequestHeader("Origin", "http://www.198good.com:88");
        postMethod.setRequestHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        postMethod.setRequestHeader("X-Requested-With", "XMLHttpRequest");
        postMethod.setRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
        postMethod.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        postMethod.setRequestHeader("Host", "http://www.198good.com:88");

        postMethod.setRequestHeader("Referer", "http://www.198good.com:88/player/v5/order.jsp?g=CQSSC&t=SSC&u=backtoxcb8&o=3");
        postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");

        String session = "th=mountain; uuid=" + UUID + "; JSESSIONID=" + JSESSIONID + "; announceRead=; username=backtoxcb8";
        postMethod.setRequestHeader("Cookie", session);

        try {
            int loginConfStatusCode = httpClient.executeMethod(postMethod);
            System.out.print(postMethod.getResponseBodyAsString());
            return postMethod.getResponseBodyAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  "";


    }
    /**
     *
     * @param amtMode 厘分角元模式 -1,1,10,100
     * @param originNO 投注期号（需转义）
     * @param multi ： 倍数
     */
    public void betto198(String amtMode,String originNO,String multi, String nums) throws UnsupportedEncodingException {
        log.info("投注198："
            + "amtMode:" + amtMode
                + "originNO:" + originNO
                + "multi:" + multi
                + "nums:" + nums
        );
        String betNO = getCurNoAlias(originNO);
        String loginUrl = "http://www.198good.com:88/placeorder.do?p=1930&u=" + URLEncoder.encode(amtMode, "utf-8" ) + "&s=" + URLEncoder.encode(betNO, "utf-8" )
                + "&q=1&m=" + URLEncoder.encode(multi, "utf-8" ) + "&w=0&b=0&g=0&c=" + URLEncoder.encode("[" + multi + "]", "utf-8" );
        loginUrl = "http://www.198good.com:88/placeorder.do?p=1930&u=" + amtMode + "&s=" + betNO
                + "&q=1&m=" + multi + "&w=0&b=0&g=0&c=" +  URLEncoder.encode("[" + multi + "]", "utf-8" );
        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

        // 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
        PostMethod postMethod = new PostMethod(loginUrl);
        postMethod.setRequestHeader("Origin", "http://www.198good.com:88");
        postMethod.setRequestHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        postMethod.setRequestHeader("X-Requested-With", "XMLHttpRequest");
        postMethod.setRequestHeader("Accept-Encoding", "gzip, deflate");
        postMethod.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");

        postMethod.setRequestHeader("Referer", "http://www.198good.com:88/player/v5/order.jsp?g=CQSSC&t=SSC&u=backtoxcb8&o=3");
        postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");


        String session = "th=mountain; uuid=" + UUID + "; JSESSIONID="+JSESSIONID+"; announceRead=<40>,65,; username=backtoxcb8";
        postMethod.setRequestHeader("Cookie",session);

        // 设置登陆时要求的信息，用户名和密码
        /*NameValuePair[] data = { new NameValuePair("p", "[\"backtoxcb8\",\"230679zxm\",3]")};
        postMethod.setRequestBody(data);*/

        String postDst = processNumsData2Post(nums);
        RequestEntity entity = new StringRequestEntity(postDst, "textml", "utf-8");
        postMethod.setRequestEntity(entity);
        try {
            log.info(" request url:" + loginUrl);
            int loginConfStatusCode=httpClient.executeMethod(postMethod);
            log.info(" response :" + postMethod.getResponseBodyAsString() );
        } catch (IOException e) {
            log.error("betto198", e);
        }
    }

    //格式：2=61,1,1;1,1,2.    数目=玩法（1）;(2);(......).
    public String processNumsData2Post(String nums) {
        StringBuffer sb = new StringBuffer();
        int cnt = (nums.length() + 1)/4;
        sb.append(cnt + "=6");

        nums = nums.replace(" ", ";");
        char[] numArray = nums.toCharArray();
        for (int i = 0; i < numArray.length - 1; i++) {
            String s = String.valueOf(numArray[i]);
            if(!";".equals(s)) {
                sb.append( s + ",");
            } else {
                sb.append(";");
            }
        }

        return  (sb.toString() + numArray[numArray.length -1]).replace(",;", ";") + ".";
    }

    public int updateBetDO(BetDO bet) {
       return betDAO.updateByPrimaryKeySelective(bet);
    }

    /**
     * 暂时只支持一个方案
     *
     * @param caipiao
     * @throws ParseException
     */
    public void updateLotteryStatus(String caipiao) throws ParseException {
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("caipiao", caipiao);
            String result = HttpUtil.doPost("http://www.ds018.com/caipiao/kline/init", map, "utf-8", DsUtil.genRequestHeaderMap(caipiao));

            //更新彩票状态
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap = JSON.parseObject(result, Map.class);
            if ("0".equals(resultMap.get("ret").toString())) {
                String curNO = resultMap.get("peroid").toString();
                String prize = resultMap.get("prize").toString();

                BetDOCondition betDOCondition = new BetDOCondition();
                //查询所有status为1的记录
                betDOCondition.createCriteria().andBetTypeEqualTo("3").andLotteryCodeEqualTo(caipiao).andStatusEqualTo("1");
                List<BetDO> betDOList = queryBetList(betDOCondition);

                //BetDOCondition bdd = new BetDOCondition();
                for (BetDO item : betDOList) {
                    // bdd.createCriteria().andIdEqualTo(item.getId());
                    //匹配当期期是否中奖或者已经是最后一期
                    if ("3".equals(item.getBetType())) {
                        //历史记录置为2
                        if (LotteryUtil.compareCQAwardNO(item.getEndNo(), curNO) < 0
                                || LotteryUtil.compareCQAwardNO(item.getStartNo(), LotteryUtil.getNextAwardNo(curNO,caipiao)) > 0) {
                            item.setStatus("2");
                            updateBetDO(item);
                        } else {
                            //方案正在进行中
                            BetRecordDOCondition betRecordDOCondition = new BetRecordDOCondition();
                            betRecordDOCondition.createCriteria().andLotteryCodeEqualTo(caipiao).andBetNoEqualTo(curNO).andStatusEqualTo("1");
                            List<BetRecordDO> betRecordDOList = betRecordDAO.selectByCondition(betRecordDOCondition);
                            for (BetRecordDO betRecordDOItem : betRecordDOList) {
                                {
                                    if (item.getBetNo().indexOf(prize.substring(prize.length() - 3)) > 0) {
                                        item.setStatus("3");
                                        item.setPrizeNo(curNO);
                                        log.info(item.getSeqNo() + "已中奖");
                                        updateBetDO(item);

                                        BetRecordDO betRecordDO = new BetRecordDO();
                                        betRecordDO.setId(betRecordDOItem.getId());
                                        betRecordDO.setStatus("3");
                                        betRecordDAO.updateByPrimaryKeySelective(betRecordDO);
                                    } else {
                                        //投注记录更新成未中奖
                                        BetRecordDO betRecordDO = new BetRecordDO();
                                        betRecordDO.setId(betRecordDOItem.getId());
                                        betRecordDO.setStatus("2");
                                        betRecordDAO.updateByPrimaryKeySelective(betRecordDO);
                                        if (!curNO.equals(item.getEndNo())) {
                                            //投注下一期
                                            log.info(item.getSeqNo() + "(" + betRecordDOItem.getBetNo() + ")" + "未中奖");
                                            String nextNo = LotteryUtil.getNextAwardNo(curNO, caipiao);
                                            String curScheduleNo = betRecordDOItem.getScheduleNo();
                                            TradeSchedule tradeSchedule = LotteryController.scheMap.get(curScheduleNo);
                                            int nextscheNo = tradeSchedule.getLoseNo();
                                            TradeSchedule nextschedule = LotteryController.scheMap.get("" + nextscheNo);
                                            BetRecordDO betRecordDO2 = new BetRecordDO();
                                            betRecordDO2.setStatus("1");
                                            betRecordDO2.setSeqNo(betRecordDOItem.getSeqNo());
                                            betRecordDO2.setBetNo(nextNo);
                                            betRecordDO2.setBetWebsite(betRecordDOItem.getBetWebsite());
                                            betRecordDO2.setLotteryCode(betRecordDOItem.getLotteryCode());
                                            betRecordDO2.setCreateTime(new Date());
                                            betRecordDO2.setScheduleNo("" + nextscheNo);
                                            betRecordDAO.insert(betRecordDO2);

                                            String originNo = nextNo.substring(0, 8) + "-" + nextNo.substring(8);
                                            //betLottery(originNo, nextschedule.getMultiple(), item.getBetNo());
                                        } else {
                                            //跑完最后一期未中奖，更新方案状态
                                            log.info(item.getSeqNo() + "未中奖");
                                            item.setStatus("2");
                                            updateBetDO(item);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (ParseException e) {
            log.error("updateLotteryStatus error", e);
        }
    }

}
