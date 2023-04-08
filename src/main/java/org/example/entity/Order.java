package org.example.entity;

/**
 * @author Christy Guo
 * @create 2023-03-28 11:58 AM
 */

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("orders")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String number;

    private Integer status;

    private Long userId;

    private Long addressBookId;

    private LocalDateTime orderTime;

    private LocalDateTime checkoutTime;

    private Integer payMethod;

    private BigDecimal amount;

    private String remark;

    private String userName;

    private String phone;

    private String address;

    private String consignee;
}
