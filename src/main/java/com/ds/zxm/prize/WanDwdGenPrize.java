package com.ds.zxm.prize;

import com.ds.zxm.constants.BaseConstants;
import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.util.LotteryUtil;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class WanDwdGenPrize extends BaseGenPrize {
    //万位定位胆
    @Override
    void init() {
        file = new File("wanFile.txt");
        allFile = new File("wanAllFile.txt");
        this.wfType = BaseConstants.WF_TYPE_DWD_WAN3_HE;
    }

    @Override
    String getGenPrizeNumsStr(TCFFCPRIZE conPrize,TCFFCPRIZE curPrize) {
        Integer wan = curPrize.getWan();
        Integer qian = curPrize.getQian();
        Integer bai = curPrize.getBai();

        int heWei = (wan + qian + bai) % 10;
        String result = "";
        if (heWei >= 5) {
            result =  "0 1 2 3 4";
        } else {
            result =  "5 6 7 8 9";
        }
        this.genStr = result;
        return result;
    }

    @Override
    boolean isPrized(TCFFCPRIZE genPrize, TCFFCPRIZE curPrize) {
        boolean result = false;
        if (null != genPrize) {
            if(this.genStr.indexOf(curPrize.getWan()) >= 0 ) {
                result = true;
            }
        }
        return result;
    }
}
