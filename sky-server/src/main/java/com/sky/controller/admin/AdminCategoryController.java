package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.admin.AdminCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类接口")
public class AdminCategoryController {
    @Autowired
    private AdminCategoryService adminCategoryService;
    @PutMapping()
    @ApiOperation("修改分类")
    public Result modifyCategory(@RequestBody CategoryDTO categoryDTO){
        return adminCategoryService.modifyCategory(categoryDTO);
    }
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> pageCategory(CategoryPageQueryDTO categoryPageQueryDTO){
        return adminCategoryService.pageCategory(categoryPageQueryDTO);
    }
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用分类")
    public Result stopOrStart(@PathVariable Long status,Long id){
        return adminCategoryService.stopOrStart(status,id);
    }
    @PostMapping
    @ApiOperation("新增分类")
    public Result addCategory(@RequestBody CategoryDTO categoryDTO){
        return adminCategoryService.addCategory(categoryDTO);
    }
    @DeleteMapping
    @ApiOperation("删除分类")
    public Result deleteCategory(Long id){
        return adminCategoryService.deleteCategory(id);
    }
    @GetMapping("/list")
    @ApiOperation("根据类型查询")
    public Result<Employee> typeQueryCategory(int type){
        return adminCategoryService.typeQueryCategory(type);
    }
}
