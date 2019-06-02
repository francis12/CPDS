package com.ssc.mapper;

import com.ssc.model.TCFFCPRIZE;
import com.ssc.model.TCFFCPRIZECondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TCFFCPRIZEDAO {
    int countByCondition(TCFFCPRIZECondition example);

    int deleteByCondition(TCFFCPRIZECondition example);

    int deleteByPrimaryKey(Long id);

    int insert(TCFFCPRIZE record);
    int insertBatch(List<TCFFCPRIZE> list);

    int insertSelective(TCFFCPRIZE record);

    List<TCFFCPRIZE> selectByCondition(TCFFCPRIZECondition example);

    TCFFCPRIZE selectByPrimaryKey(Long id);

    int updateByConditionSelective(@Param("record") TCFFCPRIZE record, @Param("example") TCFFCPRIZECondition example);

    int updateByCondition(@Param("record") TCFFCPRIZE record, @Param("example") TCFFCPRIZECondition example);

    int updateByPrimaryKeySelective(TCFFCPRIZE record);

    int updateByPrimaryKey(TCFFCPRIZE record);
}