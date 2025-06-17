package com.itheima.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmpLoginInfo {
    private Integer id;
    private String username;
    private String name;
    private String token;
}
