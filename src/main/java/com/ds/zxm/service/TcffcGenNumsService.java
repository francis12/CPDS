package com.ds.zxm.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ds.zxm.mapper.TCFFCPRIZEDAO;
import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TCFFCPRIZECondition;
import com.ds.zxm.model.TcffcPrizeConverter;
import com.ds.zxm.util.DateUtils;
import com.ds.zxm.util.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class TcffcGenNumsService {

    @Resource
    private TCFFCPRIZEDAO tcffcprizedao;
    Logger log = Logger.getLogger(TcffcGenNumsService.class);

    public String generateNextNums(TCFFCPRIZE  curPrize) {

        File file = new File("gen.txt");
        try {
            FileUtils.writeStringToFile(file, curPrize.getPrize() + " ", true);
        } catch (Exception e) {
            log.error("generateNextNums error", e);
        }
        return null;
    }
}
