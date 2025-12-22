package com.example.demo.mapper;

import com.example.demo.entity.LocBase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface LocBaseMapper {
    @Select("SELECT * FROM loc_base")
    List<LocBase> findAll();
}