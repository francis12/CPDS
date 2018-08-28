package com.ds.zxm.prize;

import com.ds.zxm.constants.BaseConstants;
import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TCFFCPRIZECondition;
import com.ds.zxm.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class GeBdjcDwdGenPrize extends BaseGenPrize {

    @Autowired
    private GeBdDxDwdGenPrize geBdDxDwdGenPrize;
    @Autowired
    private GeBdDwdGenPrize geBdDwdGenPrize;
    @Override
    void init() {
        file = new File("geBdJCFile.txt");
        allFile = new File("geBdJCAllFile.txt");
        wfType= BaseConstants.WF_TYPE_DWD_GE_JC;
    }

    //大小和单双交叉
    @Override
    public String getGenPrizeNumsStr(TCFFCPRIZE conPrize,TCFFCPRIZE curPrize) {

        String dxStr = geBdDxDwdGenPrize.getGenPrizeNumsStr(conPrize, curPrize);
        String dsStr = geBdDwdGenPrize.getGenPrizeNumsStr(conPrize, curPrize);

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
    boolean isPrized(TCFFCPRIZE genPrize, TCFFCPRIZE curPrize) {
        boolean result = false;
        if (null != genPrize) {
            if(genStr.indexOf("" + curPrize.getGe()) >= 0) {
                result = true;
            }
        }
        return result;
    }
}
