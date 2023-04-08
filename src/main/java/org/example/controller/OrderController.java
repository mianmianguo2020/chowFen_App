package org.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.common.BaseContext;
import org.example.common.R;
import org.example.entity.Order;
import org.example.entity.ShoppingCart;
import org.example.service.OrderService;
import org.example.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * @author Christy Guo
 * @create 2023-03-30 9:05 AM
 */

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    ShoppingCartService shoppingCartService;

    /**
     * @Description: 获取订单信息列表
     * @param page 页码
     * @param pageSize 页面大小
     * @param number 订单号关键字
     * @param beginTime 开始日期
     * @param endTime 结束日期
     */
    @GetMapping("/page")
    public R<Page<Order>> page(Integer page, Integer pageSize, String number, LocalDateTime beginTime, LocalDateTime endTime){
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();

        Page<Order> pageInfo = new Page<>(page,pageSize);

        queryWrapper.like(StringUtils.isNotBlank(number), Order::getNumber,number);
        queryWrapper.ge(ObjectUtils.isNotNull(beginTime), Order::getOrderTime,beginTime);
        queryWrapper.le(ObjectUtils.isNotNull(endTime), Order::getOrderTime,endTime);

        orderService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }


    @GetMapping("/userPage")
    public R<Page<Order>> userPage(Integer page, Integer pageSize){
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();

        Page<Order> pageInfo = new Page<>(page,pageSize);
        Long userId = BaseContext.getCurrentId();
        queryWrapper.eq(Order::getUserId,userId);

        orderService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Order order){
        try {
            // Submit the order
            orderService.submit(order);

            // Clear the shopping cart after the order is successfully submitted
            LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
            shoppingCartService.remove(queryWrapper);

            return R.success("下单成功");
        } catch (Exception e) {
            // Handle the exception
            return R.error("下单失败：" + e.getMessage());
        }
    }
}