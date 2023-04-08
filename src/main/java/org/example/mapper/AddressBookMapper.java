package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.AddressBook;

/**
 * @author Christy Guo
 * @create 2023-03-29 10:10 PM
 */

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
