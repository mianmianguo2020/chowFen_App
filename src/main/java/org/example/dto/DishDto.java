package org.example.dto;

import lombok.Data;
import org.example.entity.Dish;
import org.example.entity.DishFlavor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christy Guo
 * @create 2023-03-29 10:25 AM
 *
 */


@Data
public class DishDto extends Dish { //DishDto extends the Dish class, meaning it inherits all the fields and methods from the Dish class. However, DishDto adds a few additional fields:

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}