package com.sky.service.user.Impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.user.ShoppingCartMapper;
import com.sky.service.user.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    ShoppingCartMapper shoppingCartMapper;


    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        //判断数据库中有没有这个购物车项目
        ShoppingCart shoppingCart;
        shoppingCart = shoppingCartMapper.queryShoppingCart(shoppingCartDTO,BaseContext.getCurrentId());
        //如果有，就在基础上数量+1
        if(shoppingCart!=null){
            shoppingCart.setNumber(shoppingCart.getNumber()+1);
            shoppingCartMapper.updateNum(shoppingCart);
        }
        else {
            shoppingCart=new ShoppingCart();
            BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
            shoppingCart.setUserId(BaseContext.getCurrentId());
            shoppingCart.setCreateTime(LocalDateTime.now());
            if(shoppingCart.getDishId()!=null){
                Dish dish = shoppingCartMapper.queryDish(shoppingCart.getDishId());
                shoppingCart.setName(dish.getName());
                shoppingCart.setNumber(1);
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            }
            else {
                Setmeal setmeal = shoppingCartMapper.querySetmeal(shoppingCart.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setNumber(1);
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCartMapper.add(shoppingCart);
        }
    }

    @Override
    public void clean() {
        Long currentId = BaseContext.getCurrentId();
        shoppingCartMapper.clean(currentId);
    }

    @Override
    public List<ShoppingCart> queryShoppingCart() {
        Long id = BaseContext.getCurrentId();
        return shoppingCartMapper.queryShoppingCartList(id);
    }

    @Override
    public void deleteShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart;
        shoppingCart = shoppingCartMapper.queryShoppingCart(shoppingCartDTO,BaseContext.getCurrentId());
        Integer number = shoppingCart.getNumber();
        shoppingCart.setUserId(BaseContext.getCurrentId());
        shoppingCart.setDishId(shoppingCartDTO.getDishId());
        shoppingCart.setSetmealId(shoppingCartDTO.getSetmealId());
        if(number<=1){
            shoppingCartMapper.deleteShoppingCart(shoppingCart);
        }
        else {
            shoppingCart.setNumber(shoppingCart.getNumber()-1);
            shoppingCartMapper.minusOneShoppingCart(shoppingCart);
        }
    }
}
