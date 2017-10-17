package com.ds.zxm.mapper;

import com.ds.zxm.model.BetDO;
import com.ds.zxm.model.BetDOCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BetDAO {
    int countByCondition(BetDOCondition example);

    int deleteByCondition(BetDOCondition example);

    int deleteByPrimaryKey(Long id);

    int insert(BetDO record);

    int insertSelective(BetDO record);

    List<BetDO> selectByExampleWithBLOBs(BetDOCondition example);

    List<BetDO> selectByCondition(BetDOCondition example);

    BetDO selectByPrimaryKey(Long id);

    int updateByConditionSelective(@Param("record") BetDO record, @Param("example") BetDOCondition example);

    int updateByExampleWithBLOBs(@Param("record") BetDO record, @Param("example") BetDOCondition example);

    int updateByCondition(@Param("record") BetDO record, @Param("example") BetDOCondition example);

    int updateByPrimaryKeySelective(BetDO record);

    int updateByPrimaryKeyWithBLOBs(BetDO record);

    int updateByPrimaryKey(BetDO record);
}