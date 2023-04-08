package org.example.service.impl;

/**
 * @author Christy Guo
 * @create 2023-03-28 6:37 PM
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.common.CustomException;
import org.example.dto.SetMealDto;
import org.example.entity.Category;
import org.example.entity.SetMeal;
import org.example.entity.SetMealDish;
import org.example.mapper.SetmealMapper;
import org.example.service.CategoryService;
import org.example.service.SetMealService;
import org.example.service.SetmealDishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, SetMeal> implements SetMealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @Transactional
    public void saveWithDish(SetMealDto setmealDto) {
        this.save(setmealDto);

        List<SetMealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);

    }



    @Transactional
    @Override
    public void removeWithDish(List<Long> ids) {/
        //select count(*) from setmeal where id in (1,2,3) and status=1;
        LambdaQueryWrapper<SetMeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SetMeal::getId,ids);
        queryWrapper.eq(SetMeal::getStatus,1);

        int count = this.count(queryWrapper);
        if(count >0){
        }
        this.removeByIds(ids);

        //delete from setmeal_dish where setmeal_id in (1,2,3)
        LambdaQueryWrapper<SetMealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetMealDish::getSetmealId,ids);

        setmealDishService.remove(lambdaQueryWrapper);
    }

    @Override
    public SetMealDto getByIdWithSetMealDto(String id) {
        SetMeal setMeal = this.getById(id);

        SetMealDto setMealDto = new SetMealDto();
        BeanUtils.copyProperties(setMeal,setMealDto);

        Long categoryId = setMeal.getCategoryId();
        Category category = categoryService.getById(categoryId);
        setMealDto.setCategoryName(category.getName());

        LambdaQueryWrapper<SetMealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetMealDish::getDishId,id);

        List<SetMealDish> setMealDishes = setmealDishService.list(queryWrapper);
        setMealDto.setSetmealDishes(setMealDishes);
        return setMealDto;
    }

    @Override
    @Transactional
    public boolean updateWithDish(SetMealDto setMealDto) {
        this.updateById(setMealDto);
        List<SetMealDish> setmealDishes = setMealDto.getSetmealDishes();
        setmealDishes.forEach((item)->{
            item.setSetmealId(setMealDto.getId());
        });

        log.info(setmealDishes.toString());

        setmealDishService.saveOrUpdateBatch(setmealDishes);
        return true;
    }

    @Override
    public List<SetMeal> getListByCategoryIdWithSetMeal(String categoryId, Integer status) {
        LambdaQueryWrapper<SetMeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetMeal::getCategoryId,categoryId);
        queryWrapper.eq(status != null,SetMeal::getStatus,status);
        return this.list(queryWrapper);
    }

}
