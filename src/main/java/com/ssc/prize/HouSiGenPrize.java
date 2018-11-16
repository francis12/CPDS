package com.ssc.prize;

import com.ssc.model.TCFFCPRIZE;
import com.ssc.util.LotteryUtil;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class HouSiGenPrize extends BaseGenPrize {
    //前4
    @Override
    void init() {
        file = new File("hou4File.txt");
        allFile = new File("hou4AllFile.txt");
    }

    @Override
    public String getGenPrizeNumsStr(TCFFCPRIZE conPrize, TCFFCPRIZE curPrize) {
        return LotteryUtil.genPy4AllNumStr(conPrize.getQian()) + "*" + LotteryUtil.genPy4AllNumStr(conPrize.getBai()) + "*" + LotteryUtil.genPy4AllNumStr(conPrize.getShi()) + "*" + LotteryUtil.genPy4AllNumStr(conPrize.getGe());
    }

    @Override
    boolean isPrized(TCFFCPRIZE genPrize, TCFFCPRIZE curPrize) {
        if (null != genPrize) {
            if ( LotteryUtil.judgeIsmatchBetweenAll4(genPrize.getQian(), curPrize.getQian())
                    && LotteryUtil.judgeIsmatchBetweenAll4(genPrize.getBai(), curPrize.getBai())
                    && LotteryUtil.judgeIsmatchBetweenAll4(genPrize.getShi(), curPrize.getShi())
                    && LotteryUtil.judgeIsmatchBetweenAll4(genPrize.getGe(), curPrize.getGe())) {
                return true;
            }
        }
        return false;
    }
}
