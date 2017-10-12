package com.ds.zxm.service;

import com.ds.zxm.mapper.LotteryDetailMapper;
import com.ds.zxm.model.LotteryDetail;
import com.ds.zxm.model.LotteryDetailCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotteryDetailService {

    @Autowired
    private LotteryDetailMapper lotteryDetailMapper;

    public List<LotteryDetail> queryLotteryDetails() {

        LotteryDetailCondition lotteryDetailCondition = new LotteryDetailCondition();
        lotteryDetailCondition.createCriteria().andLotteryCodeEqualTo("");
        return lotteryDetailMapper.selectByCondition(lotteryDetailCondition);
    }
}
