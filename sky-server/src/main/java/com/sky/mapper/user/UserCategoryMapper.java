package com.sky.mapper.user;

import com.sky.entity.Category;
import com.sky.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserCategoryMapper {

    @Select("select * from category")
    List<Category> queryCategory();

    @Select("select * from setmeal")
    List<Setmeal> querySetmeal();
}
