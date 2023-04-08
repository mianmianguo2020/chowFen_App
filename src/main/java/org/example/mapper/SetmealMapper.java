package org.example.mapper;

/**
 * @author Christy Guo
 * @create 2023-03-28 6:34 PM
 */
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.SetMeal;

@Mapper
public interface SetmealMapper extends BaseMapper<SetMeal> {
}
