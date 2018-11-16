package com.ssc.strategy;

import com.ssc.mapper.TCFFCPRIZEDAO;
import com.ssc.model.TCFFCPRIZE;
import com.ssc.service.TcffcGenNumsService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
