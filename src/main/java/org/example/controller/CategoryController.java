package org.example.controller;


/**
 * @author Christy Guo
 * @create 2023-03-28 11:56 AM
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.common.BaseContext;
import org.example.common.R;
import org.example.entity.Category;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/category")

public class CategoryController {
    // 注入CategoryService
    @Autowired
    CategoryService categoryService;

    /**
     * @Description: 获取分类信息
     * @param page 页码
     * @param pageSize 页面大小
     */
    @GetMapping("/page")
    public R<Page<Category>> page(int page, int pageSize){
        Page<Category> pageInfo = new Page<>(page,pageSize);
        System.out.println(page);
        System.out.println(pageInfo);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        // 增加排序条件
        queryWrapper.orderByDesc(Category::getSort); //sort by sort-col in category

        categoryService.page(pageInfo,queryWrapper);
        System.out.println(pageInfo);
        log.info(pageInfo.toString());
        return R.success(pageInfo);
    }

    /**
     *@Description: Add a category
     *@param category The encapsulated object of the category
     *category.name The name of the category
     *category.sort The sorting of the category
     *category.type The type of the category, 1 for dish categories and 2 for set meal categories
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Category category){
        Long id = (Long) request.getSession().getAttribute("employee");

        if (category == null){
            return R.error("类型为空");
        }

        BaseContext.setCurrentId(id);

        categoryService.save(category);
        return R.success("创建成功");
    }

    /**
     * @Description: Delete category according to id
     * @param id category ids
     */
    @DeleteMapping
    public R<String> delete( Long id){ //HttpServletRequest request, if any question about the request
        log.info("删除分类 ，id为 ：{}",id);
        System.out.println("ids " + id);
    // Delete directly by id, without judging whether it contains the associated dish content
//     categoryService.removeById(id);

        // 对于上述的优化
        categoryService.remove(id);
        return R.success("删除成功");
    }

    /**
     * @Description: 更新分类信息
     * @param category 更新分类封装对象
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Category category){
        if (category == null){
            return R.error("类型为空");
        }
        Long id = (Long) request.getSession().getAttribute("employee");

        BaseContext.setCurrentId(id);
        categoryService.updateById(category);
        return R.success("更新成功");
    }

    /**
     * @Description: Get the category list of dishes
     * @param category
     */


    // 根据条件查询分类数据
    @GetMapping("/list")
    public R<List<Category>> categoryList(Category category) {
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //  条件只有当 category.getType()不为空
        queryWrapper.eq(category.getType() != null, Category::getType, category.getType());

        //排序
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);

        return R.success(list);

    }
}
