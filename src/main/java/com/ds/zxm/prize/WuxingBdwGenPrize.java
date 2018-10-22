package com.ds.zxm.prize;

import com.ds.zxm.constants.BaseConstants;
import com.ds.zxm.model.TCFFCPRIZE;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class WuxingBdwGenPrize extends BaseGenPrize {
    //万位定位胆
    @Override
    void init() {
        file = new File("wuXinBdwFile.txt");
        allFile = new File("wuXinBdwAllFile.txt");
        this.wfType = BaseConstants.WF_TYPE_WXBDW_HZ;
    }

    @Override
    String getGenPrizeNumsStr(TCFFCPRIZE conPrize,TCFFCPRIZE curPrize) {
        Integer wan = curPrize.getWan();
        Integer qian = curPrize.getQian();
        Integer bai = curPrize.getBai();
        Integer shi = curPrize.getShi();
        Integer ge = curPrize.getGe();

        int heWei = (wan + qian + bai + shi + ge) % 10;
        String result = "";
        if (heWei != 0) {
            result = String.valueOf(10-heWei);
        } else {
            result = String.valueOf(heWei);
        }
        this.genStr = result;
        return result;
    }

    @Override
    boolean isPrized(TCFFCPRIZE genPrize, TCFFCPRIZE curPrize) {
        boolean result = false;
        if (null != genPrize) {
            if(curPrize.getPrize().indexOf(this.genStr) >= 0) {
                result = true;
            }
        }
        return result;
    }
}
