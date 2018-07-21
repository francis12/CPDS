package com.ds.zxm.service;

import com.ds.zxm.mapper.CurNOModelDAO;
import com.ds.zxm.mapper.GenPrizeModelDAO;
import com.ds.zxm.mapper.TCFFCPRIZEDAO;
import com.ds.zxm.model.*;
import com.ds.zxm.util.RestClientProxyUtil;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
