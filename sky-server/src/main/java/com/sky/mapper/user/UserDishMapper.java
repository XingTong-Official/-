package com.sky.mapper.user;

import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDishMapper {
    @Select("select * from dish where category_id=#{categoryId}")
    List<Dish> queryDish(String categoryId);

    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> queryFlavors(Long id);
}
