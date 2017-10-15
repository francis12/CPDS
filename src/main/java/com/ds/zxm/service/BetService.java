package com.ds.zxm.service;

import com.ds.zxm.mapper.BetDAO;
import com.ds.zxm.mapper.LotteryDetailMapper;
import com.ds.zxm.model.BetDO;
import com.ds.zxm.model.BetDOCondition;
import com.ds.zxm.model.LotteryDetail;
import com.ds.zxm.model.LotteryDetailCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetService {

    @Autowired
    private BetDAO betDAO;

    public List<BetDO> queryBetList(BetDOCondition betDOCondition) {
        return betDAO.selectByExampleWithBLOBs(betDOCondition);
    }
    //查询当期curNo内的计划
    public List<BetDO> queryBetUnprizeInfo(BetDO betDO, String curNo) {
        BetDOCondition betDOCondition = new BetDOCondition();
        betDOCondition.createCriteria().andBetTypeEqualTo(betDO.getBetType()).andStartNoLessThanOrEqualTo(curNo)
                .andEndNoGreaterThanOrEqualTo(curNo).andLotteryCodeEqualTo(betDO.getLotteryCode()).andStatusEqualTo(betDO.getStatus())
                ;
        return betDAO.selectByExampleWithBLOBs(betDOCondition);
    }

    public void insert(BetDO bet) {
        betDAO.insert(bet);
    }

    public int updateBetDO(BetDO bet, BetDOCondition bdd) {
       return betDAO.updateByExampleWithBLOBs(bet, bdd);
    }
}
