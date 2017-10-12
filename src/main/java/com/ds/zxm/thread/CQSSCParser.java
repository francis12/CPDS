package com.ds.zxm.thread;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CQSSCParser {
    public static void main(String[] args) {
        new CQSSCParser().start();
    }
    public void start(){
        try{
            Connection conn = Jsoup.connect("http://caipiao.163.com/order/cqssc/");
            conn.timeout(3000);
            Document doc = conn.get();

            // 页面中有三类彩票, 分别包含在三个table中,table的类名都是<table class="awardList">
            // 每类彩票table的<tbody>选出来， 每行一个彩种
            Elements els = doc.select("#awardTableBox").select("#awardNumBody");
            Elements awardNumList = els.select("tr");

            DigLotteryProc(awardNumList);



        }catch(Exception e){

        }
    }

    private void DigLotteryProc(Elements digLotteryBody){
        // 处理数字彩票,从中间抓取取双色球、大乐透和七乐彩的开奖结果，每个彩种在table中都是<tbody>的一行<tr>
        Elements trs = digLotteryBody.select("tr");
        for (Element row : trs){
            Elements name = row.select(".first");
            if (0 == name.text().compareTo("双色球")){
               // SSQ(row);
            }
            if (0 == name.text().compareTo("大乐透")){
                //DLT(row);
            }
            if (0 == name.text().compareTo("七乐彩")){
                //QLC(row);
            }
        }
    }

}
