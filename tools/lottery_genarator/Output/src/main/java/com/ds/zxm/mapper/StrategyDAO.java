package com.ds.zxm.mapper;

import com.ds.zxm.model.StrategyDO;
import com.ds.zxm.model.StrategyDOCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StrategyDAO {
    int countByCondition(StrategyDOCondition example);

    int deleteByCondition(StrategyDOCondition example);

    int deleteByPrimaryKey(Long id);

    int insert(StrategyDO record);

    int insertSelective(StrategyDO record);

    List<StrategyDO> selectByCondition(StrategyDOCondition example);

    StrategyDO selectByPrimaryKey(Long id);

    int updateByConditionSelective(@Param("record") StrategyDO record, @Param("example") StrategyDOCondition example);

    int updateByCondition(@Param("record") StrategyDO record, @Param("example") StrategyDOCondition example);

    int updateByPrimaryKeySelective(StrategyDO record);

    int updateByPrimaryKey(StrategyDO record);
}