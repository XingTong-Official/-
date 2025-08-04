package com.sky.service.user.Impl;

import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.user.UserDishMapper;
import com.sky.service.user.UserDishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDishServiceImpl implements UserDishService {
    @Autowired
    UserDishMapper userDishMapper;
    @Override
    public List<DishVO> queryDishListById(String categoryId) {
        List<DishVO> dishVOList=new ArrayList<>();
        List<Dish> dishList = userDishMapper.queryDish(categoryId);
        for(Dish dish:dishList){
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(dish,dishVO);
            List<DishFlavor> flavors = userDishMapper.queryFlavors(dish.getId());
            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }
        return dishVOList;
    }
}
