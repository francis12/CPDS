package com.ssc.prizeschedule;

import com.ssc.constants.BaseConstants;
import com.ssc.model.TCFFCPRIZE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
@Scope("prototype")
@Service
public class GeBdjcDwdGenPrize extends BaseGenPrize {

    @Autowired
    private GeBdDxDwdGenPrize geBdDxDwdGenPrize;
    @Autowired
    private GeBdDwdGenPrize geBdDwdGenPrize;
    @Override
    void init() {
        wfType= BaseConstants.WF_TYPE_DWD_GE_JC;
    }

    //大小和单双交叉
    @Override
    public String getGenPrizeNumsStr(TCFFCPRIZE curPrize) {

        String dxStr = geBdDxDwdGenPrize.getGenPrizeNumsStr( curPrize);
        String dsStr = geBdDwdGenPrize.getGenPrizeNumsStr(curPrize);

        //取交集
        String[] dxArr = dxStr.split(",");
        String[] dsArr = dsStr.split(",");

//        for(String dxItem : dxArr) {
//            for(String dsItem : dsArr) {
//                if (dxItem.equals(dsItem)) {
//                    sb.append(dxItem+",");
//                }
//            }
//        }
        Set<String> result = new HashSet<>();
        for(String dxItem : dxArr) {
            result.add(dxItem);
        }
        for(String dsItem : dsArr) {
            result.add(dsItem);
        }
        List<String> listResult = new ArrayList<>(result);
        if(listResult.size()>7) {
            listResult = listResult.subList(0,7);
        }
        genStr="";
        for(String item:listResult){
            genStr=genStr+item+",";
        }
        log.info( "交叉预测ben期" + curPrize.getNo()  + ",本期个位：" +  curPrize.getGe() + ",预测下期个位:" + genStr);
        return genStr;
    }
    @Override
    boolean isPrized( TCFFCPRIZE curPrize) {
        boolean result = false;
        if(genStr.indexOf("" + curPrize.getGe()) >= 0) {
            result = true;
        }
        return result;
    }
}
