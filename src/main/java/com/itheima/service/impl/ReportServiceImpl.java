package com.itheima.service.impl;

import com.itheima.entity.ClazzCountOption;
import com.itheima.entity.JobOption;
import com.itheima.mapper.EmpMapper;
import com.itheima.mapper.StudentMapper;
import com.itheima.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public JobOption empJobData() {
        List<Map> list = empMapper.countByJob();

        // List jobList = new ArrayList();
        // for (Map map : list) {
        //     jobList.add(map.get("pos"));
        // }
        //1.获取jobList职位列表
        List jobList = list.stream().map(item -> item.get("pos")).toList();

        //2.获取dataList各职位总人数列表
        List dataList = list.stream().map(item -> item.get("sum")).toList();

        //3.封装JobOption对象，并返回
        return new JobOption(jobList, dataList);
    }

    @Override
    public List<Map> empGenderData() {
        //调用mapper，获取员工性别数据
        return empMapper.countByGender();
    }

    @Override
    public List<Map> getStudentDegreeData() {
        return studentMapper.countStudentDegreeData();
    }

    @Override
    public ClazzCountOption getStudentCountData() {
        List<Map<String, Object>> countList = studentMapper.getStudentCount();
        if(!CollectionUtils.isEmpty(countList)){
            List<Object> clazzList = countList.stream().map(map -> {
                return map.get("cname");
            }).toList();

            List<Object> dataList = countList.stream().map(map -> {
                return map.get("scount");
            }).toList();

            return new ClazzCountOption(clazzList, dataList);
        }
        return null;
    }
}
