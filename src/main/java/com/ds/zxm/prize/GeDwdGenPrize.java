package com.ds.zxm.prize;

import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.util.LotteryUtil;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class GeDwdGenPrize extends BaseGenPrize {
    @Override
    void init() {
        file = new File("geFile.txt");
        allFile = new File("geAllFile.txt");
    }

    @Override
    String getGenPrizeNumsStr(TCFFCPRIZE conPrize,TCFFCPRIZE curPrize) {

        StringBuffer sb = new StringBuffer();
        //当前个位波动，下一期预测个位波动为最近的3个数
        Integer curAdjust = curPrize.getAdjustNum()%10;
        int ge = curPrize.getGe();
        sb.append(curAdjust);

        /*for(int i=-3; i < 5; i++) {
            int adjust = curAdjust + i;
            int geItem = ge + adjust;
            if(geItem < 0) {
                geItem = geItem + 10;
            } else if (geItem >=10) {
                geItem = geItem %10;
            }
            sb.append(""+ geItem + (i != 4 ?"," : ""));
        }*/
        log.info(curPrize.getNo() + "波动：" + curPrize.getAdjustNum() + ",个位：" + curPrize.getGe() + ",计算下一期个位：" + sb.toString());
        genStr = sb.toString();
        return sb.toString();
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
