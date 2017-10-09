package com.ds.zxm.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ds.zxm.mapper.LotteryDetailMapper;
import com.ds.zxm.mapper.LotteryMapper;
import com.ds.zxm.model.LotteryDO;
import com.ds.zxm.model.LotteryDetail;
import com.ds.zxm.model.LotteryDetailCondition;
import com.ds.zxm.model.LotteryparseInfo;
import com.ds.zxm.thread.LotteryQueueStarter;
import com.ds.zxm.thread.LotteryUpdateConsumer;
import com.ds.zxm.util.DateUtils;
import com.ds.zxm.util.HttpUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class LotteryService implements  ApplicationContextAware  {

    @Resource
    private LotteryDetailMapper lotteryDetailDAO;
    @Resource
    private LotteryQueueStarter lotteryQueueStarter;
    @Resource
    private LotteryUpdateConsumer lotteryUpdateQueue;
    public static final String swedishLotteryCode = "RDSSC";
    public static final String swedishLotteryLatestNoUrl = "https://www.swedishlottery.se/lotto/resulat/lottery/issue_info?lottery=SS";
    public static Executor executor = Executors.newCachedThreadPool();



    @Autowired
    private LotteryMapper lotteryMapper;

  /*  public Lottery getLotteryInfo(){
        Lottery lottery=lotteryMapper.findLotteryInfo();
        //User user=null;
        return lottery;
    }*/

     
    public void fetch2LatestFromMaxRecord() throws Exception {
        LotteryDetailCondition LotteryDetailCondition = new LotteryDetailCondition();
        List<LotteryDetail> list = lotteryDetailDAO.selectMax(LotteryDetailCondition);
        if(list != null && list.size() > 0) {
            LotteryDetail maxRecord  = list.get(0);
            this.fetchLotteryInfo(maxRecord.getLotteryCode(), maxRecord.getNo(), 3);
        }
    }

     
    public void saveLottery(LotteryDO lottery) throws Exception {
         lotteryMapper.insert(lottery);
    }

     
    public void fetchLotteryInfo(String lotteryCode, String no) throws Exception {

        Thread.sleep(100);
        LotteryDetailCondition LotteryDetailCondition = new LotteryDetailCondition();
        LotteryDetailCondition.createCriteria().andLotteryCodeEqualTo(lotteryCode)
                .andNoEqualTo(no);
        int cnt = lotteryDetailDAO.countByCondition(LotteryDetailCondition);
        if (cnt > 0) {
            System.out.println("跳过处理---" + lotteryCode + ":" + no);
            return;
        }

        LotteryDetail LotteryDetail = new LotteryDetail();
        String getUrl = "";
        if (swedishLotteryCode.equals(lotteryCode)) {
            try {
                getUrl = "https://www.swedishlottery.se/lotto/resulat/lottery/number?lottery=SS&issueNo=" + no;

                String lotteryInfo = HttpUtil.doGet(getUrl, null);
                Map<String, Object> parameter = JSON.parseObject(lotteryInfo, Map.class);

                if (1 == (int) parameter.get("code")) {
                    parameter.putAll(JSON.parseObject(parameter.get("result").toString(), Map.class));
                    if (null == parameter.get("openCode")) {
                        System.out.println(lotteryCode + ":" + no + "未找到开奖数据！");
                        return;
                    }
                    String openCode = parameter.get("openCode").toString();

                    LotteryDetail.setLotteryCode(lotteryCode);
                    LotteryDetail.setNo(no);

                    String[] numArray = openCode.split(",");
                    LotteryDetail.setNum1(numArray[numArray.length - 1]);
                    LotteryDetail.setNum2(numArray[numArray.length - 2]);
                    LotteryDetail.setNum3(numArray[numArray.length - 3]);
                    LotteryDetail.setNum4(numArray[numArray.length - 4]);
                    LotteryDetail.setNum5(numArray[numArray.length - 5]);

                    LotteryparseInfo lotteryParseInfo = this.getParseInfoFromNo(no);
                    LotteryDetail.setAliasNo(lotteryParseInfo.getAliasNo());
                    LotteryDetail.setLotteryDate(LotteryDetail.getAliasNo().substring(0, 8));

                    this.lotteryDetailSaveOnUnExist(LotteryDetail);
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        System.out.println("完成处理---" + lotteryCode + ":" + no);
    }

    public void lotteryDetailSaveOnUnExist(LotteryDetail detail) {
        LotteryDetailCondition LotteryDetailCondition = new LotteryDetailCondition();
        LotteryDetailCondition.createCriteria().andLotteryCodeEqualTo(detail.getLotteryCode())
                .andNoEqualTo(detail.getNo());
        int cnt = lotteryDetailDAO.countByCondition(LotteryDetailCondition);
        if (cnt == 0) {
            lotteryDetailDAO.insert(detail);
        }
    }

    /**
     * lotteryCode 彩种 no 取数开始期数
     */
    // type: 0 -- 只抓取当前no一期， 1 -- 抓取no当天从no到最后一期 2.按日期抓取 3 -- 从no开始抓取到最新开奖
    public void fetchLotteryInfo(String lotteryCode, String originNo, int type) throws Exception {

        LotteryparseInfo lotteryParseInfo = this.getParseInfoFromNo(originNo);
        switch (type) {
            case 0:
                this.fetchLotteryInfo(lotteryCode, originNo);
                break;
            case 1:
                this.fetchOneDayLottery(lotteryCode, lotteryParseInfo);
                break;
            case 2:
                break;
            case 3:
                fetchToLatestLottery(lotteryCode, lotteryParseInfo, this.fetchLatestLottery(lotteryCode));
                break;
            default:
                break;
        }

    }

    private LotteryparseInfo fetchLatestLottery(String lotteryCode) {
        String latestNo = null;
        if (swedishLotteryCode.equals(lotteryCode)) {
            String lotteryInfo = HttpUtil.doGet(swedishLotteryLatestNoUrl, null);
            Map<String, Object> parameter = JSON.parseObject(lotteryInfo, Map.class);
            JSONArray array = JSON.parseArray(parameter.get("result").toString());

            parameter.putAll(JSON.parseObject(array.get(0).toString(),  Map.class));
            latestNo = (String) parameter.get("issue");
        }
        return this.getParseInfoFromNo(latestNo);

    }

    /**
     * {"result":{"lotteryId":24,"issueNo":"21-06-2017-2402","index":423,"openCode":"0,5,4,4,8,6,5"},"code":1,"msg":"ok"}
     * https://www.swedishlottery.se/lotto/resulat/lottery/number?lottery=SS&issueNo=21-06-2017-0002
     *
     * @param no
     * @return
     */
    public static void main(String[] args) {
        // System.out.println(LotteryServiceImpl.getParseInfoFromNo("21-06-2017-0002").next().getUseNo());
        // LotteryparseInfo lotteryParseInfo =
        // LotteryServiceImpl.getParseInfoFromNo("21-06-2017-0002");

        // LotteryServiceImpl.queryOneDayNos(lotteryParseInfo);
    }

    private void fetchOneDayLottery(String lotteryCode, LotteryparseInfo lotteryParseInfo) throws Exception {
        LotteryparseInfo next = lotteryParseInfo.next();
        int curDay = lotteryParseInfo.getCurrentTime().getDay();
        if (next.getCurrentTime().getDay() == curDay) {
            // 每期的业务逻辑,取值错误时直接跳出递归
			/*LotteryDetail queueItem = new LotteryDetail();
			queueItem.setLotteryCode(lotteryCode);
			queueItem.setNo(next.getUseNo());
			LotteryUpdateConsumer.queue.put(queueItem);*/
            LotteryQueueStarter lotteryQueueStarter =(LotteryQueueStarter) act.getBean("LotteryQueueStarter");
            lotteryQueueStarter.setLotteryCode(lotteryCode);
            lotteryQueueStarter.setNo(next.getUseNo());
            executor.execute(lotteryQueueStarter);
            //this.fetchLotteryInfo(lotteryCode, next.getUseNo());
            System.out.println(next.getUseNo());
            fetchOneDayLottery(lotteryCode, lotteryParseInfo.next());
        }
    }

    private void fetchToLatestLottery(String lotteryCode, LotteryparseInfo lotteryParseInfo, LotteryparseInfo latest)
            throws Exception {
        LotteryparseInfo next = lotteryParseInfo.next();
        if (!next.equals(latest)) {
            // 每期的业务逻辑,取值错误时直接跳出递归
		/*	LotteryDetail queueItem = new LotteryDetail();
			queueItem.setLotteryCode(lotteryCode);
			queueItem.setNo(next.getUseNo());
			LotteryUpdateConsumer.queue.put(queueItem);*/
            LotteryQueueStarter lotteryQueueStarter =(LotteryQueueStarter) act.getBean("LotteryQueueStarter");
            lotteryQueueStarter.setLotteryCode(lotteryCode);
            lotteryQueueStarter.setNo(next.getUseNo());
            executor.execute(lotteryQueueStarter);
            //this.fetchLotteryInfo(lotteryCode, next.getUseNo());
            fetchToLatestLottery(lotteryCode, lotteryParseInfo.next(), latest);
        } else {
            // 防止由于取数据过长导致不是最新期数
            LotteryparseInfo info = this.fetchLatestLottery(lotteryCode);
            if (info.equals(latest)) {
                return;
            }
            fetchToLatestLottery(lotteryCode, lotteryParseInfo, info);
        }
    }

    private LotteryparseInfo getParseInfoFromNo(String no) {

        String[] nos = no.split("-");

        Date date = null;
        try {
            date = DateUtils.String2Date("" + appendDateStr(Integer.valueOf(nos[2]))
                    + appendDateStr(Integer.valueOf(nos[1])) + appendDateStr(Integer.valueOf(nos[0])) + "  "
                    + appendDateStr(Integer.valueOf(nos[3].substring(0, 2)))
                    + appendDateStr(Integer.valueOf(nos[3].substring(2))), "yyyyMMdd  HHmm");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new LotteryparseInfo(date);
    }

    private static String appendDateStr(int time) {
        return String.valueOf(time).length() == 1 ? "0" + time : String.valueOf(time);
    }
    ApplicationContext act;
     
    public void setApplicationContext(ApplicationContext act) throws BeansException {
        this.act = act;

    }
}
