package com.ds.zxm.prize;

import com.ds.zxm.model.TCFFCPRIZE;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class GeFiveDwdGenPrize extends BaseGenPrize {
    @Override
    void init() {
        file = new File("ge5File.txt");
        allFile = new File("ge5AllFile.txt");
    }

    @Override
    String getGenPrizeNumsStr(TCFFCPRIZE conPrize,TCFFCPRIZE curPrize) {

        StringBuffer sb = new StringBuffer();
        //当前个位波动，下一期预测个位波动为最近的3个数
        Integer curAdjust = curPrize.getAdjustNum()%10;
        int ge = curPrize.getGe();
        //sb.append(curAdjust);

        for(int i=-2; i < 3; i++) {
            int adjust = (curAdjust + i)%10;
            int geItem = ge + adjust;
            if(geItem < 0) {
                geItem = geItem + 10;
            } else if (geItem >=10) {
                geItem = geItem %10;
            }
            sb.append(""+ geItem + (i != 2 ?"," : ""));
        }
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
