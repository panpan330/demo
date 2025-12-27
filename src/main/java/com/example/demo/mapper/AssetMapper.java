package com.example.demo.mapper;

import com.example.demo.entity.AssetDevice;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface AssetMapper {

    @Select("SELECT * FROM sys_asset_device")
    List<AssetDevice> findAll();

    @Select("SELECT * FROM sys_asset_device WHERE id = #{id}")
    AssetDevice findById(Long id);

    @Insert("INSERT INTO sys_asset_device(device_name, device_code, status, create_time) VALUES(#{deviceName}, #{deviceCode}, #{status}, NOW())")
    void insert(AssetDevice asset);

    // ⭐ 核心：更新状态和借用人
    @Update("UPDATE sys_asset_device SET status = #{status}, borrower_id = #{borrowerId} WHERE id = #{id}")
    void updateStatusAndBorrower(@Param("id") Long id, @Param("status") String status, @Param("borrowerId") Long borrowerId);

    @Delete("DELETE FROM sys_asset_device WHERE id = #{id}")
    void deleteById(Long id);
}