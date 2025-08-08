package com.sky.service.admin.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.admin.MealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.admin.MealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealServiceImpl implements MealService {
    @Autowired
    MealMapper mealMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public Result<PageResult> pageQueryMeal(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> setmeals = mealMapper.pageQueryMeal(setmealPageQueryDTO);


        PageResult pageResult=new PageResult();
        pageResult.setTotal(setmeals.getTotal());
        pageResult.setRecords(setmeals.getResult());
        return Result.success(pageResult);
    }

    @Override
    public Result stopOrStart(Long status, Long id) {
        int i = mealMapper.stopOrStart(status, id);
//        redisTemplate.delete("Category_"+id);
        return Result.success();
    }

    @Override
    public Result deleteAll(String ids) {
        if (ids==null) return Result.error("请勿发送恶意信息!");
        String[] split = ids.split(",");
//        List<Long> list = new ArrayList<>();
//        for (int i=0;i<split.length;i++){
//            list.add(Long.parseLong(split[i]));
//        }
        mealMapper.deleteAll(split);
//        for (String s:split){
//            redisTemplate.delete("Category_"+s);
//        }
        return Result.success();
    }

    @Override
    public Result<SetmealVO> queryMeal(Long id) {
        if(id==null) return Result.error("请勿发送恶意信息!");
        Setmeal setmeal = mealMapper.queryMeal(id);
        SetmealVO setmealVO=new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);
        Category category = mealMapper.queryCategory(setmeal.getCategoryId());
        setmealVO.setCategoryName(category.getName());

        List<SetmealDish> setmealDishes = mealMapper.queryMealDish(setmeal.getId());
        setmealVO.setSetmealDishes(setmealDishes);
        return Result.success(setmealVO);
    }

    @Override
    public Result addMeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmeal.setStatus(1);
        mealMapper.addMeal(setmeal);

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
//        for (int i=0;i<setmealDishes.size();i++){
//            setmealDishes.get(i).setSetmealId(setmeal.getId());
//        }
        mealMapper.addMealDish(setmealDishes);

        return Result.success();
    }

    @Override
    public Result modifyMeal(SetmealDTO setmealDTO) {
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);

        for(int i=0;i<setmealDishes.size();i++){
            setmealDishes.get(i).setSetmealId(setmeal.getId());
        }
        mealMapper.deleteDishes(setmeal.getId());
        mealMapper.addMealDish(setmealDishes);

        mealMapper.modifyMeal(setmeal);
//        redisTemplate.delete("Category_"+setmeal.getId());
        return Result.success();
    }
}

