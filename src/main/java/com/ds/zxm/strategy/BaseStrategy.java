package com.ds.zxm.strategy;

import com.ds.zxm.mapper.TCFFCPRIZEDAO;
import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TCFFCPRIZECondition;
import com.ds.zxm.model.TcffcPrizeConverter;
import com.ds.zxm.service.TcffcGenNumsService;
import com.ds.zxm.util.DateUtils;
import com.ds.zxm.util.LotteryUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public abstract class BaseStrategy {

    @Resource
    protected TCFFCPRIZEDAO tcffcprizedao;
    @Resource
    protected TcffcGenNumsService tcffcGenNumsService;
    Logger log = Logger.getLogger(BaseStrategy.class);

    protected static  List<String> allSanXinNums = new ArrayList<>();
    static {
        for(int i = 0; i<10; i++) {
            for(int j = 0; j<10; j++) {
                for(int k = 0; k<10; k++) {
                    String item = "" + i +j + k;
                    allSanXinNums.add(item);
                }
            }
        }
    }

    public abstract boolean isWin(Object calNo, TCFFCPRIZE realNo);

    public abstract Object calBetNum(Date time);
}
