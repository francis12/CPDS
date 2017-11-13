package com.ds.zxm.mapper;

import com.ds.zxm.model.ScheduleDO;
import com.ds.zxm.model.ScheduleDOCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ScheduleDAO {
    int countByCondition(ScheduleDOCondition example);

    int deleteByCondition(ScheduleDOCondition example);

    int deleteByPrimaryKey(Long id);

    int insert(ScheduleDO record);

    int insertSelective(ScheduleDO record);

    List<ScheduleDO> selectByCondition(ScheduleDOCondition example);

    ScheduleDO selectByPrimaryKey(Long id);

    int updateByConditionSelective(@Param("record") ScheduleDO record, @Param("example") ScheduleDOCondition example);

    int updateByCondition(@Param("record") ScheduleDO record, @Param("example") ScheduleDOCondition example);

    int updateByPrimaryKeySelective(ScheduleDO record);

    int updateByPrimaryKey(ScheduleDO record);
}