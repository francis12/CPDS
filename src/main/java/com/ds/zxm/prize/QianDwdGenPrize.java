package com.ds.zxm.prize;

import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.util.LotteryUtil;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class QianDwdGenPrize extends BaseGenPrize {
    //千位定位胆
    @Override
    void init() {
        file = new File("qianFile.txt");
        allFile = new File("qianAllFile.txt");
    }

    @Override
    String getGenPrizeNumsStr(TCFFCPRIZE conPrize) {
        return LotteryUtil.genPy3NumStr(conPrize.getQian());
    }

    @Override
    boolean isPrized(TCFFCPRIZE genPrize, TCFFCPRIZE curPrize) {
        if (null != genPrize) {
            if ( LotteryUtil.judgeIsmatchBetween3(genPrize.getQian(), curPrize.getQian())) {
                return true;
            }
        }
        return false;
    }
}
