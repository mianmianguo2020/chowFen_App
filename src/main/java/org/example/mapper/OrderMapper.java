package org.example.mapper;

/**
 * @author Christy Guo
 * @create 2023-03-30 9:06 AM
 */
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.Order;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
