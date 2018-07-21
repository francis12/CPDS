package com.ds.zxm.prize;

import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.util.LotteryUtil;
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
    String getGenPrizeNumsStr(TCFFCPRIZE conPrize,TCFFCPRIZE curPrize) {
        return LotteryUtil.genPyPost4NumStr(conPrize.getQian()) + "*" + LotteryUtil.genPy4NumStr(conPrize.getBai()) + "*" + LotteryUtil.genPy4NumStr(conPrize.getShi());
    }

    @Override
    boolean isPrized(TCFFCPRIZE genPrize, TCFFCPRIZE curPrize) {
        if (null != genPrize) {
            if (  LotteryUtil.judgeIsmatchBetweenPost4(genPrize.getQian(), curPrize.getQian())
                    &&LotteryUtil.judgeIsmatchBetween4(genPrize.getBai(), curPrize.getBai())
                    &&LotteryUtil.judgeIsmatchBetween4(genPrize.getShi(), curPrize.getShi())) {
                return true;
            }
        }
        return false;
    }
}
