package com.ssc.prizeschedule;

import com.ssc.constants.BaseConstants;
import com.ssc.mapper.CurNoModelDAO;
import com.ssc.mapper.GenPrizeModelDAO;
import com.ssc.mapper.TCFFCPRIZEDAO;
import com.ssc.mapper.TecentTimeDAO;
import com.ssc.model.*;
import com.ssc.service.TcffcGenNumsService;
import com.ssc.util.DateUtils;
import com.ssc.util.LotteryUtil;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Scope("prototype")
@Service
public abstract class BaseGenPrize {


    @Resource
    protected TCFFCPRIZEDAO tcffcprizedao;
    @Resource
    private CurNoModelDAO curNOModelDAO;
    @Resource
    private GenPrizeModelDAO genPrizeModelDAO;
    @Resource
    private TecentTimeDAO tecentTimeDAO;
    @Autowired
    private GenAdjustPrize genAdjustPrize;
    Logger log = Logger.getLogger(TcffcGenNumsService.class);

    protected TCFFCPRIZE genPrize = null;
    protected List<TCFFCPRIZE> genPrizeList = null;
    //是否投注
    protected boolean isToTz = true;
    //是否写入文件
    protected boolean isWrite2File = true;
    //是否同步预测结果到数据库
    protected boolean isSyncGenData = false;
    //是否回测数据模式
    protected boolean isTestDataMode = false;
    File file = null;

    public boolean isTestDataMode() {
        return isTestDataMode;
    }

    public void setTestDataMode(boolean testDataMode) {
        isTestDataMode = testDataMode;
    }

    File allFile = null;
    String genStr = "";
    int curCount = 0;
    protected String wfType ="";
    private Boolean isPrized = false;
    protected String cacheKey = null;


    public Map<String, Object> run(TCFFCPRIZE curPrize) {
        this.init();
        cacheKey = "gen" + this.wfType;
        file = new File(this.wfType+".txt");
        allFile = new File(this.wfType+"-all.txt");

        Map<String, Object> genResult = this.generateNextNums(curPrize);
        return genResult;
    }


    public Map<String, Object> generateNextNums(TCFFCPRIZE curPrize) {
        Map<String, Object> winResult = new HashMap<>();
        try {
            //当前期开奖号码和预测号码
            isPrized = this.isPrized(curPrize);
            winResult.put("isPrized", isPrized);
            Date nextMin = DateUtils.addMinutes(1, curPrize.getTime());
            String nextStr =this.getGenPrizeNumsStr(curPrize);
            String strResult = curPrize.getNo() + "实际：" + curPrize.getPrize() + " " + (isPrized ? "中" : "挂") + "\r\n预测" + TcffcPrizeConverter.genNofromTime(nextMin) + ":" + nextStr;
            writeResult2File(strResult);
            if(isSyncGenData && !isTestDataMode) {
                updateGenPrizeResult(curPrize, nextStr);
            }
            winResult.put("nextStr", nextStr);
        } catch (Exception e) {
            log.error("generateNextNums error", e);
        }
        return winResult;
    }
    abstract void init();
    //conPrize:预测的下期号码，curPrize:当前期实际开出的号码
    abstract String getGenPrizeNumsStr(TCFFCPRIZE curPrize);
    //判断是否中奖
    abstract boolean isPrized(TCFFCPRIZE curPrize);
    //记录预测和开奖结果到文件中
    protected void writeResult2File(String result) {
        try {
            if (isWrite2File){
                FileUtils.writeStringToFile(file, result, false);
                FileUtils.writeStringToFile(allFile, result, true);
            }
        } catch (IOException e) {
            log.error("BaseGenPrize writeResult2File error", e);
        }
    };



    public synchronized void updateGenPrizeResult(TCFFCPRIZE curPirze, String nextStr) {
        GenPrizeModel genPrizeModel = new GenPrizeModel();
        genPrizeModel.setLotteryCode(curPirze.getLotteryCode());
        Date nextMin = DateUtils.addMinutes(1,curPirze.getTime());
        String nextNo = TcffcPrizeConverter.genNofromTime(nextMin);
        genPrizeModel.setNo(nextNo);
        genPrizeModel.setGenPrize(nextStr);

        genPrizeModel.setType(wfType);
        GenPrizeModelCondition condition = new GenPrizeModelCondition();
        condition.createCriteria().andTypeEqualTo(wfType).andNoEqualTo(nextNo);
        int count = genPrizeModelDAO.countByCondition(condition);
        if (count <= 0) {
            genPrizeModelDAO.insert(genPrizeModel);
        }

        GenPrizeModelCondition genPrizeModelCondition = new GenPrizeModelCondition();
        genPrizeModelCondition.createCriteria().andLotteryCodeEqualTo(curPirze.getLotteryCode()).andNoEqualTo(curPirze.getNo()).andTypeEqualTo(wfType);

        List<GenPrizeModel> list = genPrizeModelDAO.selectByCondition(genPrizeModelCondition);
        if (list != null && list.size() > 0) {

            GenPrizeModel lastPrizeModel = new GenPrizeModel();
            lastPrizeModel.setRealPrize(curPirze.getPrize());
            lastPrizeModel.setIsPrized(isPrized ? "已中奖" : "未中奖");
            log.info(curPirze.getNo() + wfType+"将被更新为:" + isPrized);
            genPrizeModelDAO.updateByConditionSelective(lastPrizeModel, genPrizeModelCondition);
        }
    }
}
