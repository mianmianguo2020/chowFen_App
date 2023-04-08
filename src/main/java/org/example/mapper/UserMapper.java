package org.example.mapper;

/**
 * @author Christy Guo
 * @create 2023-03-29 8:55 PM
 */
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
