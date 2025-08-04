package com.sky.mapper.admin;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MealMapper {
    Page<Setmeal> pageQueryMeal(SetmealPageQueryDTO setmealPageQueryDTO);

    @Update("update setmeal set status = #{status} where id = #{id}")
    int stopOrStart(Long status, Long id);

    int deleteAll(String[] split);

    @Select("select * from setmeal where id = #{id}")
    Setmeal queryMeal(Long id);

    @AutoFill(OperationType.INSERT)
    int addMeal(Setmeal setmeal);

    int addMealDish(List<SetmealDish> setmealDishes);

    @Select("select * from category where id = #{categoryId}")
    Category queryCategory(Long categoryId);

    @Select("select * from setmeal_dish where setmeal_id=#{id}")
    List<SetmealDish> queryMealDish(Long id);

    int addMealDishes(List<SetmealDish> setmealDishes);

    @Delete("delete from setmeal_dish where setmeal_id = #{id}")
    int deleteDishes(Long id);

    @AutoFill(OperationType.UPDATE)
    void modifyMeal(Setmeal setmeal);
}
