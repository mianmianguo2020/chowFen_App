package org.example.controller;

/**
 * @author Christy Guo
 * @create 2023-03-27 11:16 PM
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.example.common.R;
import org.example.entity.Employee;
import org.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) { //@RequestBody 传入的是Json对象
       // 1. Encrypt the password submitted from the page using md5
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2. Query the database based on the username submitted from the page
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        // 3. If no employee is found, return a login failed result
        if (emp == null) {
            return R.error("Login failed");
        }

        // 4. Compare the password. If it doesn't match, return a login failed result
        if (!emp.getPassword().equals(password)) {
            return R.error("Login failed");
        }

        // 5. If the employee is disabled, return a result indicating that the account has been disabled
        if (emp.getStatus() == 0) {
            return R.error("Account has been disabled");
        }

        // 6. If the login is successful, store the employee ID in the session and return a login success result
        request.getSession().setAttribute("employee", emp.getId());

        employeeService.doSomething();

        return R.success(emp);
    }

    /**
     * Employee logout
     * @param request
     * @return
     */

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        // Clear the ID of the currently logged in employee saved in the session
        request.getSession().removeAttribute("employee");
        return R.success("Logout success");
    }

    /**
     * Add a new employee
     * @param employee
     * @return
     */

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {

        // Set the initial password to "123456" and encrypt it using md5
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());

        //获得当前登录用户的id
//        Long empId = (Long) request.getSession().getAttribute("employee");
//
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);
        employeeService.save(employee);

        return R.success("Success of new employees");
    }


    /**
     * Employee information paging query
     * @param page
     * @param pageSize
     * @param name
     * @return
     */

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);

        // Construct a page builder
        Page pageInfo = new Page(page, pageSize);

        // Construct a query builder
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        // Add filter conditions
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
//        if (name != null && !name.isEmpty()) {
//            queryWrapper.like(Employee::getName, name);
//        }

        // Add sorting conditions
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        // Execute the query
        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * Modify employee information based on ID
     * @param employee
     * @return
     */

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        Long empId = (Long)request.getSession().getAttribute("employee");
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(empId);

        System.out.println(empId);
        employeeService.updateById(employee);
        return R.success("Employee information updated successfully");
    }


    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("No corresponding employee information found");
    }
}