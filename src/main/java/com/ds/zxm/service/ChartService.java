package com.ds.zxm.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ds.zxm.mapper.TCFFCPRIZEDAO;
import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TCFFCPRIZECondition;
import com.ds.zxm.model.TcffcPrizeConverter;
import com.ds.zxm.util.DateUtils;
import com.ds.zxm.util.DsUtil;
import com.ds.zxm.util.HttpUtil;
import com.ds.zxm.util.RestClientProxyUtil;
import com.ds.zxm.vo.OnlineImVO;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class ChartService {

    @Resource
    private TCFFCPRIZEDAO tcffcprizedao;
    @Resource
    private TcffcGenNumsService tcffcGenNumsService;
    Logger log = Logger.getLogger(ChartService.class);


    //                "2013/1/24", "2013/1/25", "2013/1/28", "2013/1/29", "2013/1/30","2013/1/24", "2013/1/25", "2013/1/28", "2013/1/29", "2013/1/30","2013/1/24", "2013/1/25", "2013/1/28", "2013/1/29", "2013/1/30"
//[1,2,1,2],
//                [2,3,2,3],
//                        [3,4,3,4],
//                        [4,3,3,4],
//                        [3,4,3,4],
//                        [1,2,1,2],
//                        [2,3,2,3],
//                        [5,6,5,6],
//                        [4,3,3,4],
//                        [3,4,3,4],
//                        [1,2,1,2],
//                        [2,3,2,3],
//                        [3,4,3,4],
//                        [4,3,3,4],
//                        [3,4,3,4]
    public Map<String, Object> queryTecentOnlineData2Option(Integer limit) throws ParseException {

        Date date = DateUtils.getBaiduCurTime();
        Date minTime = DateUtils.addMinutes(-limit, date);

        TCFFCPRIZECondition tcffcprizeCondition = new TCFFCPRIZECondition();
        tcffcprizeCondition.createCriteria().andTimeGreaterThanOrEqualTo(minTime);
        tcffcprizeCondition.setOrderByClause("time asc");

        List<TCFFCPRIZE> list = tcffcprizedao.selectByCondition(tcffcprizeCondition);

        List<String> noList = new ArrayList<>();
        List<Integer> adjustList = new ArrayList<>();
        List<Integer[]> adjustDsList = new ArrayList();

        if(null != list && list.size() > 0) {
            for(TCFFCPRIZE item : list) {
                noList.add(item.getNo());
                int last1Adjust = item.getAdjustNum()%10;
                //0：双，1：单
                adjustList.add(last1Adjust%2==0?0:1);
            }
            //转成K线数组
            adjustDsList = this.convert2KArray(adjustList);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("xAxis", noList);
        map.put("series", adjustDsList);
        return map;
    }

    //正常数据转成K线图数据
    // 开盘，收盘，最低，最高
    //0为双，1位单。开双为正
    private List<Integer[]> convert2KArray(List<Integer> list) {
        if(null == list || list.size() == 0){
            return null;
        }
        List<Integer[]> result = new ArrayList<>();
        int preKp,preSp = 0;
        int size = list.size();
        for(int i=0;i < size; i++) {
            int curKp = preSp;
            int curSp = 0;
            int max,min;
            int adjustDs = list.get(i);
            if(adjustDs == 0) {
                curSp = curKp + 1;
                max = curSp;
                min = curKp;
            } else {
                curSp = curKp - 1;
                max = curKp;
                min = curSp;
            }
            Integer[] cur = {curKp, curSp, min, max};
            result.add(cur);
            preKp = curKp;
            preSp = curSp;
        }
        return result;
    }
}
