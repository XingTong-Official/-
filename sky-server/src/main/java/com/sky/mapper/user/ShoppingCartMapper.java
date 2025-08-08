package com.sky.mapper.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    ShoppingCart queryShoppingCart(ShoppingCartDTO shoppingCartDTO,Long userId);

    @Update("update shopping_cart set number=number+1 , amount=amount")
    void increase();

    @Insert("insert into shopping_cart(name, image, user_id, dish_id, setmeal_id, dish_flavor, number, amount, create_time) " +
            "values (#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{number},#{amount},#{createTime})")
    void add(ShoppingCart shoppingCart);

    @Select("select * from dish where id=#{dishId}")
    Dish queryDish(Long dishId);

    @Select("select * from setmeal where id=#{setmealId}")
    Setmeal querySetmeal(Long setmealId);

    @Update("update shopping_cart set number=#{number} where id=#{id}")
    void updateNum(ShoppingCart shoppingCart);

    @Delete("delete from shopping_cart where user_id=#{currentId}")
    void clean(Long currentId);

    @Select("select * from shopping_cart where user_id=#{id}")
    List<ShoppingCart> queryShoppingCartList(Long id);

    void deleteShoppingCart(ShoppingCart shoppingCart);

    void minusOneShoppingCart(ShoppingCart shoppingCart);
}
