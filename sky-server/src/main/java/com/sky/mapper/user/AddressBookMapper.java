package com.sky.mapper.user;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AddressBookMapper{
    public void addAddressBook(AddressBook addressBook);

    @Select("select * from address_book where user_id=#{currentId}")
    List<AddressBook> queryAddressBook(Long currentId);

    @Update("update address_book set is_default=0 where user_id = #{userId};")
    void setDefaultFirst(AddressBook addressBook);
    @Update("update address_book set is_default=1 where id = #{id};")
    void setDefaultSecond(AddressBook addressBook);

    @Select("select * from address_book where id = #{id}")
    AddressBook queryById(Long id);

    void modifyAddressBook(AddressBook addressBook);

    @Delete("delete from address_book where id=#{id}")
    void deleteAddressBook(Long id);
}
