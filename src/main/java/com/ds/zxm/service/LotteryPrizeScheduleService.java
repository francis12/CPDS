package com.ds.zxm.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ds.zxm.mapper.TCFFCPRIZEDAO;
import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TCFFCPRIZECondition;
import com.ds.zxm.model.TcffcPrizeConverter;
import com.ds.zxm.util.*;
import com.ds.zxm.vo.OnlineImVO;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class LotteryPrizeScheduleService{

    @Resource
    private TCFFCPRIZEDAO tcffcprizedao;
    @Resource
    private TcffcGenNumsService tcffcGenNumsService;
    public static Executor executor = Executors.newFixedThreadPool(1);
    Logger log = Logger.getLogger(LotteryPrizeScheduleService.class);

    private static int c1 = 0;
    private static int c2 = 0;

    public void fetchTcffcPrize() {
        String s;
        try {
            s = new SimpleDateFormat("HHmmss").format(new Date());
            String hh = s.substring(0, 2);
            String mm = s.substring(2, 4);
            String ss = s.substring(4, 6);
            int c = 0;
            c = Integer.parseInt(mm);
            int d = Integer.parseInt(hh);
            if (ss.equals("10")) {
                if (Integer.parseInt(ss) > 30) {
                    c = c + 1;
                }
                int result = pcqqOnline(c, d);
                while (result == 0) {
                    result = pcqqOnline(c, d);
                }
            }
        } catch (Exception e) {
            log.error("fetchTcffcPrize error", e);
        }

    }

    private int pcqqOnline(int c, int d) {
        HttpURLConnection conn = null;
        TCFFCPRIZE tcffcprize = null;
        int cha = 0;
        try {
            tcffcprize = new TCFFCPRIZE();
            tcffcprize.setLotteryCode("TCFFC");
            URL realUrl = new URL("http://mma.qq.com/cgi-bin/im/online&callback ");
            conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = in.readLine()) != null) {
                    buffer.append(line);
                }
                String result = buffer.toString();
                result = result.substring(12);
                result = result.substring(0, result.length() - 1);
                JSONObject jsonObject = JSONObject.parseObject(result);
                Integer curr = (Integer) jsonObject.get("c");
                int l = curr;
                int a[] = new int[9];
                int i = 0;
                int sum = 0;
                if (c % 2 == 0) {
                    c2 = l;
                } else {
                    c1 = l;
                }
                if (c % 2 == 0) {
                    cha = c2 - c1;
                } else {
                    cha = c1 - c2;
                }
                while (true) {
                    a[i] = l % 10;
                    sum = sum + a[i];
                    i++;
                    l = l / 10;
                    if (l / 10 == 0) {
                        a[i] = l;
                        sum = sum + a[i];
                        break;
                    }
                }
                //System.out.println(Arrays.toString(a));
                NumberFormat nf = NumberFormat.getNumberInstance();
                String s = nf.format(Long.parseLong(curr + ""));
                String qs = new SimpleDateFormat("yyyyMMdd").format(new Date());
                int min = d * 60 + c;
                String qs1 = "";
                if (min < 10) {
                    qs1 = "000" + min;
                } else if (min >= 10 && min < 100) {
                    qs1 = "00" + min;
                } else if (min >= 100 && min < 1000) {
                    qs1 = "0" + min;
                } else {
                    qs1 = min + "";
                }
                //log.info("开奖结果:" + sum % 10 + "," + a[3] + "," + a[2] + "," + a[1] + "," + a[0]);
                //log.info("期数:" + qs + "-" + qs1 + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " " + s + " " + (cha >= 0 ? "+" + cha : cha));

                String no = qs + "-" + qs1;
                int wan = sum % 10;
                int qian = a[3];
                int bai = a[2];
                int shi = a[1];
                int ge = a[0];
                int onlineNum = Integer.valueOf(s.replace(",", ""));
                int adjustNum = cha;
                String prize = "" + wan + qian + bai + shi + ge + "";
                tcffcprize.setNo(no);
                tcffcprize.setPrize(prize);
                tcffcprize.setWan(wan);
                tcffcprize.setQian(qian);
                tcffcprize.setBai(bai);
                tcffcprize.setShi(shi);
                tcffcprize.setGe(ge);
                tcffcprize.setOnlineNum(onlineNum);
                tcffcprize.setAdjustNum(adjustNum);
                try {
                    Date date = DateUtils.String2Date(DateUtils.date2String(new Date(), "yyyyMMdd"), "yyyyMMdd");
                    tcffcprize.setLotteryDate(date);
                } catch (ParseException e) {
                    log.error("日期转换错误", e);
                }
                tcffcprize.setTime(new Date());
                this.insertOnUnexist(tcffcprize);
            }
        } catch (IOException e) {
            log.error("连接超时，重新采集......", e);
            return 0;
        }
        return cha;
    }

    public static void main(String[] args) {
        //new LotteryPrizeScheduleService().fetchTcffcPrizeFrom77Org();
    }

    public void fetchTcffcPrizeFrom77Org() {
        String formatCurTimeStr="";
        try {
            String curTimeStr = DateUtils.date2String(getCurTime(), "yyyy-MM-dd HH:mm");
            formatCurTimeStr = DateUtils.date2String(DateUtils.String2Date(curTimeStr, "yyyy-MM-dd HH:mm"), "yyyy-MM-dd HH:mm:ss");
            while (!StringUtils.isEmpty(formatCurTimeStr)) {
                try {
                    TCFFCPRIZE parsedTcffcPrize = this.fetchExactTimePrize(formatCurTimeStr);
                    //正常获取到开奖号码
                    if (null != parsedTcffcPrize) {
                        log.info("第"+ parsedTcffcPrize.getNo() + "(" + formatCurTimeStr +")开奖号为：" + parsedTcffcPrize.getPrize());
                        this.insertOnUnexist(parsedTcffcPrize);
                        //notice出号中心
                        tcffcGenNumsService.noticeGenNumsService(parsedTcffcPrize);
                    } else {
                        log.error("第" + formatCurTimeStr + "期开奖数据获取失败!");
                    }
                } catch (Exception e) {
                    log.error("fetchExactTimePrize error, formatCurTimeStr:" + formatCurTimeStr, e);
                    log.error("第" + formatCurTimeStr + "期开奖数据获取失败,跳过!");
                }
                Date nextMin = DateUtils.addMinutes(1, DateUtils.String2Date(formatCurTimeStr, "yyyy-MM-dd HH:mm:ss"));
                formatCurTimeStr = DateUtils.date2String(nextMin,"yyyy-MM-dd HH:mm:ss" );

                Thread.sleep(5 *1000);
            }
        } catch (Exception e) {
            log.error("fetchTcffcPrizeFrom77Org error", e);
        }
    }
    private TCFFCPRIZE fetchExactTimePrize2(String formatCurTimeStr) throws Exception {
        List<OnlineImVO> list = RestClientProxyUtil.doGet("http://77tj.org/api/tencent/onlineim", List.class);

        list.stream().forEach(item -> {

        });

        return null;
    }
        //获取指定期
    private TCFFCPRIZE fetchExactTimePrize(String formatCurTimeStr) throws Exception {

        Date time =DateUtils.String2Date(formatCurTimeStr, "yyyy-MM-dd HH:mm:ss");
        Date curTime = DateUtils.String2Date(DateUtils.date2String(getCurTime(), "yyyy-MM-dd HH:mm"), "yyyy-MM-dd HH:mm");


        while (curTime.compareTo(time) < 0){
            Thread.sleep(1*1000);
            Date nowFor  = DateUtils.getWebsiteDatetime("http://www.baidu.com");
            curTime = DateUtils.String2Date(DateUtils.date2String(nowFor, "yyyy-MM-dd HH:mm"), "yyyy-MM-dd HH:mm");
        }

        TCFFCPRIZE parsedTcffcPrize = null;
        boolean isGet = false;
        int retryCnt = 0;
        while(!isGet && retryCnt < 30) {
            try {
                log.info("formatCurTimeStr:"+ formatCurTimeStr + ",retryCnt:" + retryCnt);
                retryCnt++;
                Thread.sleep(2000);
                //String result = HttpUtil.doGet("http://77tj.org/api/tencent/onlineim", "utf-8");
                String result = RestClientProxyUtil.doGet("http://77tj.org/api/tencent/onlineim", String.class);

                JSONArray prizeArray = JSONObject.parseArray(result);
                for (int i = 0; i < prizeArray.size(); i++) {
                    JSONObject prize = prizeArray.getJSONObject(i);
                    String onlineTime = (String) prize.get("onlinetime");
                    if (formatCurTimeStr.equals(onlineTime)) {
                        //获取到了当前期
                        isGet = true;
                        TCFFCPRIZE tcffcprize = new TCFFCPRIZE();
                        try {
                            tcffcprize.setTime(time);
                            tcffcprize.setLotteryCode("TCFFC");
                            tcffcprize.setOnlineNum(prize.getIntValue("onlinenumber"));
                            tcffcprize.setAdjustNum(prize.getIntValue("onlinechange"));
                            parsedTcffcPrize = TcffcPrizeConverter.convert2TCFFCPrize(tcffcprize);
                        } catch (Exception e) {
                            log.error("fetchTcffcPrizeFrom77Org error:" + formatCurTimeStr,e );
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                log.error("fetchExactTimePrize error,", e);
            }
        }
        return parsedTcffcPrize;
    }
    public void batchFetchTCFFCData(int start, int end) {

        int cur = start;
        while (cur <= end) {
            log.info("tcffc开始抓取第" + cur + "页数据!");
            try {
                fetchTcFFCDateData(cur);
            } catch (Exception e) {
                log.error("batchFetchTCFFCData , cur: " + cur + "error", e);
            }
            cur++;
        }
    }

    public void batchFetchTCFFCDATAFromTecentOnline(int startNo, int endNo, int pageSize) {
        List<TCFFCPRIZE> allPrizeList = new ArrayList<>();
        TCFFCPRIZE lastTcffcPrize = null;
        for(int i = startNo; i <= endNo; i++) {
            List<TCFFCPRIZE>  curResults = this.fetchFcffcDataFromTecentOnline(i, pageSize, lastTcffcPrize);
            lastTcffcPrize = curResults.get(curResults.size()-1);
            allPrizeList.addAll(curResults);
        }
        log.info("获取数据:" + allPrizeList.size());
    }

    private List<TCFFCPRIZE> fetchFcffcDataFromTecentOnline(int no, int size, TCFFCPRIZE lastTcffcPrize) {
        List<TCFFCPRIZE> tcffcprizeList = new ArrayList<>();
        List<TCFFCPRIZE> tcffcprizeList2 = new ArrayList<>();

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("page_no", ""+no);
            map.put("page_size", ""+size);
            String result = HttpUtil.doPost("http://tencent-online.com/get_result_list", map, "utf-8", DsUtil.genRequestHeaderMap2(""));
            Map<String, Object> onlineResultMap = JSONObject.parseObject(result, Map.class);
            List<Map> dataArray = JSONArray.parseArray(String.valueOf(onlineResultMap.get("data")), Map.class);

            if(null  != dataArray) {
                dataArray.stream().forEach(item -> {
                    TCFFCPRIZE tcffcprize = new TCFFCPRIZE();
                    tcffcprize.setLotteryCode("TCFFC");
                    String time =   String.valueOf(item.get("time"));
                    String issue = String.valueOf(item.get("issue"));
                    String count = String.valueOf(item.get("count"));
                    try {
                        tcffcprize.setTime(DateUtils.String2Date(time, "yyyyMMddHHmm"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    tcffcprize.setOnlineNum(Integer.valueOf(count));
                    tcffcprizeList.add(tcffcprize);
                });
            }
            Collections.sort(tcffcprizeList);

            for(int i = 0; i< tcffcprizeList.size(); i++) {
                TCFFCPRIZE curPrize = tcffcprizeList.get(i);
                if(i == 0 ) {
                    if (lastTcffcPrize != null) {
                        curPrize.setAdjustNum(curPrize.getOnlineNum()- lastTcffcPrize.getOnlineNum());
                    }
                } else {
                    TCFFCPRIZE lastPirze = tcffcprizeList.get(i-1);
                    int adjust = curPrize.getOnlineNum()-lastPirze.getOnlineNum();
                    curPrize.setAdjustNum(adjust);
                }

                tcffcprizeList2.add(TcffcPrizeConverter.convert2TCFFCPrize(curPrize));
            }
        } catch (Exception e) {
            log.error("获取第"+ no+ "页数据失败！",e );
        }
        return  tcffcprizeList2;
    }
    private void insertOnUnexist(TCFFCPRIZE curPrize) {
        TCFFCPRIZECondition conditon = new TCFFCPRIZECondition();
        conditon.createCriteria().andNoEqualTo(curPrize.getNo());
        int cnt = tcffcprizedao.countByCondition(conditon);
        if (cnt <= 0) {
            log.info("数据库新增" + curPrize.getNo());
            tcffcprizedao.insert(curPrize);
        }
    }

    //每页15条数据
    public void fetchTcFFCDateData(int cur) throws ParseException {

        Document doc = this.fetchOnlineDataFrom77Org(cur);
        Elements elements = doc.select(".gridview").select("tbody tr");

        log.info("第" + cur + "页抓取到" + (elements.size() - 1) + "条数据!");
        for (Element item : elements) {
            Elements tdsItem = item.select("td");
            if (null != tdsItem && tdsItem.size() > 0) {
                TCFFCPRIZE tcffcprize = parseOnlineTdItem(tdsItem);
                TCFFCPRIZE curPrize = TcffcPrizeConverter.convert2TCFFCPrize(tcffcprize);
                this.insertOnUnexist(curPrize);
            }
        }
    }

    private TCFFCPRIZE parseOnlineTdItem(Elements item) throws ParseException {
        TCFFCPRIZE tcffcprize = new TCFFCPRIZE();
        String time = "";
        String onlineNum = "";
        String adjustNum = "";
        for (int i = 0; i < item.size(); i++) {
            switch (i) {
                case 0:
                    time = item.get(i).text();
                    break;
                case 1:
                    onlineNum = item.get(i).text();
                    break;
                case 2:
                    adjustNum = item.get(i).text();
                    break;
                default:
                    ;
            }
        }

        tcffcprize.setTime(DateUtils.String2Date(time, "yyyy-MM-dd HH:mm:ss"));
        tcffcprize.setOnlineNum(Integer.valueOf(onlineNum.replace(",", "")));
        tcffcprize.setAdjustNum(Integer.valueOf(adjustNum.replace(",", "")));
        return tcffcprize;
    }

    private Document fetchOnlineDataFrom77Org(int curPage) {
        Document doc = null;
        try {
            Connection conn = Jsoup.connect("http://www.77tj.org/tencent")
                    .data("PageIndex", curPage + "")
                    .data("__RequestVerificationToken", "CfDJ8NXRK3EZpUdPv6SH5UJzslcO5-4sLSbpW4zMNbMfH3P3OZ3QB2vL_ZXVQsJ-bj8noyeNsbFaVQHqdypKJBCZZeSJdzKOtG4xidySW4TFHT1s9tDMMmSmJw3dKBx4vSppr4Har_JrOH5UQ-jji9UcEfc");
            conn.timeout(5000);
            doc = conn.post();
        } catch (Exception e) {
            log.error("fetchDateData error:" + curPage, e);
        } finally {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return doc;
    }
    public void batchFetchOnlineDataFrom77OrgLogFile(String start, String end) {
        try {
            Date startDate = DateUtils.String2Date(start, "yyyy-MM-dd");
            Date endDate = DateUtils.String2Date(end, "yyyy-MM-dd");

            while (startDate.compareTo(endDate) <= 0) {
                String curDate = DateUtils.date2String(startDate, "yyyy-MM-dd");
                List<TCFFCPRIZE> dateResultList = this.batchFetchOnlineDataFrom77OrgLogFile(curDate);
                log.info(curDate + "共抓取了" + dateResultList.size() + "条数据");
                dateResultList.stream().forEach(item -> {
                    this.insertOnUnexist(item);
                });
                startDate =  DateUtils.addDate(1, startDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    static final String preTecentTimeStr = "腾讯时间：";
    static final String postTecentTimeStr = "；采集数据";
    static final String preOnlineStr = "online_resp({\"c\":";
    static final String postOnlineStr = ",\"ec\"";
    //从奇趣历史文件中抓取历史开奖记录
    public List<TCFFCPRIZE> batchFetchOnlineDataFrom77OrgLogFile(String date){
        //下载开奖文件
        //解析文件

        List<TCFFCPRIZE> resultList = new ArrayList<>();
        List<TCFFCPRIZE> resultList2 = new ArrayList<>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("logname", date);
        map.put("__RequestVerificationToken", "CfDJ8JNGqqJAhXVAj6SHbU5yR5Rc6Xc75fjwNXfsj3E_D0QaXYRGpf-z0RIn6XUFGa8JRWqM2HWJq_O_lEB-tTIWXmUcjRhK_D2_XdjiGEEonsuVo7FSRA3OwfkzsWp8Ko7qz1Uvd2-zr6bbvloqcAQ1sHY");
        String result = HttpUtil.doPost("http://www.77tj.org/tencent/download", map, "gb2312", DsUtil.gen77DownloadFileRequestHeaderMap(""));

        String[] itemsArray = result.split("\r\n");

        String curTime = "";
        String curOnline = "";
        int cnt = 0;
        for(String item :itemsArray) {
            try {
                if(item.indexOf("online_resp") > 0) {
                    String time = item.substring(item.indexOf(preTecentTimeStr) + preTecentTimeStr.length() , item.indexOf(postTecentTimeStr)-3);
                    String onlineNum = item.substring(item.indexOf(preOnlineStr)+ preOnlineStr.length(), item.indexOf(postOnlineStr));

                    if(!time.equals(curTime)) {
                        cnt++;
                        if(!onlineNum.equals(curOnline)) {
                            //下一期开奖数据已出
                            cnt = 0;
                            curTime = time;
                            curOnline = onlineNum;
                            TCFFCPRIZE tcffcprize = new TCFFCPRIZE();
                            tcffcprize.setLotteryCode("TCFFC");
                            tcffcprize.setTime(DateUtils.String2Date(time,"yyyy/MM/dd HH:mm"));
                            tcffcprize.setOnlineNum(Integer.valueOf(onlineNum));
                            resultList.add(tcffcprize);
                        } else {
                            if (cnt > 30) {
                                //下一期开奖数据已出且与上期同号
                                cnt = 0;
                                curTime = time;
                                curOnline = onlineNum;

                                TCFFCPRIZE tcffcprize = new TCFFCPRIZE();
                                tcffcprize.setLotteryCode("TCFFC");
                                tcffcprize.setTime(DateUtils.String2Date(time,"yyyy/MM/dd HH:mm"));
                                tcffcprize.setOnlineNum(Integer.valueOf(onlineNum));
                                resultList.add(tcffcprize);
                            }
                        }
                    }

                }
            } catch (Exception e) {
                log.error("batchFetchOnlineDataFrom77OrgLogFile date item error", e);
            }
        }
        Collections.sort(resultList);

        for(int i = 1; i< resultList.size(); i++) {
            TCFFCPRIZE curPrize = resultList.get(i);
            TCFFCPRIZE lastPirze = resultList.get(i-1);
            int adjust = curPrize.getOnlineNum()-lastPirze.getOnlineNum();
            curPrize.setAdjustNum(adjust);
            resultList2.add(TcffcPrizeConverter.convert2TCFFCPrize(curPrize));
        }

        return resultList2;
    }

    private Date getCurTime() {
        Date now  = new Date();
        try {
            now = DateUtils.getWebsiteDatetime("http://www.baidu.com");
        } catch (Exception e) {
            log.error("get online time error!");
        }
        return now;
    }
}
