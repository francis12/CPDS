package com.ds.zxm.mapper;

import com.ds.zxm.model.TecentTimeDO;
import com.ds.zxm.model.TecentTimeDOCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TecentTimeDAO {
    int countByCondition(TecentTimeDOCondition example);

    int deleteByCondition(TecentTimeDOCondition example);

    int deleteByPrimaryKey(Long id);

    int insert(TecentTimeDO record);

    int insertSelective(TecentTimeDO record);

    List<TecentTimeDO> selectByCondition(TecentTimeDOCondition example);

    TecentTimeDO selectByPrimaryKey(Long id);

    int updateByConditionSelective(@Param("record") TecentTimeDO record, @Param("example") TecentTimeDOCondition example);

    int updateByCondition(@Param("record") TecentTimeDO record, @Param("example") TecentTimeDOCondition example);

    int updateByPrimaryKeySelective(TecentTimeDO record);

    int updateByPrimaryKey(TecentTimeDO record);
}