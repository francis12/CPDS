package com.ssc.mapper;

import com.ssc.model.CurNoModel;
import com.ssc.model.CurNoModelCondition;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CurNoModelDAO {
    int countByCondition(CurNoModelCondition example);

    int deleteByCondition(CurNoModelCondition example);

    int deleteByPrimaryKey(Long id);

    int insert(CurNoModel record);

    int insertSelective(CurNoModel record);

    List<CurNoModel> selectByCondition(CurNoModelCondition example);

    CurNoModel selectByPrimaryKey(Long id);

    int updateByConditionSelective(@Param("record") CurNoModel record, @Param("example") CurNoModelCondition example);

    int updateByCondition(@Param("record") CurNoModel record, @Param("example") CurNoModelCondition example);

    int updateByPrimaryKeySelective(CurNoModel record);

    int updateByPrimaryKey(CurNoModel record);
}