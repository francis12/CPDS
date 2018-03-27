package com.ds.zxm.service;

import com.alibaba.fastjson.JSONObject;
import com.ds.zxm.mapper.TCFFCPRIZEDAO;
import com.ds.zxm.model.*;
import com.ds.zxm.util.DateUtils;
import org.apache.log4j.Logger;
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
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class LotteryPrizeScheduleService {

    @Resource
    private TCFFCPRIZEDAO tcffcprizedao;
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
                log.error("fetchTcffcPrize error",e);
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
                System.out.println("result:" + result);
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
                if (min < 100) {
                    qs1 = "00" + min;
                } else if (min >= 100 && min < 1000) {
                    qs1 = "0" + min;
                } else {
                    qs1 = min + "";
                }
                log.info("开奖结果:" + sum % 10 + "," + a[3] + "," + a[2] + "," + a[1] + "," + a[0]);
                log.info("期数:" + qs + "-" + qs1 + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " " + s + " " + (cha >= 0 ? "+" + cha : cha));

                String no = qs + "-" + qs1;
                int wan = sum%10;
                int qian = a[3];
                int bai = a[2];
                int shi = a[1];
                int ge = a[0];
                int onlineNum = Integer.valueOf(s.replace(",",""));
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
                    Date date = DateUtils.String2Date( DateUtils.date2String(new Date(), "yyyyMMdd"),"yyyyMMdd" ) ;
                    tcffcprize.setLotteryDate(date);
                } catch (ParseException e) {
                    log.error("日期转换错误",e);
                }
                tcffcprize.setTime(new Date());
                tcffcprizedao.insert(tcffcprize);
            }
        } catch (IOException e) {
            log.error("连接超时，重新采集......",e);
            return 0;
        }
        return cha;
    }
}
