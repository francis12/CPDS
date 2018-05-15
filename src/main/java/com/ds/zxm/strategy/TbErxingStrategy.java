package com.ds.zxm.strategy;

import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TCFFCPRIZECondition;
import com.ds.zxm.model.TcffcPrizeConverter;
import com.ds.zxm.util.DateUtils;
import com.ds.zxm.util.LotteryUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class TbErxingStrategy extends BaseStrategy{

    /**
     * 二星十注推波小概率
     * @param calNo
     * @param realNo
     * @return
     */

    static List<String> allErxing = new ArrayList<>();
    static List<String> allCombineErxing = new ArrayList<>();

    HashSet<String> existErxing = new HashSet<>();
    static {
        for(int i=0; i <10;i++) {
            for(int j=0; j < 10; j++) {
                allErxing.add(i + "" + j);
            }
        }
    }

    static {
        for (int i = 0; i < allErxing.size();i++) {
            for (int j = i+1; j < allErxing.size();j++) {
                for (int k = 0; k < allErxing.size();k++) {

                }
            }
        }
    }

    @Override
    public boolean isWin(Object calNo, TCFFCPRIZE realNo) {
        //判断再未来一万期内出现4连中
        return false;
    }

    @Override
    public TCFFCPRIZE calBetNum(Date time) {
        try {
            //1.计算理论预测数据 2.数据库读取实际开奖数据,3比较是否
            String no = TcffcPrizeConverter.genNofromTime(time);
            TCFFCPRIZECondition condition = new TCFFCPRIZECondition();

            Date startDate = DateUtils.addMinutes(-10000,time);
            condition.createCriteria().andTimeBetween(startDate, time);
            List<TCFFCPRIZE> prizeList = tcffcprizedao.selectByCondition(condition);
            prizeList.stream().forEach(prize -> {
                existErxing.add(prize.getShi() + "" + prize.getGe());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
