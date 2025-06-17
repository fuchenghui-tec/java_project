package com.itheima.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 员工职位人数统计类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobOption {
    private List jobList;   //职位列表
    private List dataList;  //各职位对应的总人数
}
