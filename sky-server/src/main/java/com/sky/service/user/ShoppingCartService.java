package com.sky.service.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    void add(ShoppingCartDTO shoppingCartDTO);

    void clean();

    List<ShoppingCart> queryShoppingCart();

    void deleteShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
