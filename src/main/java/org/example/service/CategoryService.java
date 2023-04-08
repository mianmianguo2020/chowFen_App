package org.example.service;

/**
 * @author Christy Guo
 * @create 2023-03-28 12:00 PM
 */
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.Category;

public interface CategoryService extends IService<Category> { //implement business logic on delte
    void remove(Long id);
}
