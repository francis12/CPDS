package com.ssc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class StockHttpClient {

	 /**  
     * 发送 get请求  
     */  
    public static String get(String url) {  
    	String result = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            // 创建httpget.  
            HttpGet httpget = new HttpGet(url);  
            //System.out.println("executing request " + httpget.getURI());  
            // 执行get请求.  
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                // 获取响应实体  
                HttpEntity entity = response.getEntity();  
               // System.out.println("--------------------------------------");  
                // 打印响应状态  
                //System.out.println(response.getStatusLine());  
                if (entity != null) {  
                    // 打印响应内容长度  
                   /* System.out.println("Response content length: "  
                            + entity.getContentLength());  */
                    // 打印响应内容  
                    result =  EntityUtils.toString(entity);
                    /*System.out.println("Response content: "  
                            + result);  */
                }  
                //System.out.println("------------------------------------");  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
		return result;  
    }  
  
    /**  
     * 发送 post请求 访问本地应用并根据传递参数不同返回不同结果  
     */  
    public static void post(String url) {  
        // 创建默认的httpClient实例.  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httppost  
        HttpPost httppost = new HttpPost(url);  
        // 创建参数队列  
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        formparams.add(new BasicNameValuePair("inputVal", "13301330133"));  
  
        UrlEncodedFormEntity uefEntity;  
        try {  
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
            httppost.setEntity(uefEntity);  
            System.out.println("executing request " + httppost.getURI());  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    System.out  
                            .println("--------------------------------------");  
                    System.out.println("Response content: "  
                            + EntityUtils.toString(entity, "UTF-8"));  
                    System.out  
                            .println("--------------------------------------");  
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    /**  
     * post方式提交表单（模拟用户登录请求）  
     */  
    public static void postForm(String url) {  
        // 创建默认的httpClient实例.  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httppost  
        HttpPost httppost = new HttpPost(url);  
        // 创建参数队列  
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        formparams.add(new BasicNameValuePair("j_username", "13301330133"));  
        formparams.add(new BasicNameValuePair("j_password", "330133"));  
        UrlEncodedFormEntity uefEntity;  
        try {  
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
            httppost.setEntity(uefEntity);  
            System.out.println("executing request " + httppost.getURI());  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    System.out  
                            .println("--------------------------------------");  
                    System.out.println("Response content: "  
                            + EntityUtils.toString(entity, "UTF-8"));  
                    System.out  
                            .println("--------------------------------------");  
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
  
    /**  
     * 文件下载  
     */  
    public static void download(String url) {  
        // 生成一个httpclient对象  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            HttpGet httpget = new HttpGet(url);  
            CloseableHttpResponse response = httpclient.execute(httpget);  
            HttpEntity resEntity = response.getEntity();  
            if (resEntity != null) {  
                // 响应长度  
                System.out.println("Response content length: "  
                        + resEntity.getContentLength());  
                InputStream in = resEntity.getContent();  
                String fileName = url.substring(url.lastIndexOf("/"));  
                File file = new File("E:\\" + fileName);  
                try {  
                    FileOutputStream fout = new FileOutputStream(file);  
                    int l = -1;  
                    byte[] tmp = new byte[1024];  
                    while ((l = in.read(tmp)) != -1) {  
                        fout.write(tmp, 0, l);  
                        // 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试  
                    }  
                    fout.flush();  
                    fout.close();  
                } finally {  
                    // 关闭低层流。  
                    in.close();  
                }  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
  
    }  
  
    public static void main(String[] args) throws Exception {  
  
        // HttpClientUtils.ssl2("https://www.baidu.com?word=刘德华"); //模拟访问HTTPS  
       System.out.println(StockHttpClient.get("http://hq.sinajs.cn/list=sz002751"));  ; //模拟访问get  
        // HttpClientUtils.post("http://localhost:8080/BCP/system/user/checkName");//模拟访问post  
        // HttpClientUtils.postForm("http://localhost:8080/BCP/j_spring_security_check");//模拟访问post form表单提交  
        //HttpClientUtils.upload("http://localhost:8080/BCP/all/test/upload"); // 模拟文件上传  
        // HttpClientUtils.download("http://localhost:8080/BCP/images/crops/tongyong.jpg");//模拟文件上传  
  
    }  
}
