package org.example.mapper;

/**
 * @author Christy Guo
 * @create 2023-03-27 11:11 PM
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.Employee;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee>{
}
