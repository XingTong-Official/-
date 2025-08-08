package com.sky.service.user;

import java.util.List;

public interface UserSetmealService {
    List querySetmeal(String categoryId);

    List queryDish(String id);
}
