package com.ssc.service;

import com.ssc.util.DateUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class XjSSCScanService {


    public void startScanXjssc() {
        while(true) {
            this.scanXjsscPrizeData();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //实时监控官网
    public void scanXjsscPrizeData() {
        Document doc = null;
        try {
            Connection conn = Jsoup.connect("http://www.xjflcp.com/game/sscIndex")
                    .data("__RequestVerificationToken", "CfDJ8NXRK3EZpUdPv6SH5UJzslcO5-4sLSbpW4zMNbMfH3P3OZ3QB2vL_ZXVQsJ-bj8noyeNsbFaVQHqdypKJBCZZeSJdzKOtG4xidySW4TFHT1s9tDMMmSmJw3dKBx4vSppr4Har_JrOH5UQ-jji9UcEfc");
            conn.timeout(5000);
            doc = conn.post();
            Elements elements = doc.select("#kj_code_tab").select("tbody tr");
            Elements curItem = elements.get(1).select("td");
            String no = curItem.select(".bold").text();
            String nums = curItem.select(".kj_codes").text();
            String calNo = getCurrentTjNo();

            //System.out.println("计算期数" + calNo);
            //System.out.println("官网开奖：" + no + ":" + nums);
            if(calNo.equals(no)) {
                System.out.println("重要通知:" + calNo + "期已提前开奖，开奖号码为:" + nums);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        /*for (Element item : elements) {
            Elements tdsItem = item.select("td");
            if (null != tdsItem && tdsItem.size() > 0) {
                String no = tdsItem.select(".bold").text();
                String nums = tdsItem.select(".kj_codes").text();
                System.out.println("计算期数" + getCurrentTjNo());
                System.out.println(no + ":" + nums);

            }
        }*/
    }

    public String  getCurrentTjNo() {
        Date nowFor  = null;
        try {
            nowFor = DateUtils.getWebsiteDatetime("http://www.baidu.com");
            //计算与10点的相差时间
            Date curTimeForTenHour = DateUtils.String2Date(DateUtils.date2String(nowFor, "yyyy-MM-dd") + " 10:00:00", "yyyy-MM-dd HH:mm:ss");
            long minute = DateUtils.calculateMiniute(nowFor, curTimeForTenHour);
            //每十分钟一期
            long no = minute /10+1;

            String tmp = "00" + String.valueOf(no);
            String dateNo =DateUtils.date2String(nowFor, "yyyyMMdd");
            String minNo = tmp.substring(tmp.length()-2);
            return dateNo+minNo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        new XjSSCScanService().startScanXjssc();
    }
}
