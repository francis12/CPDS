package com.ds.zxm.mapper;

import com.ds.zxm.model.BetRecordDO;
import com.ds.zxm.model.BetRecordDOCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BetRecordDAO {
    int countByCondition(BetRecordDOCondition example);

    int deleteByCondition(BetRecordDOCondition example);

    int deleteByPrimaryKey(Long id);

    int insert(BetRecordDO record);

    int insertSelective(BetRecordDO record);

    List<BetRecordDO> selectByCondition(BetRecordDOCondition example);

    BetRecordDO selectByPrimaryKey(Long id);

    int updateByConditionSelective(@Param("record") BetRecordDO record, @Param("example") BetRecordDOCondition example);

    int updateByCondition(@Param("record") BetRecordDO record, @Param("example") BetRecordDOCondition example);

    int updateByPrimaryKeySelective(BetRecordDO record);

    int updateByPrimaryKey(BetRecordDO record);
}