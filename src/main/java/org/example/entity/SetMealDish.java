package org.example.entity;

/**
 * @author Christy Guo
 * @create 2023-03-29 4:24 PM
 */
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@TableName("setmeal_dish")
public class SetMealDish implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    private Long setmealId;


    private Long dishId;


    private String name;

    private BigDecimal price;

    private Integer copies;

    private Integer sort;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


    private Integer isDeleted;
}
