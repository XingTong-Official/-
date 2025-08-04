package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.admin.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "菜品管理")
@RequestMapping("/admin/dish")
@Slf4j
public class DishController {
    @Autowired
    DishService dishService;
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> pageQueryDish(DishPageQueryDTO dishPageQueryDTO){
        return dishService.pageQueryDish(dishPageQueryDTO);
    }
    @DeleteMapping
    @ApiOperation("批量删除")
    public Result deleteDish(String ids){
        return dishService.deleteDish(ids);
    }
    @GetMapping("/{id}")
    @ApiOperation("查询单个")
    public Result<DishVO> queryDish(@PathVariable Long id){
        DishVO dishVO = dishService.queryDish(id);
        return Result.success(dishVO);
    }
    @GetMapping("/list")
    @ApiOperation("分类查询")
    public Result<List> listQueryDish(Long categoryId){
        return dishService.listQueryDish(categoryId);
    }
    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售停售")
    public Result stopOrStart(@PathVariable Long status,Long id){
        return dishService.stopOrStart(status,id);
    }
    @PostMapping
    @ApiOperation("新增菜品")
    public Result addDish(@RequestBody DishDTO dishDTO){
        return dishService.addDish(dishDTO);
    }
    @PutMapping
    @ApiOperation("修改菜品")
    public Result editDish(@RequestBody DishDTO dishDTO){
        return dishService.editDish(dishDTO);
    }
}
