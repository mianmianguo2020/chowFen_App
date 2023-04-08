package org.example.entity;

import lombok.Data;
import java.io.Serializable;


/**
 * @author Christy Guo
 * @create 2023-03-29 8:48 PM
 */


@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;

    private String avatar;

    private Integer status;
}
