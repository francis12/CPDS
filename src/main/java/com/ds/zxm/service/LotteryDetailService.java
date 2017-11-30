package com.ds.zxm.service;

import com.ds.zxm.mapper.LotteryDetailMapper;
import com.ds.zxm.model.LotteryDetail;
import com.ds.zxm.model.LotteryDetailCondition;
import com.ds.zxm.util.DateUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.logging.Logger;

@Service
public class LotteryDetailService {

    @Autowired
    private LotteryDetailMapper lotteryDetailMapper;

    org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LotteryDetailService.class);
    public List<LotteryDetail> queryLotteryDetails() {

        LotteryDetailCondition lotteryDetailCondition = new LotteryDetailCondition();
        lotteryDetailCondition.createCriteria().andLotteryCodeEqualTo("");
        return lotteryDetailMapper.selectByCondition(lotteryDetailCondition);
    }

    public Map<String,Object> analysisQQffcData(String type, String start, String end) {
        Map<String,Object> map = new HashMap<>();
        LotteryDetailCondition lotteryDetailCondition = new LotteryDetailCondition();
        lotteryDetailCondition.setOrderByClause("alias_no asc ");
        lotteryDetailCondition.createCriteria().andLotteryCodeEqualTo("QQFFC").andLotteryDateBetween(start, end);
        List<LotteryDetail> list = lotteryDetailMapper.selectByConditionWithH3(lotteryDetailCondition);
        List<String> timeList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        for(int i=1; i<list.size(); i++) {
            LotteryDetail next = list.get(i);
            LotteryDetail pre = list.get(i-1);
            int nextNum = Integer.valueOf(next.getAliasNo().substring(next.getAliasNo().length()-4));
            int preNum = Integer.valueOf(pre.getAliasNo().substring(pre.getAliasNo().length()-4));
            timeList.add(next.getAliasNo());
            if (next.getAliasNo().substring(0,8).equals(pre.getAliasNo().substring(0,8))) {
                numList.add(nextNum - preNum);
            } else {
                numList.add(0);
            }
        }

        map.put("xAxis", timeList);
        map.put("series", numList);
        return map;
    }

    public void fetchQQffcData(Date startDate, Date endDate) throws Exception {
        while ( startDate.compareTo(endDate) <= 0) {
            String date = DateUtils.date2String(startDate, "yyyyMMdd");
            this.fetchDateData(date);
            log.info("完成处理:" + date );
            startDate = DateUtils.addDate(1, startDate);
        }
    }
    public void fetchDateData(String date) throws Exception {
            Connection conn = Jsoup.connect("http://www.tx-ffc.com/txffc/kj-" + date + ".html");
            conn.timeout(3000);
            Document doc = conn.get();

            Elements els = null;
            els  = doc.select(".klist .kj_list").select(".kj_code");
            log.info(date + " kj_code size:"  + els.size());
            DigLotteryProc(els, date);
    }

    private void DigLotteryProc(Elements els, String date) throws ParseException {
        // 处理数字彩票,从中间抓取取双色球、大乐透和七乐彩的开奖结果，每个彩种在table中都是<tbody>的一行<tr>
        LotteryDetail lotteryDetail = null;
        for (Element kjTitleElement : els) {
            Elements kjElements = kjTitleElement.select(".kj_title");
            for (Element row : kjElements) {
                String kjTime = row.getElementsByClass("a1").text();
                String curNo = row.getElementsByClass("a2").text();
                String kjNo = row.getElementsByClass("a3").text();

                lotteryDetail =  new LotteryDetail();
                try {
                    lotteryDetail.setLotteryCode("QQFFC");
                    lotteryDetail.setLotteryDate(date);
                    lotteryDetail.setTime(DateUtils.String2Date(date + kjTime, "yyyyMMddhh:mm"));
                    lotteryDetail.setNo(date + "-" + curNo.substring(0,curNo.length()-1));
                    lotteryDetail.setAliasNo(date + "-" + curNo.substring(0,curNo.length()-1));

                    lotteryDetail.setNum1(kjNo.substring(4));
                    lotteryDetail.setNum2(kjNo.substring(3,4));
                    lotteryDetail.setNum3(kjNo.substring(2,3));
                    lotteryDetail.setNum4(kjNo.substring(1,2));
                    lotteryDetail.setNum5(kjNo.substring(0,1));
                    this.saveIfNotExist(lotteryDetail);
                } catch (Exception e) {
                    log.error(date + curNo + " lotteryDetail set error," , e);
                }
            }
        }
    }

    private void saveIfNotExist(LotteryDetail lotteryDetail) {
        LotteryDetailCondition lotteryDetailCondition = new LotteryDetailCondition();
        lotteryDetailCondition.createCriteria().andLotteryCodeEqualTo(lotteryDetail.getLotteryCode())
                .andAliasNoEqualTo(lotteryDetail.getAliasNo());
        int cnt = lotteryDetailMapper.countByCondition(lotteryDetailCondition);
        if (cnt == 0) {
            lotteryDetailMapper.insert(lotteryDetail);
        }
    }

}
