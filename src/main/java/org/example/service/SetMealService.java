package org.example.service;

/**
 * @author Christy Guo
 * @create 2023-03-28 6:40 PM
 */

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.dto.SetMealDto;
import org.example.entity.SetMeal;

import java.util.List;

public interface SetMealService extends IService<SetMeal> {
    SetMealDto getByIdWithSetMealDto(String id);

    boolean updateWithDish(SetMealDto setMealDto);

    void saveWithDish(SetMealDto setMealDto);

    void removeWithDish(List<Long> ids);

    List<SetMeal> getListByCategoryIdWithSetMeal(String categoryId, Integer status);
}
