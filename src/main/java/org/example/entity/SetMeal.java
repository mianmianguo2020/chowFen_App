package org.example.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Christy Guo
 * @create 2023-03-28 6:32 PM
 */
@Data
@TableName("setmeal")
public class SetMeal implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    private Long categoryId;

    private String name;


    private BigDecimal price;


    private Integer status;


    private String code;


    private String description;


    private String image;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

}

