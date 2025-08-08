package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.DishFlavor;
import com.sky.result.Result;
import com.sky.service.user.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "购物车相关")
@RequestMapping("/user/shoppingCart")
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;
    @PostMapping("/add")
    @ApiOperation("添加内容")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        shoppingCartService.add(shoppingCartDTO);
        return Result.success();
    }
    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result clean(){
        shoppingCartService.clean();
        return Result.success();
    }
    @GetMapping("/list")
    @ApiOperation("查看购物车")
    public Result<List> queryShoppingCart(){
        return Result.success(shoppingCartService.queryShoppingCart());
    }
    @PostMapping("/sub")
    @ApiOperation("删除购物车中一个商品")
    public Result deleteShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO){
        shoppingCartService.deleteShoppingCart(shoppingCartDTO);
        return Result.success();
    }
}
