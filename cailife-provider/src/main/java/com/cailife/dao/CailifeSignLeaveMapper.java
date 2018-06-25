package com.cailife.dao;

import com.cailife.pojo.CailifeSignLeave;
import com.cailife.pojo.CailifeSignLeaveExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CailifeSignLeaveMapper {
    int countByExample(CailifeSignLeaveExample example);

    int deleteByExample(CailifeSignLeaveExample example);

    int deleteByPrimaryKey(String id);

    int insert(CailifeSignLeave record);

    int insertSelective(CailifeSignLeave record);

    List<CailifeSignLeave> selectByExampleWithBLOBs(CailifeSignLeaveExample example);

    List<CailifeSignLeave> selectByExample(CailifeSignLeaveExample example);

    CailifeSignLeave selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CailifeSignLeave record, @Param("example") CailifeSignLeaveExample example);

    int updateByExampleWithBLOBs(@Param("record") CailifeSignLeave record, @Param("example") CailifeSignLeaveExample example);

    int updateByExample(@Param("record") CailifeSignLeave record, @Param("example") CailifeSignLeaveExample example);

    int updateByPrimaryKeySelective(CailifeSignLeave record);

    int updateByPrimaryKeyWithBLOBs(CailifeSignLeave record);

    int updateByPrimaryKey(CailifeSignLeave record);
}