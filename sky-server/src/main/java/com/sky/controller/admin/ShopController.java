package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@Slf4j
@Api(tags = "商家信息接口")
@RequestMapping("/admin/shop")
public class ShopController {
    private final String key="ShopStatus";
    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation("设置营业信息")
    public Result setShopStatus(@PathVariable Integer status){
        redisTemplate.opsForValue().set(key,status);
        return Result.success();
    }
    @GetMapping("/status")
    @ApiOperation("获取营业信息")
    public Result<Integer> getShopStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(key);
        return Result.success(status);
    }
}
