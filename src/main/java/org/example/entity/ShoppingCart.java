package org.example.entity;

/**
 * @author Christy Guo
 * @create 2023-03-30 12:34 AM
 */

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("shopping_cart")
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Long userId;

    private Long dishId;

    private Long setmealId;

    private String dishFlavor;

    private Integer number;

    private BigDecimal amount;

    private String image;

    private LocalDateTime createTime;
}
