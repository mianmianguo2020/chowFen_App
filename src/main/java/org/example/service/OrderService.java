package org.example.service;

/**
 * @author Christy Guo
 * @create 2023-03-28 11:57 AM
 */


import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.Order;

public interface OrderService extends IService<Order> {
    void submit(Order order);
}

