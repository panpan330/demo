package com.example.demo.mapper;

import com.example.demo.entity.AssetDevice;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface AssetMapper {

    // 查 (已有)
    @Select("SELECT d.*, c.name as categoryName FROM asset_device d " +
            "LEFT JOIN asset_category c ON d.category_id = c.id " +
            "ORDER BY d.id DESC")
    List<AssetDevice> findAll();

    // 借/还 (已有)
    @Update("UPDATE asset_device SET status = 'BORROWED' WHERE id = #{id}")
    void borrowDevice(Long id);

    @Update("UPDATE asset_device SET status = 'IDLE' WHERE id = #{id}")
    void returnDevice(Long id);

    // ⭐ 新增：设备入库
    @Insert("INSERT INTO asset_device (category_id, device_name, device_code, status, price) " +
            "VALUES (#{categoryId}, #{deviceName}, #{deviceCode}, 'IDLE', #{price})")
    void add(AssetDevice device);

    // ⭐ 修改：资产盘点/修正
    @Update("UPDATE asset_device SET category_id=#{categoryId}, device_name=#{deviceName}, " +
            "device_code=#{deviceCode}, price=#{price} WHERE id=#{id}")
    void update(AssetDevice device);

    // ⭐ 删除：资产报废
    @Delete("DELETE FROM asset_device WHERE id = #{id}")
    void delete(Long id);
}