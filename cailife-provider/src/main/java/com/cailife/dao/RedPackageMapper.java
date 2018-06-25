package com.cailife.dao;

import com.cailife.pojo.RedPackage;
import com.cailife.pojo.RedPackageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RedPackageMapper {
    int countByExample(RedPackageExample example);

    int deleteByExample(RedPackageExample example);

    int deleteByPrimaryKey(String id);

    int insert(RedPackage record);

    int insertSelective(RedPackage record);

    List<RedPackage> selectByExample(RedPackageExample example);

    RedPackage selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RedPackage record, @Param("example") RedPackageExample example);

    int updateByExample(@Param("record") RedPackage record, @Param("example") RedPackageExample example);

    int updateByPrimaryKeySelective(RedPackage record);

    int updateByPrimaryKey(RedPackage record);
}