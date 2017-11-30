package com.ds.zxm.mapper;

import com.ds.zxm.model.TecentOnlineDO;
import com.ds.zxm.model.TecentOnlineDOCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TecentOnlineDAO {
    int countByCondition(TecentOnlineDOCondition example);

    int deleteByCondition(TecentOnlineDOCondition example);

    int deleteByPrimaryKey(Long id);

    int insert(TecentOnlineDO record);

    int insertSelective(TecentOnlineDO record);

    List<TecentOnlineDO> selectByCondition(TecentOnlineDOCondition example);

    TecentOnlineDO selectByPrimaryKey(Long id);

    int updateByConditionSelective(@Param("record") TecentOnlineDO record, @Param("example") TecentOnlineDOCondition example);

    int updateByCondition(@Param("record") TecentOnlineDO record, @Param("example") TecentOnlineDOCondition example);

    int updateByPrimaryKeySelective(TecentOnlineDO record);

    int updateByPrimaryKey(TecentOnlineDO record);
}