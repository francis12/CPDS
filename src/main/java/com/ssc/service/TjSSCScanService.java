package com.ssc.service;

import com.ssc.util.RestClientProxyUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TjSSCScanService {



    //实时监控官网
    public void scanTjsscPrizeData() {
       String url = "http://www.tjflcpw.com/Handlers/WinMessageHandler.ashx";
       Map<String, Object> map = new HashMap<>();
       map.put("currentPage",1);
       map.put("pageSize",13);
       map.put("playType","4");
       map.put("time","2018/07/05");
       ResponseEntity<String> result = RestClientProxyUtil.doPost(url,map, MediaType.APPLICATION_FORM_URLENCODED);
       System.out.println(result.getBody());

    }

    public static void main(String[] args) {
        new TjSSCScanService().scanTjsscPrizeData();
    }
}
