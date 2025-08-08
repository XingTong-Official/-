package com.sky.controller.user;

import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.user.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api("地址簿")
@RequestMapping("/user/addressBook")
public class AddressBookController {
    @Autowired
    AddressBookService addressBookService;
    @PostMapping
    @ApiOperation("添加地址簿")
    public Result addAddressBook(@RequestBody AddressBook addressBook){
        addressBookService.addAddressBook(addressBook);
        return Result.success();
    }
    @GetMapping("/list")
    @ApiOperation("查询当前登录用户的所有地址信息")
    public Result<List> queryAddressBook(){
        List<AddressBook> list = addressBookService.queryAddressBook();
        return Result.success(list);
    }
    @PutMapping("/default")
    @ApiOperation("设置默认地址")
    public Result setDefault(@RequestBody AddressBook addressBook){
        addressBookService.setDefault(addressBook);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("根据id查询地址")
    public Result<AddressBook> queryById(@PathVariable Long id){
        AddressBook addressBook = addressBookService.queryById(id);
        return Result.success(addressBook);
    }
    @PutMapping
    @ApiOperation("根据id修改地址")
    public Result modifyAddressBook(@RequestBody AddressBook addressBook){
        addressBookService.modifyAddressBook(addressBook);
        return Result.success();
    }
    @DeleteMapping
    @ApiOperation("根据id删除地址")
    public Result deleteAddressBook(Long id){
        addressBookService.deleteAddressBook(id);
        return Result.success();
    }
}
