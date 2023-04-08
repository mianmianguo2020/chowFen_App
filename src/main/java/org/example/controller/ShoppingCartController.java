package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.common.BaseContext;
import org.example.common.R;
import org.example.entity.ShoppingCart;
import org.example.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author Christy Guo
 * @create 2023-03-30 12:35 AM
 */
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    /**
     * @Description: 获取购物车列表
     * @Author: <a href="https://www.codermast.com/">CoderMast</a>
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        Long currentUserId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentUserId);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);

        log.info("shopping cart list",list);

        return R.success(list);
    }

    /**
     * @param shoppingCart 添加的菜品封装对象
     * @Description: 将菜品添加到购物车
     */

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        log.info("购物车数据:{}",shoppingCart);

        //设置用户id，指定当前是哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(ShoppingCart::getUserId,currentId);           //将getUserId值为currentId


        if(dishId != null){
            //添加到购物车的是菜品
            queryWrapper.eq(ShoppingCart::getDishId,dishId);

        }else{
            //添加到购物车的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }

        //查询当前菜品或者套餐是否在购物车中
        //SQL:select * from shopping_cart where user_id = ? and dish_id/setmeal_id = ?
        ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);

        if(cartServiceOne != null){
            //如果已经存在，就在原来数量基础上加一
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number + 1);
            shoppingCartService.updateById(cartServiceOne);
        }else{
            //如果不存在，则添加到购物车，数量默认就是一
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }

        return R.success(cartServiceOne);
    }


    /**
     * @param map 数据封装
     * @Description: 减少一个菜品的数量，数量为1时减少即为删除该菜品
     * @Author: <a href="https://www.codermast.com/">CoderMast</a>
     */
    @PostMapping("/sub")
    public R<String> sub(@RequestBody Map<String, String> map) {
        if (map.get("setmealId") != null) {
            LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
            queryWrapper.eq(ShoppingCart::getSetmealId, map.get("setmealId"));

            ShoppingCart one = shoppingCartService.getOne(queryWrapper);
            if (one != null) {
                if (one.getNumber() > 1) {
                    one.setNumber(one.getNumber() - 1);
                    shoppingCartService.updateById(one);
                } else {
                    shoppingCartService.remove(queryWrapper);
                }
            }
        }

        if ((map.get("dishId") != null)) {
            LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
            queryWrapper.eq(ShoppingCart::getDishId, map.get("dishId"));

            ShoppingCart one = shoppingCartService.getOne(queryWrapper);
            if (one != null) {
                if (one.getNumber() > 1) {
                    one.setNumber(one.getNumber() - 1);
                    shoppingCartService.updateById(one);
                } else {
                    shoppingCartService.remove(queryWrapper);
                }
            }
        }


        return R.success("删除成功");
    }

    /**
     * @Description: 清空购物车
     */
    @DeleteMapping("/clean")
    public R<String> clean() {
        log.info("清空购物车");
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        shoppingCartService.remove(queryWrapper);
        return R.success("清空购物车成功");
    }
}
