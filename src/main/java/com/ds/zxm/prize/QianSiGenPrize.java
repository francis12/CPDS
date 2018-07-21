package com.ds.zxm.prize;

import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TCFFCPRIZECondition;
import com.ds.zxm.model.TcffcPrizeConverter;
import com.ds.zxm.util.DateUtils;
import com.ds.zxm.util.LotteryUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class QianSiGenPrize extends BaseGenPrize {
    //前4
    @Override
    void init() {
        file = new File("qian4File.txt");
        allFile = new File("qian4AllFile.txt");
    }

    @Override
    public String getGenPrizeNumsStr(TCFFCPRIZE conPrize,TCFFCPRIZE curPrize) {
        StringBuilder sb = new StringBuilder();

        if (conPrize != null) {
            List<TCFFCPRIZE> tcffcprizeList = LotteryUtil.genNextWuXingPrizeByGen2(conPrize);
            Collections.sort(tcffcprizeList);

            genPrizeList = tcffcprizeList;
            Set<String> siXinSet = new HashSet<>();
            if(null != tcffcprizeList && tcffcprizeList.size() > 0) {
                tcffcprizeList.stream().forEach(item -> {
                    siXinSet.add(item.getPrize().substring(0,4));
                });
            }
            siXinSet.stream().forEach(item -> {
                sb.append(item + " ");
            });
        }
        return sb.toString();
    }

    @Override
    boolean isPrized(TCFFCPRIZE genPrize, TCFFCPRIZE curPrize) {
        String result = this.getGenPrizeNumsStr(genPrize, curPrize);
        boolean isPrized = result.indexOf(curPrize.getPrize().substring(0,4)) > 0;
        return isPrized;
    }
}
