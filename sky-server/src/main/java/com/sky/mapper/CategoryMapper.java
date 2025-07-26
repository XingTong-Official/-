package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @AutoFill(OperationType.UPDATE)
    int modifyCategory(Category category);


    Page<Category> pageCategory(CategoryPageQueryDTO categoryPageQueryDTO);

    @Update("update sky_take_out.category set status = #{status} where id = #{id}")
    int stopOrStart(Long status, Long id);

    @AutoFill(OperationType.INSERT)
    @Insert("insert into sky_take_out.category(type, name, sort, status, create_time, update_time, create_user, update_user) values " +
            "(#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    int addCategory(Category category);

    @Delete("delete from sky_take_out.category where id =#{id}")
    int deleteCategory(Long id);

    @Select("select * from sky_take_out.category where type = #{type}")
    List<Category> typeQueryCategory(int type);
}
