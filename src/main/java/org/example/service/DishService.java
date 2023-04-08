package org.example.service;

/**
 * @author Christy Guo
 * @create 2023-03-28 6:38 PM
 */
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.dto.DishDto;
import org.example.entity.Dish;

public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    void updateWithflavor(DishDto dishDto);
}

