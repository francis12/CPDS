package com.ssc.prizeschedule;

import com.ssc.model.TCFFCPRIZE;
import com.ssc.util.LotteryUtil;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ZhongsanGenPrize extends BaseGenPrize {
    //中3
    @Override
    void init() {
        file = new File("zhong3File.txt");
        allFile = new File("zhong3AllFile.txt");
    }

    @Override
    String getGenPrizeNumsStr(TCFFCPRIZE curPrize) {
       // return LotteryUtil.genPyPost4NumStr(conPrize.getQian()) + "*" + LotteryUtil.genPy4NumStr(conPrize.getBai()) + "*" + LotteryUtil.genPy4NumStr(conPrize.getShi());
        return null;
    }

    @Override
    boolean isPrized( TCFFCPRIZE curPrize) {
        /*if (null != genPrize) {
            if (  LotteryUtil.judgeIsmatchBetweenPost4(genPrize.getQian(), curPrize.getQian())
                    &&LotteryUtil.judgeIsmatchBetween4(genPrize.getBai(), curPrize.getBai())
                    &&LotteryUtil.judgeIsmatchBetween4(genPrize.getShi(), curPrize.getShi())) {
                return true;
            }
        }*/
        return false;
    }
}
