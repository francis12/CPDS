package com.ds.zxm.strategy;

import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TCFFCPRIZECondition;
import com.ds.zxm.model.TcffcPrizeConverter;
import com.ds.zxm.prize.QianSiGenPrize;
import com.ds.zxm.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QianSiStrategy extends BaseStrategy{

    /**
     * 前4
     * @return
     */

    @Autowired
    private QianSiGenPrize qianSiGenPrize;
    @Override
    public boolean isWin(Object calNo, TCFFCPRIZE realNo) {
        String result = qianSiGenPrize.getGenPrizeNumsStr(realNo);
        boolean isPrized = result.indexOf(realNo.getPrize().substring(0,4)) > 0;
        return isPrized;

    }

    @Override
    public Object calBetNum(Date time) {
        String[] numArr = null;
        try {
            //1.计算理论预测数据 2.数据库读取实际开奖数据,3比较是否
            String no = TcffcPrizeConverter.genNofromTime(time);
            TCFFCPRIZECondition condition = new TCFFCPRIZECondition();
            condition.createCriteria().andTimeEqualTo(time);
            List<TCFFCPRIZE> prizeList = tcffcprizedao.selectByCondition(condition);
            if (null != prizeList && prizeList.size() > 0) {
                TCFFCPRIZE cur = prizeList.get(0);
                String nums = qianSiGenPrize.getGenPrizeNumsStr(cur);
                numArr = nums.split(" ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return numArr;
    }
}
