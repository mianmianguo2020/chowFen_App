package org.example.entity;

/**
 * @author Christy Guo
 * @create 2023-03-28 12:00 PM
 */

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer type;

    private String name;

    private Integer sort;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    public Category(String john, int i) {
    }
}

