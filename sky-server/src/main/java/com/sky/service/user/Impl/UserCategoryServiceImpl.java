package com.sky.service.user.Impl;

import com.sky.mapper.user.UserCategoryMapper;
import com.sky.service.user.UserCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCategoryServiceImpl implements UserCategoryService {

    @Autowired
    UserCategoryMapper userCategoryMapper;
    @Override
    public List query(String type) {
        if (type==null) return userCategoryMapper.queryAllCategory();
        return userCategoryMapper.queryCategory(type);
    }
}
