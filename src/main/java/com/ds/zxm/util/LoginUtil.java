package com.ds.zxm.util;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;


public class LoginUtil {
    public static void main2(String[] args) {
        try {
            System.out.println(getcookies("http://www.198good.com:88/player/v5/login.jsp?o=3"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getcookies(String A_URL) throws IOException {
        CookieManager manager=new CookieManager();
        manager.setCookiePolicy(java.net.CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(manager);
        URL url=new URL(A_URL);
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.getHeaderFields();
        CookieStore store = manager.getCookieStore();
        String cookies = store.getCookies().toString();
        cookies = cookies.replace("[", "");
        cookies = cookies.replace("]", "");
        return cookies;
    }
    public static String doPost(){
        String B_URL = "";
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        try{
            URL url = new URL(B_URL);


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();


            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:51.0) Gecko/20100101 Firefox/51.0");

            conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");

            conn.setRequestProperty("Accept-Encoding", "gzip, deflate");

            conn.setRequestProperty("Referer", "http://www.198good.com:88/player/v5/login.jsp?o=3");

            conn.setRequestProperty("Cookie", getcookies("http://www.198good.com:88/player/v5/login.jsp?o=3") + "; th=mountain");

            conn.setRequestProperty("Connection", "keep-alive");

            conn.setRequestProperty("Upgrade-Insecure-Requests", "1");

            conn.setDoOutput(true);
            conn.setDoInput(true);

            out = new PrintWriter(conn.getOutputStream());
            out.flush();

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = in.readLine())!=null){
                result +="\n"+line;
            }
        }catch(Exception e){
            System.out.println("发送POST请求异常"+e);
            e.printStackTrace();
        }finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    public static void main(String[] args) {

        // 需登陆后访问的 Url
       // String dataUrl = "http://hi.mop.com/?";
        HttpClient httpClient = new HttpClient();


        //1.http://www.198good.com:88获取cookie(jsessionid) 2.通过（get）http://www.198good.com:88/player/v5/login.jsp?o=3获取其他cookie（请求header中加入上一步中取得的jsessionid）
        //
        GetMethod getMethod198 = new GetMethod("http://www.198good.com:88/player/v5/login.jsp?o=3");

        try {
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            //int statusCode=httpClient.executeMethod(postMethod);

            int statusCode = httpClient.executeMethod(getMethod198);
            // 获得登陆后的 Cookie
            Cookie[] cookies = httpClient.getState().getCookies();

            StringBuffer tmpcookies = new StringBuffer();
            for (Cookie c : cookies) {
                tmpcookies.append(c.toString() + ";");
                System.out.println("cookies = "+c.toString());
            }

            if(statusCode==200){//重定向到新的URL
                System.out.println("模拟登录成功");
                /*// 进行登陆后的操作
                GetMethod loginGetMethod = new GetMethod("http://www.198good.com:88/player/v5/login.jsp?o=3");
                // 例如，referer 从哪里来的，UA 像搜索引擎都会表名自己是谁，无良搜索引擎除外
                loginGetMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
                httpClient.executeMethod(loginGetMethod);
                // 打印出返回数据，检验一下是否成功
                String text = loginGetMethod.getResponseBodyAsString();
                //System.out.println(text);*/

                // 登陆 Url
                String loginUrl = "http://www.198good.com:88/loginconf.do";

                // 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
                PostMethod postMethod = new PostMethod(loginUrl);
                postMethod.setRequestHeader("Origin", "http://www.198good.com:88");
                postMethod.setRequestHeader("Accept", "application/json, text/javascript, */*; q=0.01");
                postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                postMethod.setRequestHeader("X-Requested-With", "XMLHttpRequest");
                postMethod.setRequestHeader("Accept-Encoding", "gzip, deflate");
                postMethod.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");

                postMethod.setRequestHeader("Referer", "http://www.198good.com:88/player/v5/login.jsp?o=3");
                postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");

                String session = "th=mountain; uuid=f54f9769c3e0-8af4-4cad-889a-8c3976cf8cc8; sb=true; JSESSIONID=23274AB353D6CB646E2C8DCF8AC36C58; announceRead=; username=backtoxcb8";
                postMethod.setRequestHeader("Cookie",session);

                // 设置登陆时要求的信息，用户名和密码
                NameValuePair[] data = { new NameValuePair("p", "[\"backtoxcb8\",\"230679zxm\",3]")};
                postMethod.setRequestBody(data);
                int loginConfStatusCode=httpClient.executeMethod(postMethod);
                System.out.print(postMethod.getResponseBodyAsString());
                if (loginConfStatusCode==301) {

                }

            }
            else {
                System.out.println("登录失败");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

