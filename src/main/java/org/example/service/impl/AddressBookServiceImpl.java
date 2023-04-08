package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.AddressBook;
import org.example.mapper.AddressBookMapper;
import org.example.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @author Christy Guo
 * @create 2023-03-29 10:11 PM
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
