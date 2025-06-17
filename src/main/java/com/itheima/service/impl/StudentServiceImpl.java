package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.StudentMapper;
import com.itheima.entity.PageBean;
import com.itheima.entity.Student;
import com.itheima.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public void save(Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.insert(student);
    }


    @Override
    public PageBean page(String name, Integer degree,Integer clazzId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        List<Student> studentList = studentMapper.list(name,degree,clazzId);
        Page<Student> p = (Page<Student>) studentList;

        return new PageBean(p.getTotal(), p.getResult());
    }


    @Override
    public Student getInfo(Integer id) {
        return studentMapper.getById(id);
    }


    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.update(student);
    }


    @Override
    public void delete(List<Integer> ids) {
        studentMapper.delete(ids);
    }


    @Override
    public void violationHandle(Integer id, Integer score) {
        studentMapper.updateViolation(id, score);
    }
}
