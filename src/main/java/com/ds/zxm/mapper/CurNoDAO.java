package com.ds.zxm.mapper;

import com.ds.zxm.model.CurNoDO;
import com.ds.zxm.model.CurNoDOCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CurNoDAO {
    int countByCondition(CurNoDOCondition example);

    int deleteByCondition(CurNoDOCondition example);

    int deleteByPrimaryKey(Long id);

    int insert(CurNoDO record);

    int insertSelective(CurNoDO record);

    List<CurNoDO> selectByCondition(CurNoDOCondition example);

    CurNoDO selectByPrimaryKey(Long id);

    int updateByConditionSelective(@Param("record") CurNoDO record, @Param("example") CurNoDOCondition example);

    int updateByCondition(@Param("record") CurNoDO record, @Param("example") CurNoDOCondition example);

    int updateByPrimaryKeySelective(CurNoDO record);

    int updateByPrimaryKey(CurNoDO record);
}