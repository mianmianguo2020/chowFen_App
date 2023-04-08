package org.example.dto;

/**
 * @author Christy Guo
 * @create 2023-03-29 4:25 PM
 *
 *
 */

import lombok.Data;
import org.example.entity.SetMeal;
import org.example.entity.SetMealDish;

import java.util.List;

@Data
public class SetMealDto extends SetMeal {

    private List<SetMealDish> setmealDishes;

    private String categoryName;
}
