package com.ds.zxm.service;

import com.ds.zxm.mapper.CurNoModelDAO;
import com.ds.zxm.mapper.GenPrizeModelDAO;
import com.ds.zxm.mapper.TCFFCPRIZEDAO;
import com.ds.zxm.model.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class LotteryGenService {

    @Resource
    private TCFFCPRIZEDAO tcffcprizedao;
    @Resource
    private CurNoModelDAO curNOModelDAO;
    @Resource
    private GenPrizeModelDAO genPrizeModelDAO;
    @Resource
    private TcffcGenNumsService tcffcGenNumsService;

    Logger log = Logger.getLogger(LotteryGenService.class);

    public void initCurNO() {
        CurNoModel curNOModel = new CurNoModel();
        curNOModel.setLotteryCode("TCFFC");
        String nextNO = TcffcPrizeConverter.genNofromTime(new Date());
        curNOModel.setNextNo(nextNO);
        CurNoModelCondition curNOModelCondition = new CurNoModelCondition();
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
        CurNoModelCondition curNOModelCondition = new CurNoModelCondition();
        curNOModelCondition.createCriteria().andLotteryCodeEqualTo(lotteryCode);
        List<CurNoModel> curNOModelList = curNOModelDAO.selectByCondition(curNOModelCondition);

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
    public GenPrizeModel getLatestGenPrize(Map<String, Object> map) {
        GenPrizeModel result = null;
        CurNoModelCondition curNOModelCondition = new CurNoModelCondition();
        String lotteryCode =(String) map.get("lotteryCode");
        String type =(String) map.get("type");

        CurNoModelCondition.Criteria criteria = curNOModelCondition.createCriteria();
        criteria.andLotteryCodeEqualTo(lotteryCode);
        List<CurNoModel> curNOModelList = curNOModelDAO.selectByCondition(curNOModelCondition);

        if (null != curNOModelList && curNOModelList.size() != 0) {
            String no = curNOModelList.get(0).getNextNo();
            GenPrizeModelCondition genPrizeModelCondition = new GenPrizeModelCondition();
            genPrizeModelCondition.createCriteria().andNoEqualTo(no).andTypeEqualTo(type);

            List<GenPrizeModel> genPrizeModelList = genPrizeModelDAO.selectByCondition(genPrizeModelCondition);

            String no2 = curNOModelList.get(0).getCurNo();
            GenPrizeModelCondition genPrizeModelCondition2 = new GenPrizeModelCondition();
            genPrizeModelCondition2.createCriteria().andNoEqualTo(no2).andTypeEqualTo(type);
            List<GenPrizeModel> genPrizeModelList2 = genPrizeModelDAO.selectByCondition(genPrizeModelCondition2);

            if (null != genPrizeModelList && genPrizeModelList.size() > 0) {
                result = genPrizeModelList.get(0);
                if (null != genPrizeModelList2 && genPrizeModelList2.size() > 0) {
                    result.setIsPrized(genPrizeModelList2.get(0).getIsPrized());
                }
            }
        }
        return result;
    }
}
