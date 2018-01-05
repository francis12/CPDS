package com.ds.zxm.mapper;

import com.ds.zxm.model.StrategyDetailDO;
import com.ds.zxm.model.StrategyDetailDOCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StrategyDetailDAO {
    int countByCondition(StrategyDetailDOCondition example);

    int deleteByCondition(StrategyDetailDOCondition example);

    int deleteByPrimaryKey(Long id);

    int insert(StrategyDetailDO record);
    int insertBatch(List<StrategyDetailDO> list);

    int insertSelective(StrategyDetailDO record);

    List<StrategyDetailDO> selectByCondition(StrategyDetailDOCondition example);

    StrategyDetailDO selectByPrimaryKey(Long id);

    int updateByConditionSelective(@Param("record") StrategyDetailDO record, @Param("example") StrategyDetailDOCondition example);

    int updateByCondition(@Param("record") StrategyDetailDO record, @Param("example") StrategyDetailDOCondition example);

    int updateByPrimaryKeySelective(StrategyDetailDO record);

    int updateByPrimaryKey(StrategyDetailDO record);
}