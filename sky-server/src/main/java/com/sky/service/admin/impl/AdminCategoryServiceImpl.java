package com.sky.service.admin.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.mapper.admin.AdminCategoryMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.admin.AdminCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {
    @Autowired
    AdminCategoryMapper adminCategoryMapper;
    @Override
    public Result modifyCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
//        category.setStatus(1);
//        category.setCreateTime(LocalDateTime.now());
//        category.setCreateUser(BaseContext.getCurrentId());
//        category.setUpdateTime(LocalDateTime.now());
//        category.setUpdateUser(BaseContext.getCurrentId());
        int i = adminCategoryMapper.modifyCategory(category);
        if(i==1) {
            return Result.success();
        }
        else if(i==0){
            return Result.error("没有指定ID的菜品!");
        }
        else{
            return Result.error("数据库异常，请联系管理员!");
        }
    }

    @Override
    public Result<PageResult> pageCategory(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        Page<Category> page= adminCategoryMapper.pageCategory(categoryPageQueryDTO);

        PageResult pageResult=new PageResult();
        pageResult.setTotal(page.getTotal());
        pageResult.setRecords(page.getResult());

        Result<PageResult> result = new Result<>();
        result.setData(pageResult);
        result.setCode(1);
        return result;

    }

    @Override
    public Result stopOrStart(Long status, Long id) {
        int i = adminCategoryMapper.stopOrStart(status, id);
        if(i==1) return Result.success();
        else return Result.error("数据库未查询到相关数据");
    }

    @Override
    public Result addCategory(CategoryDTO categoryDTO) {
        Category category=new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        category.setStatus(1);
        category.setCreateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        int i = adminCategoryMapper.addCategory(category);
        if(i==1){
            return Result.success();
        }
        else return Result.error("数据库异常，请联系管理员!");
    }

    @Override
    public Result deleteCategory(Long id) {
        int i = adminCategoryMapper.deleteCategory(id);
        if(i != 0) return Result.success();
        else return Result.error("数据库中不存在该数据");
    }

    @Override
    public Result<Employee> typeQueryCategory(int type) {
        List<Category> categories = adminCategoryMapper.typeQueryCategory(type);

        Result result=new Result<>();
        result.setCode(1);
        result.setData(categories);
        return result;
    }
}
