package com.sky.service.user.Impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.user.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.user.UserService;
import com.sky.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    WeChatProperties weChatProperties;
    String WeChatLoginUrl="https://api.weixin.qq.com/sns/jscode2session";
    String grantType="authorization_code";
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("appid",weChatProperties.getAppid());
        hashMap.put("secret",weChatProperties.getSecret());
        hashMap.put("js_code",userLoginDTO.getCode());
        hashMap.put("grant_type",grantType);
        String res = HttpClientUtil.doGet(WeChatLoginUrl, hashMap);
        JSONObject jsonObject = JSONObject.parseObject(res);

        String openid = jsonObject.getString("openid");
        if (openid==null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        User user = userMapper.selectOpenid(openid);
        if(user==null){
            user = new User();
            user.setOpenid(openid);
            user.setCreateTime(LocalDateTime.now());
            userMapper.insertUser(user);
        }
        return user;
    }
}
