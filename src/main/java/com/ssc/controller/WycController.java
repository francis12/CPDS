package com.ssc.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ssc.util.HttpUtil;
import com.ssc.vo.WycResultVO;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

@Controller
public class WycController {
    Logger log = Logger.getLogger(WycController.class);
    private static final String YDURL = "http://fanyi.youdao.com/translate?&doctype=json&type=AUTO&i=";
    private static final String BAIDUURL = "https://fanyi.baidu.com/transapi?from=auto&to=&query=";

    @ResponseBody
    @RequestMapping(value = "/wyc", method = {RequestMethod.POST})
    public WycResultVO wycByTranslate(String content, String type, int radio) {
        WycResultVO wycResultVO = new WycResultVO();
        String result2 = null;
        try {
            wycResultVO.setStatus(1);
            if(StringUtils.isEmpty(type) || "1".equals(type)) {
                String result1 = translate(content);
                result2 = translate(result1);
            } else if ("2".equals(type)) {
                String result1 = translateByBaidu(content);
                result2 = translateByBaidu(result1);
            }
            wycResultVO.setContent(result2);
        } catch (Exception e) {
            wycResultVO.setStatus(-1);
            wycResultVO.setContent(content);
            log.error("伪原创发生错误,",e);
        }
        System.out.println(result2);
        return wycResultVO;
    }

    public static void main(String[] args) {
        String content = "ZooKeeper是一个分布式的，开放源码的分布式应用程序协调服务，是Google的Chubby一个开源的实现，它是集群的管理者，监视着集群中各个节点的状态根据节点提交的反馈进行下一步合理操作。最终，将简单易用的接口和性能高效、功能稳定的系统提供给用户。每个子目录项如 NameService 都被称作为znode，和文件系统一样，我们能够自由的增加、删除znode，在一个znode下增加、删除子znode，唯一的不同在于znode是可以存储数据的。";
        String result2 = null;
        try {
            String result1 = translate(content);
            result2 = translate(result1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("有道结果 :" + result2);
        try {
            String result1 = translateByBaidu(content);
            result2 = translateByBaidu(result1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("百度结果 :" + result2);
    }

    private static String translate(String content) throws UnsupportedEncodingException {
        String transResult = "";
        String encodeContent = URLEncoder.encode(content,"utf-8");
        String result = HttpUtil.doGet(YDURL + encodeContent, "utf-8");
        Map<String, Object> map = JSONObject.parseObject(result, Map.class);
        if (0 == (Integer) map.get("errorCode")) {
            JSONArray translateResult = ((JSONArray) map.get("translateResult")).getJSONArray(0);
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<translateResult.size();i++) {
                sb.append(translateResult.getJSONObject(i).getString("tgt"));
            }
            transResult = sb.toString();
        }
        return transResult;
    }

    private static String translateByBaidu(String content) throws UnsupportedEncodingException {
        String transResult = "";
        String encodeContent = URLEncoder.encode(content,"utf-8");
        String result = HttpUtil.doGet(BAIDUURL + encodeContent, "utf-8");
        Map<String, Object> map = JSONObject.parseObject(result, Map.class);
        if (0 == (Integer) map.get("status")) {
            JSONArray translateResult = (JSONArray) map.get("data");
            JSONObject resultItem = translateResult.getJSONObject(0);
            transResult = resultItem.getString("dst");
        }
        return transResult;
    }
}
