package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.ShoppingCart;
import org.example.mapper.ShoppingCartMapper;
import org.example.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @author Christy Guo
 * @create 2023-03-30 12:36 AM
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}