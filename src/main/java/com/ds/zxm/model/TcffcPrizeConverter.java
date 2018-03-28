package com.ds.zxm.model;

import com.ds.zxm.util.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TcffcPrizeConverter {

    public static TCFFCPRIZE convert2TCFFCPrize(TCFFCPRIZE tcffcprize) {
        return convert2TCFFCPrizeFromOnlineNum(tcffcprize.getOnlineNum(), tcffcprize.getTime(), tcffcprize.getAdjustNum());
    }

    public static TCFFCPRIZE convert2TCFFCPrizeFromOnlineNum(Integer onlineNum, Date time, Integer adjustNum) {

        TCFFCPRIZE tcffcprize = new TCFFCPRIZE();
        tcffcprize.setLotteryCode("TCFFC");
        tcffcprize.setOnlineNum(onlineNum);
        tcffcprize.setAdjustNum(adjustNum);
        tcffcprize.setNo(genNofromTime(time));
        tcffcprize.setTime(time);
        try {
            tcffcprize.setLotteryDate(DateUtils.String2Date(DateUtils.date2String(time, "yyyyMMdd HH:mm:ss"), "yyyyMMdd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int prizeItemArray[] = new int[9];
        int i = 0;
        int sum = 0;
        while (true) {
            prizeItemArray[i] = onlineNum % 10;
            sum = sum + prizeItemArray[i];
            i++;
            onlineNum = onlineNum / 10;
            if (onlineNum / 10 == 0) {
                prizeItemArray[i] = onlineNum;
                sum = sum + prizeItemArray[i];
                break;
            }
        }

        int wan = sum % 10;
        int qian = prizeItemArray[3];
        int bai = prizeItemArray[2];
        int shi = prizeItemArray[1];
        int ge = prizeItemArray[0];

        tcffcprize.setWan(wan);
        tcffcprize.setQian(qian);
        tcffcprize.setBai(bai);
        tcffcprize.setShi(shi);
        tcffcprize.setGe(ge);
        tcffcprize.setPrize("" + wan + qian + bai + shi + ge + "");
        return tcffcprize;
    }

    //从当前时间确定当前期数
    public static String genNofromTime(Date time) {
        String dateQs = new SimpleDateFormat("yyyyMMdd").format(time);
        String hms = new SimpleDateFormat("HHmmss").format(time);
        String hh = hms.substring(0, 2);
        String mm = hms.substring(2, 4);
        String ss = hms.substring(4, 6);

        int qs = Integer.valueOf(hh) * 60 + Integer.valueOf(mm);
        String timeQs = "";
        if (qs < 10) {
            timeQs = "000" + qs;
        } else if (qs >= 10 && qs < 100) {
            timeQs = "00" + qs;
        } else if (qs >= 100 && qs < 1000) {
            timeQs = "0" + qs;
        } else {
            timeQs = qs + "";
        }
        return dateQs + "-" + timeQs;
    }
}