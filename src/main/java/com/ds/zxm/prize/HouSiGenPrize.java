package com.ds.zxm.prize;

import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TCFFCPRIZECondition;
import com.ds.zxm.model.TcffcPrizeConverter;
import com.ds.zxm.util.DateUtils;
import com.ds.zxm.util.LotteryUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;

@Service
public class HouSiGenPrize extends BaseGenPrize {
    //前4
    @Override
    void init() {
        file = new File("hou4File.txt");
        allFile = new File("hou4AllFile.txt");
    }

    @Override
    public String getGenPrizeNumsStr(TCFFCPRIZE conPrize,TCFFCPRIZE curPrize) {
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
