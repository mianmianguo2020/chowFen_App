package org.example.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.Dish;

/**
 * @author Christy Guo
 * @create 2023-03-28 6:33 PM
 */

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
