package com.lottery.dao;

import com.lottery.model.LotteryDetailDO;
import com.lottery.model.LotteryDetailDOCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LotteryDetailDAO {
    int countByCondition(LotteryDetailDOCondition example);

    int deleteByCondition(LotteryDetailDOCondition example);

    int deleteByPrimaryKey(Long id);

    int insert(LotteryDetailDO record);

    int insertSelective(LotteryDetailDO record);

    List<LotteryDetailDO> selectByCondition(LotteryDetailDOCondition example);

    LotteryDetailDO selectByPrimaryKey(Long id);

    int updateByConditionSelective(@Param("record") LotteryDetailDO record, @Param("example") LotteryDetailDOCondition example);

    int updateByCondition(@Param("record") LotteryDetailDO record, @Param("example") LotteryDetailDOCondition example);

    int updateByPrimaryKeySelective(LotteryDetailDO record);

    int updateByPrimaryKey(LotteryDetailDO record);

	List<LotteryDetailDO> selectMax(LotteryDetailDOCondition lotteryDetailDOCondition);
}