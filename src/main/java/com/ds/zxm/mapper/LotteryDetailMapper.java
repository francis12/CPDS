package com.ds.zxm.mapper;

import com.ds.zxm.model.LotteryDetail;
import com.ds.zxm.model.LotteryDetailCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LotteryDetailMapper {
    int countByCondition(LotteryDetailCondition example);

    int deleteByCondition(LotteryDetailCondition example);

    int deleteByPrimaryKey(Long id);

    int insert(LotteryDetail record);

    int insertSelective(LotteryDetail record);

    List<LotteryDetail> selectByCondition(LotteryDetailCondition example);

    LotteryDetail selectByPrimaryKey(Long id);

    int updateByConditionSelective(@Param("record") LotteryDetail record, @Param("example") LotteryDetailCondition example);

    int updateByCondition(@Param("record") LotteryDetail record, @Param("example") LotteryDetailCondition example);

    int updateByPrimaryKeySelective(LotteryDetail record);

    int updateByPrimaryKey(LotteryDetail record);

	List<LotteryDetail> selectMax(LotteryDetailCondition LotteryDetailCondition);
}