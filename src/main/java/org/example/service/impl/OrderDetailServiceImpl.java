package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.OrderDetail;
import org.example.mapper.OrderDetailMapper;
import org.example.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @author Christy Guo
 * @create 2023-03-30 9:06 AM
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
