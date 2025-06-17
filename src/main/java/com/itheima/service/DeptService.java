package com.itheima.service;

import com.itheima.entity.Dept;

import java.util.List;

public interface DeptService {
    /**
     * 查询部门列表
     * @return
     */
    public List<Dept> list();

    /**
     * 根据id删除部门
     * @param id
     */
    void delete(Integer id);

    /**
     * 新增部门
     * @param dept
     */
    void save(Dept dept);

    /**
     * 数据回显
     * @param id
     * @return
     */
    Dept getById(Integer id);

    /**
     * 修改部门
     * @param dept
     */
    void update(Dept dept);
}
