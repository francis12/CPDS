package com.ssc.mapper;

import com.ssc.model.GenPrizeModel;
import com.ssc.model.GenPrizeModelCondition;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface GenPrizeModelDAO {
    int countByCondition(GenPrizeModelCondition example);

    int deleteByCondition(GenPrizeModelCondition example);

    int deleteByPrimaryKey(Long id);

    int insert(GenPrizeModel record);

    int insertSelective(GenPrizeModel record);

    List<GenPrizeModel> selectByCondition(GenPrizeModelCondition example);

    GenPrizeModel selectByPrimaryKey(Long id);

    int updateByConditionSelective(@Param("record") GenPrizeModel record, @Param("example") GenPrizeModelCondition example);

    int updateByCondition(@Param("record") GenPrizeModel record, @Param("example") GenPrizeModelCondition example);

    int updateByPrimaryKeySelective(GenPrizeModel record);

    int updateByPrimaryKey(GenPrizeModel record);
}