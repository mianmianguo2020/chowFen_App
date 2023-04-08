package org.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.springframework.stereotype.Service;


/**
 * @author Christy Guo
 * @create 2023-03-29 8:54 PM
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


}
