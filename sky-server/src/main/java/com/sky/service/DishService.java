package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    Result<PageResult> pageQueryDish(DishPageQueryDTO dishPageQueryDTO);

    Result deleteDish(String s);

    DishVO queryDish(Long id);

    Result<List> listQueryDish(Long id);

    Result stopOrStart(Long status, Long id);

    Result addDish(DishDTO dishDTO);

    Result editDish(DishDTO dishDTO);
}
