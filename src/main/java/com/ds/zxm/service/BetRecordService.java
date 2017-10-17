package com.ds.zxm.service;

import com.ds.zxm.mapper.BetRecordDAO;
import com.ds.zxm.mapper.UserMapper;
import com.ds.zxm.model.BetRecordDO;
import com.ds.zxm.model.BetRecordDOCondition;
import com.ds.zxm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zl on 2015/8/27.
 */

@Service
public class BetRecordService {

    @Autowired
    private BetRecordDAO betRecordDAO;

    public int insert(BetRecordDO betRecord){
        return betRecordDAO.insert(betRecord);
    }

    public int update(BetRecordDO betRecord, BetRecordDOCondition betRecordDOCondition){
        return betRecordDAO.updateByConditionSelective(betRecord, betRecordDOCondition);
    }
    public List<BetRecordDO> queryList(BetRecordDOCondition betRecordDOCondition){
        return betRecordDAO.selectByCondition(betRecordDOCondition);
    }

}
