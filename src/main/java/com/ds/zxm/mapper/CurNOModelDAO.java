package com.ds.zxm.mapper;

import com.ds.zxm.model.CurNOModel;
import com.ds.zxm.model.CurNOModelCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CurNOModelDAO {
    int countByCondition(CurNOModelCondition example);

    int deleteByCondition(CurNOModelCondition example);

    int deleteByPrimaryKey(Long id);

    int insert(CurNOModel record);

    int insertSelective(CurNOModel record);

    List<CurNOModel> selectByCondition(CurNOModelCondition example);

    CurNOModel selectByPrimaryKey(Long id);

    int updateByConditionSelective(@Param("record") CurNOModel record, @Param("example") CurNOModelCondition example);

    int updateByCondition(@Param("record") CurNOModel record, @Param("example") CurNOModelCondition example);

    int updateByPrimaryKeySelective(CurNOModel record);

    int updateByPrimaryKey(CurNOModel record);
}