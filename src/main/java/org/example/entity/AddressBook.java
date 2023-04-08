package org.example.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author Christy Guo
 * @create 2023-03-29 10:10 PM
 */
@Data
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    private Long userId;


    private String consignee;


    private String phone;


    private String sex;


    private String provinceCode;

    private String provinceName;


    private String cityCode;


    private String cityName;


    private String districtCode;


    private String districtName;


    private String detail;


    private String label;

    private Integer isDefault;

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


