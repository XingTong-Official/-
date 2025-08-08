package com.sky.service.user;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
    void addAddressBook(AddressBook addressBook);

    List<AddressBook> queryAddressBook();

    void setDefault(AddressBook id);

    AddressBook queryById(Long id);

    void modifyAddressBook(AddressBook addressBook);

    void deleteAddressBook(Long id);
}
