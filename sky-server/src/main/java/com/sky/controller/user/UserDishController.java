package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.user.UserDishService;
import com.sky.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/dish")
public class UserDishController {
    @Autowired
    UserDishService userDishService;
    @GetMapping("/list")
    public Result<List> queryDishListById(String categoryId){
        List list=userDishService.queryDishListById(categoryId);
        return Result.success(list);
    }
}
