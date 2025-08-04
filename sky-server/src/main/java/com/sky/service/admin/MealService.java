package com.sky.service.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.vo.SetmealVO;

public interface MealService {
    Result<PageResult> pageQueryMeal(SetmealPageQueryDTO setmealPageQueryDTO);

    Result stopOrStart(Long status, Long id);

    Result deleteAll(String ids);

    Result<SetmealVO> queryMeal(Long id);

    Result addMeal(SetmealDTO setmealDTO);

    Result modifyMeal(SetmealDTO setmealDTO);
}
