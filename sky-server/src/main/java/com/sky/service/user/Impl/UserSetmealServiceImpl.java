package com.sky.service.user.Impl;

import com.sky.entity.Setmeal;
import com.sky.mapper.user.UserSetmealMapper;
import com.sky.service.user.UserSetmealService;
import com.sky.vo.DishItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSetmealServiceImpl implements UserSetmealService {
    @Autowired
    UserSetmealMapper userSetmealMapper;
    @Override
    public List querySetmeal(String categoryId) {
        List<Setmeal> list = userSetmealMapper.querySetmeal(categoryId);
        return list;
    }

    @Override
    public List queryDish(String id) {
        List<DishItemVO> list = userSetmealMapper.queryDish(id);
        return list;
    }
}
