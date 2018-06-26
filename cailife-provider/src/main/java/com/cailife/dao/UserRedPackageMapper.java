package com.cailife.dao;

import com.cailife.pojo.UserRedPackage;
import com.cailife.pojo.UserRedPackageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRedPackageMapper {
    int countByExample(UserRedPackageExample example);

    int deleteByExample(UserRedPackageExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserRedPackage record);

    int insertSelective(UserRedPackage record);

    List<UserRedPackage> selectByExample(UserRedPackageExample example);

    UserRedPackage selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserRedPackage record, @Param("example") UserRedPackageExample example);

    int updateByExample(@Param("record") UserRedPackage record, @Param("example") UserRedPackageExample example);

    int updateByPrimaryKeySelective(UserRedPackage record);

    int updateByPrimaryKey(UserRedPackage record);
    
    int batchInsertUserRedPack(@Param("userRedPackList") List<UserRedPackage> userRedPackList);
}