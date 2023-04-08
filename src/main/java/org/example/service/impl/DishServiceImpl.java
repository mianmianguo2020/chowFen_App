package org.example.service.impl;

/**
 * @author Christy Guo
 * @create 2023-03-28 6:36 PM
 */
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.DishDto;
import org.example.entity.Dish;
import org.example.entity.DishFlavor;
import org.example.mapper.DishMapper;
import org.example.service.DishFlavorService;
import org.example.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService; 

    @Transactional
    public void saveWithFlavor(DishDto dishDto){
        this.save(dishDto);
        log.info("this = " + this);

        Long dishId = dishDto.getId();      //获取前端传过来的dishId

        // 菜品口味 （flavors 并不包含 dishId,故dish需要另外赋值）
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.forEach(flavor -> flavor.setDishId(dishId));

//        List<DishFlavor> flavors = dishDto.getFlavors();
//        List<DishFlavor> updatedFlavors = new ArrayList<>();
//        for (DishFlavor flavor : flavors) {
//            flavor.setDishId(dishId);
//            updatedFlavors.add(flavor);
//        }

//        flavors = flavors.stream().map((flavor)->{
//            flavor.setDishId(dishId);
//            return flavor;
//        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    @Override

    /**
 * **/
    public DishDto getByIdWithFlavor(Long id) {
        Dish dish = this.getById(id);   
        DishDto dishDto = new DishDto();
        log.info("dish, " + dish.toString());
        //拷贝
        BeanUtils.copyProperties(dish, dishDto);

        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());

        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);


        return dishDto;
    }


    @Override
    public void updateWithflavor(DishDto dishDto){ 
        log.info("dishDto = " + dishDto.toString());

        this.updateById(dishDto);
        log.info("this = " + this);

        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);


        List<DishFlavor> flavors = dishDto.getFlavors();
        List<DishFlavor> flavorList = new ArrayList<>();
        for (DishFlavor item : flavors) {
            item.setDishId(dishDto.getId());
            flavorList.add(item);
        }

        dishFlavorService.saveBatch(flavorList);

    }
}

