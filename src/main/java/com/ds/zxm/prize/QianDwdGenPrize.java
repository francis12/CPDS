package com.ds.zxm.prize;

import com.ds.zxm.mapper.CurNOModelDAO;
import com.ds.zxm.mapper.GenPrizeModelDAO;
import com.ds.zxm.mapper.TCFFCPRIZEDAO;
import com.ds.zxm.mapper.TecentTimeDAO;
import com.ds.zxm.model.*;
import com.ds.zxm.service.TcffcGenNumsService;
import com.ds.zxm.util.DateUtils;
import com.ds.zxm.util.LotteryUtil;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

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
