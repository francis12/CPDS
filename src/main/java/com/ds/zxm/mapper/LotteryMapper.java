package com.ds.zxm.mapper;

import com.ds.zxm.model.LotteryDO;
import com.ds.zxm.model.LotteryDOCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zl on 2015/8/27.
 */
public interface LotteryMapper {
    int countByCondition(LotteryDOCondition example);

    int deleteByCondition(LotteryDOCondition example);

    int deleteByPrimaryKey(Long id);

    int insert(LotteryDO record);

    int insertSelective(LotteryDO record);

    List<LotteryDO> selectByCondition(LotteryDOCondition example);

    LotteryDO selectByPrimaryKey(Long id);

    int updateByConditionSelective(@Param("record") LotteryDO record, @Param("example") LotteryDOCondition example);

    int updateByCondition(@Param("record") LotteryDO record, @Param("example") LotteryDOCondition example);

    int updateByPrimaryKeySelective(LotteryDO record);

    int updateByPrimaryKey(LotteryDO record);
}
