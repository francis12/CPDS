package com.ds.zxm.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ds.zxm.mapper.TCFFCPRIZEDAO;
import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TCFFCPRIZECondition;
import com.ds.zxm.model.TcffcPrizeConverter;
import com.ds.zxm.util.DateUtils;
import com.ds.zxm.util.HttpUtil;
import com.ds.zxm.util.LotteryUtil;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class LotteryStrategyService {

    @Resource
    private TCFFCPRIZEDAO tcffcprizedao;
    @Resource
    private TcffcGenNumsService tcffcGenNumsService;
    public static Executor executor = Executors.newFixedThreadPool(1);
    Logger log = Logger.getLogger(LotteryStrategyService.class);

    private static int c1 = 0;
    private static int c2 = 0;

    public static void main(String[] args) {
        //new LotteryPrizeScheduleService().fetchTcffcPrizeFrom77Org();
    }

    public final String format="yyyy-MM-dd HH:mm:ss";
    public Map<String ,Integer> checkLotteryStrategy(String start, String end, String lotteryCode, String type, Integer rate) {
        Map<String ,Integer> loseCnt = new HashMap<>();
        int totalCheckCnt = 0;
        int winCheckCnt = 0;
        try {
            StringBuffer zgSb = new StringBuffer();
            //连挂
            StringBuffer lgSb = new StringBuffer();
            Date startTime = DateUtils.String2Date(start, format);
            Date endTime = DateUtils.String2Date(end, format);
            while (startTime.compareTo(endTime) <= 0) {
                totalCheckCnt++;
                boolean isMatch = runOneNoStrategy(startTime,type, rate);
                if(isMatch){
                    winCheckCnt++;
                    lgSb.append(zgSb.length());
                    if(zgSb.length() >= 6) {
                        log.info(startTime + "遗漏" + zgSb.length());
                    }
                    if(loseCnt.get("" + zgSb.length() ) == null) {
                        loseCnt.put("" + zgSb.length(), 1);
                    } else {
                        loseCnt.put(""+zgSb.length(), loseCnt.get("" + zgSb.length()) + 1);
                    }
                    if(zgSb.length() >= 1) {
                        zgSb.delete(0, zgSb.length());
                    }
                }else{
                    zgSb.append(isMatch?"中":"挂");
                }
                startTime = DateUtils.addMinutes(1, startTime);
            }
            log.info("回测结果:总验证期数：" + totalCheckCnt + ",预测准确率：" + BigDecimal.valueOf(winCheckCnt).divide(BigDecimal.valueOf(totalCheckCnt), 4, RoundingMode.FLOOR));
        } catch (ParseException e) {
            log.error("checkLotteryStrategy", e);
        }
        return loseCnt;
    }

    //校验开奖数据
    private boolean runOneNoStrategy(Date time, String type, Integer rate) {
        TCFFCPRIZE genNoPrize = null;
        TCFFCPRIZE realPrize = null;
        List<TCFFCPRIZE> prizeList = null;
        try {
            String no = TcffcPrizeConverter.genNofromTime(time);
            //1.计算理论预测数据 2.数据库读取实际开奖数据,3比较是否
            TCFFCPRIZECondition condition = new TCFFCPRIZECondition();
            condition.createCriteria().andNoEqualTo(no);
            prizeList = tcffcprizedao.selectByCondition(condition);
            if (null == prizeList || prizeList.size() <= 0) {
                //log.info(no + "期未找到开奖数据,默认中奖.");
                return true;
            } else {
                realPrize = prizeList.get(0);
            }
            //取上一期实际开奖数据
            Date last1Min = DateUtils.addMinutes(-1, time);
            String lastNo = TcffcPrizeConverter.genNofromTime(last1Min);
            TCFFCPRIZECondition condition2 = new TCFFCPRIZECondition();
            condition2.createCriteria().andNoEqualTo(lastNo);
            List<TCFFCPRIZE> lastPrizes = tcffcprizedao.selectByCondition(condition2);
            if (null != lastPrizes && lastPrizes.size() > 0) {
                if("1".equals(type)) {
                    genNoPrize = tcffcGenNumsService.calGenPrizeByRateNum(lastPrizes.get(0), rate);
                } else if("0".equals(type)) {
                    genNoPrize = tcffcGenNumsService.calGenPrizeByRate(lastPrizes.get(0));
                }
                //log.info(no + "预测值:" + genNoPrize.getPrize() + ",实际开奖：" + prizeList.get(0).getPrize());
                return LotteryUtil.judgeIsmatchBetween3(genNoPrize.getQian(), realPrize.getQian());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void insertOnUnexist(TCFFCPRIZE curPrize) {
        TCFFCPRIZECondition conditon = new TCFFCPRIZECondition();
        conditon.createCriteria().andNoEqualTo(curPrize.getNo());
        int cnt = tcffcprizedao.countByCondition(conditon);
        if (cnt <= 0) {
            tcffcprizedao.insert(curPrize);
        }
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
