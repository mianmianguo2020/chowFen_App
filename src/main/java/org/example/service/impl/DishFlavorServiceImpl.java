package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.DishFlavor;
import org.example.mapper.DishFlavorMapper;
import org.example.service.DishFlavorService;
import org.springframework.stereotype.Service;


/**
 * @author Christy Guo
 * @create 2023-03-29 9:50 AM
 */
@Slf4j
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {

}