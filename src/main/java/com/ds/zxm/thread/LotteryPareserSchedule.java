package com.ds.zxm.thread;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LotteryPareserSchedule {
    public void run(){
        try{
            Connection conn = Jsoup.connect("http://caipiao.163.com/award/");
            conn.timeout(3000);
            Document doc = conn.get();

            // 页面中有三类彩票, 分别包含在三个table中,table的类名都是<table class="awardList">
            // 每类彩票table的<tbody>选出来， 每行一个彩种
            Elements els = doc.select(".awardList");
            Element digLotteryBody = els.get(0).select("tbody").get(0); // 数字彩票
            Element phyLotteryBody = els.get(1).select("tbody").get(0); // 竞技体育
            Element freLotteryBody = els.get(2).select("tbody").get(0); // 高频彩

            DigLotteryProc(digLotteryBody);



        }catch(Exception e){

        }
    }

    private void DigLotteryProc(Element digLotteryBody){
        // 处理数字彩票,从中间抓取取双色球、大乐透和七乐彩的开奖结果，每个彩种在table中都是<tbody>的一行<tr>
        Elements trs = digLotteryBody.select("tr");
        for (Element row : trs){
            Elements name = row.select(".first");
            if (0 == name.text().compareTo("双色球")){
                SSQ(row);
            }
            if (0 == name.text().compareTo("大乐透")){
                //DLT(row);
            }
            if (0 == name.text().compareTo("七乐彩")){
                //QLC(row);
            }
        }
    }
    private void SSQ(Element row){
       /* try{
            SsqAward ssqAward = new SsqAward();

            String notice = "双色球";

            Elements period = row.select(".period");
            notice = notice + " " + period.text();
            ssqAward.period = Integer.valueOf(SsqAward.PhasePeriod(period.text()));


            Elements reds = row.select(".smallRedBall");
            int i = 0;
            for (Element redBall : reds){
                notice = notice + " " + redBall.text();
                ssqAward.reds[i] = Integer.valueOf(redBall.text());
                i++;
            }

            notice = notice + " :";

            Elements blue = row.select(".smallBlueball");
            notice = notice + " " + blue.text();
            ssqAward.blue = Integer.valueOf(blue.text());

            m_ssqAward = ssqAward;

            SLogger.m_logger.info(notice);
        }catch(Exception e){
            SLogger.m_logger.error(e.getMessage());
            throw e;
        }*/
    }
}
