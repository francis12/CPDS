package com.ds.zxm.service;



import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.io .BufferedReader;
import java.io .IOException;
import java.io .InputStream;
import java.io .InputStreamReader;
import java.net .HttpURLConnection;
import java.net .URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;



@Service
public class TecentOnlinePrizeService {


        private static int c1 = 0;
        private static int c2 = 0;

        public static void main(String[] args) throws InterruptedException {
            String s;
            while(true){
                s = new SimpleDateFormat("HHmmss").format(new Date());
                String hh = s.substring(0,2);
                String mm = s.substring(2,4);
                String ss = s.substring(4,6);
                int c = 0;
                c = Integer.parseInt(mm);
                int d = Integer.parseInt(hh);
                if(ss.equals("10")){
                    if(Integer.parseInt(ss)>30){
                        c=c+1;
                    }
                    int result = pcqqOnline(c,d);
                    while(result==0){
                        result = pcqqOnline(c,d);
                    }
                    //System.out.print("程序时间:"+new SimpleDateFormat("HH:mm:ss").format(new Date()));
                }
                Thread.sleep(1000);
            }
        }

        public static int pcqqOnline(int c,int d){
            HttpURLConnection conn = null;
            int cha = 0;
            try {
                URL realUrl = new URL("http://mma.qq.com/cgi-bin/im/online&callback ");
                conn = (HttpURLConnection) realUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setUseCaches(false);
                conn.setReadTimeout(5000);
                conn.setConnectTimeout(5000);
                conn.setInstanceFollowRedirects(false);
                conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
                int code = conn.getResponseCode();
                if (code == 200) {
                    InputStream is = conn.getInputStream();
                    BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while ((line = in.readLine()) != null){
                        buffer.append(line);
                    }
                    String result = buffer.toString();
                    result = result.substring(12);
                    result = result.substring(0,result.length()-1);
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    Integer curr =  (Integer) jsonObject.get("c");
                    int l = curr;
                    int a[] = new int[9];
                    int i = 0;
                    int sum = 0;
                    if(c%2==0){
                        c2 = l;
                    }else{
                        c1 = l;
                    }
                    if(c%2==0){
                        cha = c2-c1;
                    }else{
                        cha = c1-c2;
                    }
                    while(true){
                        a[i]=l%10;
                        sum=sum+a[i];
                        i++;
                        l = l/10;
                        if(l/10==0){
                            a[i]=l;
                            sum=sum+a[i];
                            break;
                        }
                    }
                    //System.out.println(Arrays.toString(a));
                    NumberFormat nf = NumberFormat.getNumberInstance();
                    String s = nf.format(Long.parseLong(curr+""));
                    String qs = new SimpleDateFormat("yyyyMMdd").format(new Date());
                    int min = d*60+c;
                    String qs1 = "";
                    if(min<100){
                        qs1="00"+min;
                    }else if(min>=100&&min<1000){
                        qs1="0"+min;
                    }else{
                        qs1=min+"";
                    }
                    System.out.println("开奖结果:"+sum%10+","+a[3]+","+a[2]+","+a[1]+","+a[0]);
                    System.out.println("期数:"+qs+"-"+qs1+" "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+" "+s+" "+(cha>=0?"+"+cha:cha));

                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println("连接超时，重新采集......");
                return 0;
            }
            return cha;
        }

    }
