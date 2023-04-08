package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;



/**
 * @author Christy Guo
 * @create 2023-03-30 9:04 AM
 */
@Data
@TableName("order_detail")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Long orderId;


    private Long dishId;


    private Long setmealId;


    private String dishFlavor;


    private Integer number;

    private BigDecimal amount;

    private String image;
}
