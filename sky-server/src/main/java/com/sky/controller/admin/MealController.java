package com.sky.controller.admin;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.admin.MealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Api(tags = "套餐设置")
@RequestMapping("/admin/setmeal")
public class MealController {
    @Autowired
    MealService mealService;

    @ApiOperation("菜品分页查询")
    @GetMapping("/page")
    public Result<PageResult> pageQueryMeal(Integer categoryId,String name,int page,int pageSize,Integer status){
        SetmealPageQueryDTO setmealPageQueryDTO = SetmealPageQueryDTO.builder()
                .page(page)
                .categoryId(categoryId)
                .pageSize(pageSize)
                .name(name)
                .status(status)
                .build();
        return mealService.pageQueryMeal(setmealPageQueryDTO);
    }
    @PostMapping("/status/{status}")
    @ApiOperation("套餐停售起售")
    public Result stopOrStart(@PathVariable Long status,Long id){
        return mealService.stopOrStart(status,id);
    }
    @DeleteMapping
    @ApiOperation("批量删除")
    public Result deleteAll(String ids){
        return mealService.deleteAll(ids);
    }
    @GetMapping("/{id}")
    @ApiOperation("查询单个id")
    public Result<SetmealVO> queryMeal(@PathVariable Long id){
        return mealService.queryMeal(id);
    }
    @PostMapping
    @ApiOperation("新增菜品")
    public Result addMeal(@RequestBody SetmealDTO setmealDTO){
        return mealService.addMeal(setmealDTO);
    }
    @PutMapping
    @ApiOperation("修改套餐")
    public Result modifyMeal(@RequestBody SetmealDTO setmealDTO){
        return mealService.modifyMeal(setmealDTO);
    }
}
