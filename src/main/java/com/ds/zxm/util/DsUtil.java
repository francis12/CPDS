package com.ds.zxm.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;


public class DsUtil {
   static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(DsUtil.class);


    public static String login2FetchAuth() {

        // 需登陆后访问的
        String result = "";
        HttpClient httpClient = new HttpClient();
        try {
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

            // 登陆 Url
            String loginUrl = "http://www.ds018.com/user/login";

            // 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
            PostMethod postMethod = new PostMethod(loginUrl);
            postMethod.setRequestHeader("Origin", "http://www.ds018.com");
            postMethod.setRequestHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            postMethod.setRequestHeader("X-Requested-With", "XMLHttpRequest");
            postMethod.setRequestHeader("Accept-Encoding", "gzip, deflate");
            postMethod.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");

            postMethod.setRequestHeader("Referer", "http://www.ds018.com/user/login");
            postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");

            String session = "caipiao=chongqing; _ga=GA1.2.747412297.1508398730; _gid=GA1.2.1138527932.1508398730";
            postMethod.setRequestHeader("Cookie", session);

            // 设置登陆时要求的信息，用户名和密码
            NameValuePair[] data = {new NameValuePair("username", "backtoxcb2"), new NameValuePair("password", "230679zxm"), new NameValuePair("back", "http://www.ds018.com/")};
            postMethod.setRequestBody(data);
            int loginConfStatusCode = httpClient.executeMethod(postMethod);
            if (loginConfStatusCode == 302) {
                String cookieHeader = postMethod.getResponseHeader("Set-Cookie").getValue();
                result = cookieHeader.substring(cookieHeader.indexOf("auth=") + "auth=".length(), cookieHeader.indexOf(";"));
            }

        } catch (Exception e) {
            log.error("query auth error", e);
        }
        return result;
    }

   public static Map<String, String> genRequestHeaderMap(String caipiao) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Cookie", "auth=" + auth + "; _ga=GA1.2.747412297.1508398730; _gid=GA1.2.1138527932.1508398730; caipiao=" + caipiao);

        //Cookie:auth=29c24f4e49a159b00f7610889da713e852fc2ad1f8a4ffecc72c5570f87a54466f260dcced491b2ac3a0a426223c89d4c1dfdaca140a9e37dcf5e93998ccdea1;
        // _ga=GA1.2.747412297.1508398730; _gid=GA1.2.1138527932.1508398730; _gat=1; caipiao=n198_90s
        return  map;
    }

    public static  String auth = null;
    public  static void init (){
        while(StringUtils.isEmpty(auth)) {
            auth = DsUtil.login2FetchAuth();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("初始化auth:" + auth);
    }
}


