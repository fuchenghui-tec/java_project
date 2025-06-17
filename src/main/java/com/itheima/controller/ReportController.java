package com.itheima.controller;

import com.itheima.entity.ClazzCountOption;
import com.itheima.entity.JobOption;
import com.itheima.entity.Result;
import com.itheima.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/report")
@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;

    /**
     * 统计各职位员工人数
     * @return
     */
    @GetMapping("/empJobData")
    public Result empJobData(){
        log.info("统计各职位员工人数...");
        JobOption jobOption = reportService.empJobData();
        return Result.success(jobOption);
    }

    /**
     * 统计各性别员工人数
     * @return
     */
    @GetMapping("/empGenderData")
    public Result empGenderData(){
        log.info("统计各性别员工人数...");
        List<Map> list = reportService.empGenderData();
        return Result.success(list);
    }

    /**
     * 统计学员的学历信息
     */
    @GetMapping("/studentDegreeData")
    public Result getStudentDegreeData(){
        log.info("统计学员的学历信息");
        List<Map> dataList = reportService.getStudentDegreeData();
        return Result.success(dataList);
    }

    /**
     * 班级人数统计
     */
    @GetMapping("/studentCountData")
    public Result getStudentCountData(){
        log.info("班级人数统计");
        ClazzCountOption clazzCountOption = reportService.getStudentCountData();
        return Result.success(clazzCountOption);
    }
}
