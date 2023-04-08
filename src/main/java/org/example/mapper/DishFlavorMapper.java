package org.example.mapper;

/**
 * @author Christy Guo
 * @create 2023-03-29 9:49 AM
 */
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.DishFlavor;

@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
}
