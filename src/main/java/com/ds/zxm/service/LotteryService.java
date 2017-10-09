package com.ds.zxm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ds.zxm.mapper.LotteryMapper;
import com.ds.zxm.mapper.UserMapper;
import com.ds.zxm.model.Lottery;
import com.ds.zxm.model.User;

/**
 * Created by zl on 2015/8/27.
 */

@Service
public class LotteryService {

    @Autowired
    private LotteryMapper  lotteryMapper;

    public Lottery getLotteryInfo(){
    	Lottery lottery=lotteryMapper.findLotteryInfo();
        //User user=null;
        return lottery;
    }

}
