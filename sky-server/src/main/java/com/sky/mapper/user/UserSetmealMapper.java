package com.sky.mapper.user;

import com.sky.entity.Setmeal;
import com.sky.vo.DishItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserSetmealMapper {
    @Select("select * from setmeal where category_id=#{categoryId}")
    List<Setmeal> querySetmeal(String categoryId);

    @Select("select * from setmeal_dish where setmeal_id=#{id}")
    List<DishItemVO> queryDish(String id);
}
