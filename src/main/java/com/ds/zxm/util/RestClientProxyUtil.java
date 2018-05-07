package com.ds.zxm.util;

import com.ds.zxm.vo.OnlineImVO;
import org.apache.commons.collections.MapUtils;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class RestClientProxyUtil {
	
	/**
	 * 指定请求header参数类型，返回指定ResponseEntity的POST请求
	 * @Description MediaType遵循Springframework.http;ResponseEntity特供后续处理
	 * @author FangzhouXu
	 * @date 2017年12月26日下午4:16:51
	 * @param url
	 * @param formParams
	 * @param mediaType
	 * @return
	 */
    public static ResponseEntity<String> doPost(String url, Map<String, Object> formParams, MediaType mediaType) {
        try {
            MultiValueMap<String, Object> paraMap = new LinkedMultiValueMap<String, Object>();
            formParams.keySet().stream().forEach(key -> paraMap.add(key, MapUtils.getString(formParams, key, "")));
            HttpHeaders headers =new HttpHeaders();
            headers.setContentType(mediaType);
            HttpEntity<MultiValueMap<String, Object>> reqEntity = new HttpEntity<MultiValueMap<String, Object>>(paraMap, headers);
            return RestClientProxy.getClient().postForEntity(url, reqEntity, String.class);
        } catch (Exception e) {
        }
        
        return null;
        
    }
    
    public static ResponseEntity<String> doExchange(String url, Map<String, Object> formParams, MediaType mediaType, HttpMethod httpMethod) {
        try {
            MultiValueMap<String, Object> paraMap = new LinkedMultiValueMap<String, Object>();
            formParams.keySet().stream().forEach(key -> paraMap.add(key, MapUtils.getString(formParams, key, "")));
            HttpHeaders headers =new HttpHeaders();
            headers.setContentType(mediaType);
            HttpEntity<MultiValueMap<String, Object>> reqEntity = new HttpEntity<MultiValueMap<String, Object>>(paraMap, headers);
            return RestClientProxy.getClient().exchange(url, httpMethod, reqEntity, String.class);
        } catch (Exception e) {
        }
        
        return null;
        
    }
    
    public static ResponseEntity<String> doPost(String url, Object param, MediaType mediaType) {
        try {
            HttpHeaders headers =new HttpHeaders();
            headers.setContentType(mediaType);
            HttpEntity<Object> reqEntity = new HttpEntity<Object>(param, headers);
            return RestClientProxy.getClient().postForEntity(url, reqEntity, String.class);
        } catch (Exception e) {
        }
        return null;
    }

	/**
     * post请求
     * @param url
     * @param formParams
     * @return
     */
    public static String doPost(String url, Map<String, String> formParams) {
        if (MapUtils.isEmpty(formParams)) {
            return doPost(url);
        }
        try {
            MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
            formParams.keySet().stream().forEach(key -> requestEntity.add(key, MapUtils.getString(formParams, key, "")));
            return RestClientProxy.getClient().postForObject(url, requestEntity, String.class);
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * post请求
     * @param url
     * @return
     */
    public static String doPost(String url) {
        try {
            return RestClientProxy.getClient().postForObject(url, HttpEntity.EMPTY, String.class);
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * get请求
     * @param url
     * @return
     */
    public static <T> T doGet(String url,Class<T> tClass) {
        try {
            return RestClientProxy.getClient().getForObject(url, tClass);
        } catch (Exception e) {
        }

        return null;
    }

    public static RestTemplate getRestTemplateClient() {
    	return RestClientProxy.getClient();
    }


    public static void main(String[] args) {

        List<OnlineImVO> list = RestClientProxyUtil.doGet("http://77tj.org/api/tencent/onlineim", List.class);
        System.out.println("done");
    }
}
