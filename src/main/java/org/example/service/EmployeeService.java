package org.example.service;

/**
 * @author Christy Guo
 * @create 2023-03-27 11:12 PM
 */
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.Employee;

public interface EmployeeService extends IService<Employee> {

    void doSomething();
}

