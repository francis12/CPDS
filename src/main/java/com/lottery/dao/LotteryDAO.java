package com.lottery.dao;

import com.lottery.model.LotteryDO;
import com.lottery.model.LotteryDOCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LotteryDAO {
    int countByCondition(LotteryDOCondition example);

    int deleteByCondition(LotteryDOCondition example);

    int deleteByPrimaryKey(Long id);

    int insert(LotteryDO record);

    int insertSelective(LotteryDO record);

    List<LotteryDO> selectByCondition(LotteryDOCondition example);

    LotteryDO selectByPrimaryKey(Long id);

    int updateByConditionSelective(@Param("record") LotteryDO record, @Param("example") LotteryDOCondition example);

    int updateByCondition(@Param("record") LotteryDO record, @Param("example") LotteryDOCondition example);

    int updateByPrimaryKeySelective(LotteryDO record);

    int updateByPrimaryKey(LotteryDO record);
}