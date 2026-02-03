package com.myb.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 接收前端新增员工传入的参数
 **/

@Data
public class EmployeeDTO implements Serializable {

    private Long id;

    private String username;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;

}
