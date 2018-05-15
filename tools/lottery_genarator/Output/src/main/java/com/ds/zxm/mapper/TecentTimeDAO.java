package com.ds.zxm.mapper;

import com.ds.zxm.model.TecentTime;
import com.ds.zxm.model.TecentTimeCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TecentTimeDAO {
    int countByCondition(TecentTimeCondition example);

    int deleteByCondition(TecentTimeCondition example);

    int deleteByPrimaryKey(Long id);

    int insert(TecentTime record);

    int insertSelective(TecentTime record);

    List<TecentTime> selectByCondition(TecentTimeCondition example);

    TecentTime selectByPrimaryKey(Long id);

    int updateByConditionSelective(@Param("record") TecentTime record, @Param("example") TecentTimeCondition example);

    int updateByCondition(@Param("record") TecentTime record, @Param("example") TecentTimeCondition example);

    int updateByPrimaryKeySelective(TecentTime record);

    int updateByPrimaryKey(TecentTime record);
}