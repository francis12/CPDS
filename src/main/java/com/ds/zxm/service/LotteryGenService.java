package com.ds.zxm.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ds.zxm.mapper.CurNOModelDAO;
import com.ds.zxm.mapper.GenPrizeModelDAO;
import com.ds.zxm.mapper.TCFFCPRIZEDAO;
import com.ds.zxm.model.*;
import com.ds.zxm.util.DateUtils;
import com.ds.zxm.util.HttpUtil;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class LotteryGenService {

    @Resource
    private TCFFCPRIZEDAO tcffcprizedao;
    @Resource
    private CurNOModelDAO curNOModelDAO;
    @Resource
    private GenPrizeModelDAO genPrizeModelDAO;
    @Resource
    private TcffcGenNumsService tcffcGenNumsService;

    Logger log = Logger.getLogger(LotteryGenService.class);

    public void initCurNO() {
        CurNOModel curNOModel = new CurNOModel();
        curNOModel.setLotteryCode("TCFFC");
        String nextNO = TcffcPrizeConverter.genNofromTime(new Date());
        curNOModel.setNextNo(nextNO);
        CurNOModelCondition curNOModelCondition = new CurNOModelCondition();
        curNOModelCondition.createCriteria().andLotteryCodeEqualTo("TCFFC");
        int cnt = curNOModelDAO.countByCondition(curNOModelCondition);
        if (cnt > 0) {
            curNOModelDAO.updateByConditionSelective(curNOModel, curNOModelCondition);
        } else {
            curNOModelDAO.insert(curNOModel);
        }
    }

    public GenPrizeModel getLatestGenPrize(String lotteryCode) {
        GenPrizeModel result = null;
        CurNOModelCondition curNOModelCondition = new CurNOModelCondition();
        curNOModelCondition.createCriteria().andLotteryCodeEqualTo(lotteryCode);
        List<CurNOModel> curNOModelList = curNOModelDAO.selectByCondition(curNOModelCondition);

        if (null != curNOModelList && curNOModelList.size() != 0) {
            String no = curNOModelList.get(0).getNextNo();
            GenPrizeModelCondition genPrizeModelCondition = new GenPrizeModelCondition();
            genPrizeModelCondition.createCriteria().andNoEqualTo(no);

            List<GenPrizeModel> genPrizeModelList = genPrizeModelDAO.selectByCondition(genPrizeModelCondition);
            if (null != genPrizeModelList && genPrizeModelList.size() > 0) {
                result = genPrizeModelList.get(0);
            }
        }
        return result;
    }
}
