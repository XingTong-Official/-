package com.sky.controller.user;

import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.user.UserSetmealService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user/setmeal")
@Api(tags = "套餐分类查询")
public class UserSetmealController {
    @Autowired
    UserSetmealService userSetmealService;
    @Autowired
    RedisTemplate redisTemplate;
    @GetMapping("/list")
    public Result<List> querySetmeal(String categoryId){
        String key ="Category_"+categoryId;
        List<Setmeal> list =(List<Setmeal>) redisTemplate.opsForValue().get(key);
        if(list!=null && list.size()>0){
            return Result.success(list);
        }
        list = userSetmealService.querySetmeal(categoryId);
        redisTemplate.opsForValue().set(key,list);
        return Result.success(list);
    }
    @GetMapping("/dish/{id}")
    public Result<List> queryDish(@PathVariable String id){
        List list = userSetmealService.queryDish(id);
        return Result.success(list);
    }
}
