package org.example.service.impl;

/**
 * @author Christy Guo
 * @create 2023-03-28 12:02 PM
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.common.CustomException;
import org.example.entity.Category;
import org.example.entity.Dish;
import org.example.entity.SetMeal;
import org.example.mapper.CategoryMapper;
import org.example.service.CategoryService;
import org.example.service.DishService;
import org.example.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {//implement business logic on delte

    @Autowired
    private DishService dishService;

    @Autowired
    private SetMealService setmealService;

    //Add query conditions, query according to category id
    @Override
    public void remove(Long categoryId) {
        //Add query conditions, query according to category id, Item cannot be deleted
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,categoryId);
        int count1 = dishService.count(dishLambdaQueryWrapper);

        //Query whether the current category is associated with dishes, if already associated, throw a business exception
        if(count1 > 0){
            //Already associated dishes, throw a business exception
            throw new CustomException("The current category is associated with dishes, which cannot be deleted");
        }

        //Query whether the current category is associated with a package, if it has been associated, throw a business exception
        LambdaQueryWrapper<SetMeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //Add query conditions, query according to category id
        setmealLambdaQueryWrapper.eq(SetMeal::getCategoryId,categoryId);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if(count2 > 0){
            //The package has been associated, and a business exception is thrown
            throw new CustomException("The current category is associated with a package and cannot be deleted");
        }

        //正常删除分类
        super.removeById(categoryId);

    }
}

