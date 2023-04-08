package org.example.mapper;

/**
 * @author Christy Guo
 * @create 2023-03-28 12:04 PM
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.Category;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
