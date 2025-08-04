package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.user.UserCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "分类接口")
@Slf4j
@RequestMapping("/user/category")
public class UserCategoryController {
    @Autowired
    UserCategoryService userCategoryService;
    @GetMapping("/list")
    @ApiOperation("查询分类")
    public Result<List> queryCategory(String type){
        List list = userCategoryService.query(type);
        return Result.success(list);
    }
}
