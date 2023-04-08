package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.common.BaseContext;
import org.example.common.R;
import org.example.dto.SetMealDto;
import org.example.entity.Category;
import org.example.entity.SetMeal;
import org.example.service.CategoryService;
import org.example.service.SetMealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Christy Guo
 * @create 2023-03-29 5:00 PM
 */
@Slf4j
@RestController
@RequestMapping("/setmeal")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SetmealController {
    @Autowired
    SetMealService setMealService;

    @Autowired
    CategoryService categoryService;

    /**
     * @Description: 分页获取套餐信息
     * @param page 页码
     * @param pageSize 页面大小
     * @param name 关键词
     */

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){

        //分页构造器对象
        Page<SetMeal> pageInfo = new Page<>(page,pageSize);
        Page<SetMealDto> dtoPage  = new Page<>();

        LambdaQueryWrapper<SetMeal> queryWrapper = new LambdaQueryWrapper<>();

        //添加查询条件，根据name进行like模糊查询
        queryWrapper.like(name!= null,SetMeal::getName,name);
        //添加排序条件，根据跟新时间降序排列
        queryWrapper.orderByDesc(SetMeal::getUpdateTime);

        setMealService.page(pageInfo,queryWrapper);

        //拷贝对象
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");

        List<SetMeal> setMealPageRecords = pageInfo.getRecords();
        List<SetMealDto> setMealDtoPageRecords = setMealPageRecords.stream().map((item) -> {

            SetMealDto setMealDto = new SetMealDto();
            //对象拷贝
            BeanUtils.copyProperties(item, setMealDto);
            Long setMealDtoCategoryId = setMealDto.getCategoryId();
            //根据分类id查询分类对象
            Category category = categoryService.getById(setMealDtoCategoryId);
            if(category != null) {
                String categoryName = category.getName();
                setMealDto.setCategoryName(categoryName);
            }
            return setMealDto;
        }).collect(Collectors.toList());

        dtoPage.setTotal(pageInfo.getTotal());
        dtoPage.setRecords(setMealDtoPageRecords);

        return R.success(dtoPage);
    }
//
//    /**
//     * @Description: 根据套餐id获取套餐信息
//     * @param id 套餐id
//     * @Author: <a href="https://www.codermast.com/">CoderMast</a>
//     */
   @GetMapping("/{id}")
   public R<SetMealDto> getByIdWithSetMealDto(@PathVariable String id){
       return R.success(setMealService.getByIdWithSetMealDto(id));
   }
//
    /**
     * @Description: 创建套餐
     * @param setMealDto 套餐对象
     * @Author: <a href="https://www.codermast.com/">CoderMast</a>
     */

    @PostMapping
    public R<String> save(@RequestBody SetMealDto setMealDto,HttpServletRequest request){
        log.info("mealPlan", setMealDto); //断点加这里
//        Long id = (Long) request.getSession().getAttribute("employee");
//        BaseContext.setCurrentId(id);

        setMealService.saveWithDish(setMealDto);

        return R.success("创建成功");
    }
//
//    /**
//     * @Description: 更新一个套餐
//     * @param setMealDto 套餐传输对象实体
//     */
   @PutMapping
   public R<String> updateWithDish(@RequestBody SetMealDto setMealDto, HttpServletRequest request){
       Long id = (Long) request.getSession().getAttribute("employee");
       BaseContext.setCurrentId(id);
       boolean ret = setMealService.updateWithDish(setMealDto);
       return ret?R.success("修改成功") : R.error("修改失败");
   }
//
    /**
     * @Description: 根据id批量删除套餐
     * @param ids id列表
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("ids: {}",ids);
        setMealService.removeWithDish(ids);
        return R.success("套餐数据删除成功");
    }
//
//    /**
//     * @Description: 停售和启售
//     * @param status : 状态码，0为停售，1为启售
//     * @param ids 操作菜品的id列表
//     */
   @PostMapping("/status/{status}")
   public R<String> status(@PathVariable Integer status,@RequestParam List<Long> ids,HttpServletRequest request){
       Long id = (Long) request.getSession().getAttribute("employee");
       BaseContext.setCurrentId(id);

       LambdaQueryWrapper<SetMeal> queryWrapper = new LambdaQueryWrapper<>();

       queryWrapper.in(ids != null,SetMeal::getId,ids);

       List<SetMeal> list = setMealService.list(queryWrapper);

       for (SetMeal setMeal : list) {
           if (setMeal != null){
               setMeal.setStatus(status);
               setMealService.updateById(setMeal);
           }
       }
       return R.success(status == 1? "启售成功" : "停售成功");
   }
//
//    /**
//     * @Description: 根据分类id查询其下的菜品
//     * @param categoryId 分类id
//     */
//    @GetMapping("/list")
//    public R<List<SetMeal>> getListByCategoryIdWithDish(String categoryId,Integer status){
//        return R.success(setMealService.getListByCategoryIdWithSetMeal(categoryId,status));
//    }


    @GetMapping("/list")
    public R<List<SetMeal>> list(SetMeal setmeal) {
        LambdaQueryWrapper<SetMeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null, SetMeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null, SetMeal::getStatus, setmeal.getStatus());
        queryWrapper.orderByDesc(SetMeal::getUpdateTime);

        List<SetMeal> list = setMealService.list(queryWrapper); // corrected variable name
        return R.success(list);
    }
}

