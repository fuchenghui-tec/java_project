package com.itheima.service.impl;

import com.itheima.entity.Dept;
import com.itheima.exception.BusinessException;
import com.itheima.mapper.DeptMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 业务逻辑处理层
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private EmpMapper empMapper;

    /**
     * 查询部门列表
     * @return
     */
    public List<Dept> list(){
        //1.调用mapper的方法，获取列表数据并返回
        return deptMapper.list();
    }

    /**
     * 根据id删除部门
     * @param id
     */
    @Override
    public void delete(Integer id) {
        //逻辑优化
        //1. 判断该部门下是否有员工
        Integer count = empMapper.countByDeptId(id);

        //2. 如果有, 提示错误信息
        if (count > 0) {
            //抛异常
            throw new BusinessException("对不起, 该部门下有员工, 不能直接删除！！");
        }

        //调用mapper的删除方法
        // Integer row = deptMapper.delete(id);
        // System.out.println("影响的行数："+row);
        deptMapper.delete(id);
    }

    /**
     * 新增部门
     * @param dept
     */
    @Override
    public void save(Dept dept) {
        //1.补充基础属性
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());

        //2.调用mapper的新增方法
        deptMapper.insert(dept);
    }

    /**
     * 数据回显
     * @param id
     * @return
     */
    @Override
    public Dept getById(Integer id) {
        //调用mapper的查询方法
        return deptMapper.getById(id);
    }

    /**
     * 修改部门
     * @param dept
     */
    @Override
    public void update(Dept dept) {
        //1.补充基础属性
        dept.setUpdateTime(LocalDateTime.now());
        //2.调用mapper
        deptMapper.update(dept);
    }
}
