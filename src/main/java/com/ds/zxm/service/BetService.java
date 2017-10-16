package com.ds.zxm.service;

import com.ds.zxm.mapper.BetDAO;
import com.ds.zxm.model.BetDO;
import com.ds.zxm.model.BetDOCondition;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Service
public class BetService {

    @Autowired
    private BetDAO betDAO;
    static HttpClient  httpClient = new HttpClient();

    public  static  final String JSESSIONID = "AAEA7AD689775ECD5E6EDBE0CF4CAB9D";
    public  static final String UUID = "f54f9769c3e0-8af4-4cad-889a-8c3976cf8cc8";

    public List<BetDO> queryBetList(BetDOCondition betDOCondition) {
        return betDAO.selectByExampleWithBLOBs(betDOCondition);
    }
    //查询当期curNo内的计划
    public List<BetDO> queryBetUnprizeInfo(BetDO betDO, String curNo) {
        BetDOCondition betDOCondition = new BetDOCondition();
        betDOCondition.createCriteria().andBetTypeEqualTo(betDO.getBetType()).andStartNoLessThanOrEqualTo(curNo)
                .andEndNoGreaterThanOrEqualTo(curNo).andLotteryCodeEqualTo(betDO.getLotteryCode()).andStatusEqualTo(betDO.getStatus())
                ;
        return betDAO.selectByExampleWithBLOBs(betDOCondition);
    }

    public void insert(BetDO bet) {
        betDAO.insert(bet);
    }
    public  static  void main(String[] args) {
        System.out.println(query198conf());
    }
    public static  String query198conf() {
        String loginUrl = "http://www.198good.com:88/loadconf.do?p=[0]&r=" + Math.random();
        //loginUrl = URLEncoder.encode( "http://www.198good.com:88/loadconf.do?p=[0]&r=0.6621101939317919", "utf-8");
        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

        // 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
        GetMethod postMethod = new GetMethod(loginUrl);
        postMethod.setRequestHeader("Origin", "http://www.198good.com:88");
        postMethod.setRequestHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        postMethod.setRequestHeader("X-Requested-With", "XMLHttpRequest");
        postMethod.setRequestHeader("Accept-Encoding", "gzip, deflate");
        postMethod.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");

        postMethod.setRequestHeader("Referer", "http://www.198good.com:88/player/v5/order.jsp?g=CQSSC&t=SSC&u=backtoxcb8&o=3");
        postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");

        String session = "th=mountain; uuid=" + UUID + "; JSESSIONID=" + JSESSIONID + "; announceRead=; username=backtoxcb8";
        postMethod.setRequestHeader("Cookie", session);

        try {
            int loginConfStatusCode = httpClient.executeMethod(postMethod);
            System.out.print(postMethod.getResponseBodyAsString());
            return postMethod.getResponseBodyAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  "";
    }

    public void betto198(String amtMode,String betNO,String multi) {

        String loginUrl = "http://www.198good.com:88/placeorder.do?p=1930&u=" + amtMode + "&s=" + betNO + "&q=1&m=" + multi + "&w=0&b=0&g=0&c=[" + multi + "]";
        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

        // 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
        PostMethod postMethod = new PostMethod(loginUrl);
        postMethod.setRequestHeader("Origin", "http://www.198good.com:88");
        postMethod.setRequestHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        postMethod.setRequestHeader("X-Requested-With", "XMLHttpRequest");
        postMethod.setRequestHeader("Accept-Encoding", "gzip, deflate");
        postMethod.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");

        postMethod.setRequestHeader("Referer", "http://www.198good.com:88/player/v5/order.jsp?g=CQSSC&t=SSC&u=backtoxcb8&o=3");
        postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");

        String session = "th=mountain; uuid=" + UUID + "; JSESSIONID="+JSESSIONID+"; announceRead=; username=backtoxcb8";
        postMethod.setRequestHeader("Cookie",session);

        // 设置登陆时要求的信息，用户名和密码
        NameValuePair[] data = { new NameValuePair("p", "[\"backtoxcb8\",\"230679zxm\",3]")};
        postMethod.setRequestBody(data);
        try {
            int loginConfStatusCode=httpClient.executeMethod(postMethod);
            System.out.print(postMethod.getResponseBodyAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int updateBetDO(BetDO bet, BetDOCondition bdd) {
       return betDAO.updateByExampleWithBLOBs(bet, bdd);
    }
}
