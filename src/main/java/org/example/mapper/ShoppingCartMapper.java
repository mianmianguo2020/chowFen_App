package org.example.mapper;

/**
 * @author Christy Guo
 * @create 2023-03-30 12:36 AM
 */
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.ShoppingCart;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}

