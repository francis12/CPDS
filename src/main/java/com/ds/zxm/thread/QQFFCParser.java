package com.ds.zxm.thread;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class QQFFCParser {
    public static void main(String[] args) {
        new QQFFCParser().start();
    }
    public void start(){
        try{
            Connection conn = Jsoup.connect("http://www.77tj.org/tencent")
                    .cookie(".AspNetCore.Antiforgery.nlUjaxz2h3Y", "CfDJ8G7EgE_H0flLt8k5_Y6lrz9stboT9douoXuT06r7tNvbhRolOtnL1H98LY56qkcSKbnTkHD2aP8osxWU_tg-mtNvBKGMXBqk7voSzaTcS9_PRMZqqVho6SxaY4JeRM77YPCUImhfYN-31f04XtNgRgQ")
                    .data("PageIndex", "51")
                    .data("__RequestVerificationToken", "CfDJ8G7EgE_H0flLt8k5_Y6lrz8ZVhBx7UjN6qPY0MM0G-N4mw_6OHtB0v_7iE21C6HJCdht752tpIM6mGto--Uu19TvDhGJoenvDnVM4B3FlGVd5222yu3KL7euTIOktmR4tdFuSquq5zcW9mS_8RSuk9o");
            conn.timeout(3000);
            Document doc = conn.post();

            // 页面中有三类彩票, 分别包含在三个table中,table的类名都是<table class="awardList">
            // 每类彩票table的<tbody>选出来， 每行一个彩种
            Elements els = null;
            els  = doc.select(".gridview").select("tbody tr");
            System.out.print(els.size());
            DigLotteryProc(els);
        }catch(Exception e){
            System.out.print(e.getStackTrace());
        }
    }

    private void DigLotteryProc(Elements els){
        for (Element row : els) {
            Elements tds = row.select("td");
            for(Element element : tds) {
                if("统计时间".equals(element.text())
                        ||"在线人数".equals(element.text())
                        ||"波动值".equals(element.text())) {
                    continue;
                }
                //时间，在线数，波动值
                System.out.println(element.text());
            }
        }
    }

}
