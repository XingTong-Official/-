package com.sky.service.user.Impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.user.AddressBookMapper;
import com.sky.service.user.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    AddressBookMapper addressBookMapper;

    @Override
    public void addAddressBook(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.addAddressBook(addressBook);
    }

    @Override
    public List<AddressBook> queryAddressBook() {
        return addressBookMapper.queryAddressBook(BaseContext.getCurrentId());
    }

    @Override
    public void setDefault(AddressBook addressBook) {
        addressBook = addressBookMapper.queryById(addressBook.getId());
        addressBookMapper.setDefaultFirst(addressBook);
        addressBookMapper.setDefaultSecond(addressBook);
    }

    @Override
    public AddressBook queryById(Long id) {
        return addressBookMapper.queryById(id);
    }

    @Override
    public void modifyAddressBook(AddressBook addressBook) {
        addressBookMapper.modifyAddressBook(addressBook);
    }

    @Override
    public void deleteAddressBook(Long id) {
        addressBookMapper.deleteAddressBook(id);
    }
}
