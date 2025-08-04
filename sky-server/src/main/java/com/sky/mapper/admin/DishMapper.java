package com.sky.mapper.admin;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DishMapper {
    Page<Dish> pageQueryDish(DishPageQueryDTO dishPageQueryDTO);

    int delete(List<Long> ids);

    @Select("select * from sky_take_out.dish where id = #{id}")
    Dish queryDish(Long id);

    @Select("select * from sky_take_out.dish where category_id = #{categoryId}")
    List<Dish> listQueryDish(Long categoryId);

    int stopOrStart(Long status, Long id);

    int addFlavor(List<DishFlavor> flavors);

    @AutoFill(OperationType.INSERT)
    int addDish(Dish dish);

    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> queryFlavors(Long id);

    @AutoFill(OperationType.UPDATE)
    int updateDish(Dish dish);

    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    int deleteAllFlavors(Long dishId);
}
