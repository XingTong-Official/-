package com.sky.service.admin.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.admin.DishMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.admin.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    DishMapper dishMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public Result<PageResult> pageQueryDish(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<Dish> dishes = dishMapper.pageQueryDish(dishPageQueryDTO);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(dishes.getTotal());
        pageResult.setRecords(dishes.getResult());
        Result<PageResult> result = new Result<>();
        result.setData(pageResult);
        result.setCode(1);
        return result;
    }

    @Override
    public Result deleteDish(String s) {
        String[] ids = s.split(",");
        List<Long> list=new ArrayList<>();
        for(int i=0;i<ids.length;i++){
            Long num = Long.parseLong(ids[i]);
            list.add(num);
        }
        int deleteNums = dishMapper.delete(list);
        for(String id:ids){
            redisTemplate.delete("Category_"+id);
        }
        return Result.success();
    }

    @Override
    public DishVO queryDish(Long id) {
        Dish dish = dishMapper.queryDish(id);
        DishVO dishVO=new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        List<DishFlavor> flavors=dishMapper.queryFlavors(dishVO.getId());
        dishVO.setFlavors(flavors);
        return dishVO;
    }

    @Override
    public Result<List> listQueryDish(Long categoryId) {
        List<Dish> list = dishMapper.listQueryDish(categoryId);
        Result<List> listResult = new Result<>();
        listResult.setCode(1);
        listResult.setData(list);
        return listResult;
    }

    @Override
    public Result stopOrStart(Long status, Long id) {
        int i = dishMapper.stopOrStart(status,id);
        redisTemplate.delete("Category_"+id);
        return Result.success();
    }

    @Override
    public Result addDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        List<DishFlavor> flavors = dishDTO.getFlavors();
        int dishNums = dishMapper.addDish(dish);
        for (int i =0;i<flavors.size();i++){
            flavors.get(i).setDishId(dish.getId());
        }
        int flavorsNums = dishMapper.addFlavor(flavors);
        return Result.success();
    }

    @Override
    public Result editDish(DishDTO dishDTO) {
        List<DishFlavor> flavors = dishDTO.getFlavors();
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        for (int i =0;i<flavors.size();i++){
            flavors.get(i).setDishId(dish.getId());
        }
        dishMapper.updateDish(dish);
        dishMapper.deleteAllFlavors(dish.getId());
        dishMapper.addFlavor(flavors);
        redisTemplate.delete("Category_"+dish.getId());
        return Result.success();
    }
}
