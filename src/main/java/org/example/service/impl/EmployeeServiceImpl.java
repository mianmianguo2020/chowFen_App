package org.example.service.impl;

/**
 * @author Christy Guo
 * @create 2023-03-27 11:13 PM
 */
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Employee;
import org.example.mapper.EmployeeMapper;
import org.example.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    public void doSomething() {
        logger.info("This is a log message from the EmployeeServiceImpl class");
    }
}
