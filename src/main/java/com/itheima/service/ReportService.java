package com.itheima.service;

import com.itheima.entity.ClazzCountOption;
import com.itheima.entity.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    /**
     * 统计各职位员工人数
     * @return
     */
    JobOption empJobData();

    /**
     * 统计各性别员工人数
     * @return
     */
    List<Map> empGenderData();

    /**
     * 统计学员的学历信息
     */
    List<Map> getStudentDegreeData();

    /**
     * 班级人数统计
     */
    ClazzCountOption getStudentCountData();

}
