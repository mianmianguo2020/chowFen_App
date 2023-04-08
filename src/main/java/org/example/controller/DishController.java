package org.example.controller;

/**
 * @author Christy Guo
 * @create 2023-03-29 9:55 AM
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.example.common.R;
import org.example.dto.DishDto;
import org.example.entity.Category;
import org.example.entity.Dish;
import org.example.entity.DishFlavor;
import org.example.service.CategoryService;
import org.example.service.DishFlavorService;
import org.example.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DishController {
    // 注入dish业务实体
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    // 注入category业务实体
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @param page     页码
     * @param pageSize 页面大小
     * @param name     关键词
     * @Description: 分页查询dish内容
     */


    @GetMapping("/page")

//**********************************
    //对比下这种写法不可以,因为传回来的数据涉及多张表格;前端菜品分类返回的是id 不能正确显示
//    public R<Page> page(int page, int pageSize, String name) {
//
//        Page<Dish> pageInfo = new Page<>(page,pageSize);
//
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.like(StringUtils.isNotEmpty(name), Dish::getName, name);
//        queryWrapper.orderByDesc(Dish::getUpdateTime);
//
//        dishService.page(pageInfo, queryWrapper);
//
//        return R.success(pageInfo);
//    }
//**********************************

    public R<Page<DishDto>> page(int page, int pageSize, String name) {
        // 菜品分页页面 debug可以在这里加断点
        Page<Dish> dishPage = new Page<>(page,pageSize);
        // 菜品分页交互对象页面
        Page<DishDto> dishDtoPage = new Page<>();
        // 构造条件过滤器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        // 构建查询条件 LIKE clause to the query if the name parameter is not empty or null, and searches for records in the Dish table where the specified column (name) contains the specified value (name) enclosed in % wildcards.
        queryWrapper.like(StringUtils.isNotEmpty(name), Dish::getName, name);
        // 分页查询菜品
        dishService.page(dishPage, queryWrapper);
        // 菜品分页记录值
        List<Dish> recordsDish = dishPage.getRecords();

        List<DishDto> recordsDishDto = new ArrayList<>();
        for (Dish dish : recordsDish) {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish, dishDto);
            Long categoryId = dishDto.getCategoryId();
            Category category = categoryService.getById(categoryId);

            if(category!=null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            recordsDishDto.add(dishDto);
        }

//**********************************
//        // 菜品分页交互对象记录值 lambda表达式
//        List<DishDto> recordsDishDto = recordsDish.stream().map((item) -> {
//            // 创建dishDto对象
//            DishDto dishDto = new DishDto();
//            // 将Dish类型的item属性赋值到dishDto
//            BeanUtils.copyProperties(item,dishDto);
//            // 获取对象的分类id
//            Long categoryId = dishDto.getCategoryId();
//            // 根据分类id查分类对象
//            Category categoryServiceById = categoryService.getById(categoryId);
//            // 从分类对象中取出分类名称
//            String categoryServiceByIdName = categoryServiceById.getName();
//            // 设置分类名称到dishDto对象
//            dishDto.setCategoryName(categoryServiceByIdName);
//            // 返回该对象
//            return dishDto;
//        }).collect(Collectors.toList());
//**********************************


        // 将封装好的记录值赋值给dishDtoPage对象
        dishDtoPage.setRecords(recordsDishDto);
        // 将总条数赋值给dishDtoPage
        dishDtoPage.setTotal(dishPage.getTotal());
        return R.success(dishDtoPage);
    }

    /**
     * @param ids 要删除的菜品id
     * @Description: 批量删除菜品
     * @Author: <a href="https://www.codermast.com/">CoderMast</a>
     */
//    @DeleteMapping
//    public R<String> delete(String ids) {
//        String[] split = ids.split(",");
//        List<String> list = new ArrayList<>(Arrays.asList(split));
//        dishService.removeBatchByIds(list);
//        return R.success("批量删除成功！");
//    }
//
    /**
     * @param id 要获取信息的id值
     * @Description: 根据id查询一个dish对象
     * @Author: CoderMast <a href="https://www.codermast.com/">codermast</a>
     */
    @GetMapping("/{id}")
    public R<DishDto> getOne(@PathVariable Long id) { //接收前端传入的id; 需要DishDto 因为需要口味讯息
        //添加断点位置
        DishDto dishDto = dishService.getByIdWithFlavor(id);

        //还是需要查询两张表, dish and dish_favor
        return R.success(dishDto);
    }

    /**
     * @Description: 添加菜品
     * @Author: <a href="https://www.codermast.com/">CoderMast</a>
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {

        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);

        return R.success("菜品添加成功");
    }

    /**
     * @Description: 修改菜品信息
     * @param dishDto 菜品的信息
     * @Author: <a href="https://www.codermast.com/">CoderMast</a>
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.updateWithflavor(dishDto);

        //清理所有菜品的缓存数据
        //Set keys = redisTemplate.keys("dish_*");
        //redisTemplate.delete(keys);

        //清理某个分类下面的菜品缓存数据
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);


//        boolean ret = dishService.updateById(dishDto);
        return R.success("更新成功");
    }

    /**
     * @Description: 停售和启售
     * @param status : 状态码，0为停售，1为启售
     * @param ids 操作菜品的id列表
     * @Author: <a href="https://www.codermast.com/">CoderMast</a>
     */
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable Integer status,@RequestParam List<Long> ids){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.in(ids != null,Dish::getId,ids);

        List<Dish> list = dishService.list(queryWrapper);

        for (Dish dish : list) {
            if (dish != null){
                dish.setStatus(status);
                dishService.updateById(dish);
            }
        }

        //清理所有菜品的缓存数据
        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);

        return R.success(status == 1? "启售成功" : "停售成功");
    }

    /**
     * @Description: 根据分类id查询其下的菜品
     * @param dish 分类id
     * @Author: <a href="https://www.codermast.com/">CoderMast</a>
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {
        List<DishDto> dishDtoList = null;
        //动态构造key
        String key = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();//

        //1.先从redis中获取缓存数据,按照菜单分类缓存
        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);

        if (dishDtoList != null) {
            //2.如果存在!=null，直接返回，不用查询数据库
            return R.success(dishDtoList);
        }
        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        //添加条件，查询状态为1（起售状态）的菜品
        queryWrapper.eq(Dish::getStatus, 1);

        //添加排序条件 排序顺序，创建时间倒序
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);

        dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            //当前菜品的id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);
            //SQL:select * from dish_flavor where dish_id = ?
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());
        //3.不存在，需要查询数据库，将查询到的数据缓存到redis
        redisTemplate.opsForValue().set(key, dishDtoList, 60, TimeUnit.HOURS);//60分钟

        return R.success(dishDtoList);
    }




// 根据条件(分类id)查询对应的菜品数据
//    *********************************
    //按照需要更新版本
//    public R<List<Dish>> list(Dish dish) {
//        System.out.println(" @GetMapping(\"/list\")");
//        //构造查询条件
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
//        //条件条件，查询状态是1 （Status=0代表禁售，Status=1代表正常）
//        queryWrapper.eq(Dish::getStatus, 1);
//
//        //添加排序条件
//        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);   //根据getSort升序排,根据getUpdateTime降序排
//        List<Dish> list = dishService.list(queryWrapper);
//
//        return R.success(list);
//    }
//    *********************************

}

