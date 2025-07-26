package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import com.sky.result.Result;

public interface CategoryService {
    Result modifyCategory(CategoryDTO categoryDTO);

    Result<PageResult> pageCategory(CategoryPageQueryDTO categoryPageQueryDTO);

    Result stopOrStart(Long status, Long id);

    Result addCategory(CategoryDTO categoryDTO);

    Result deleteCategory(Long id);

    Result<Employee> typeQueryCategory(int type);
}
