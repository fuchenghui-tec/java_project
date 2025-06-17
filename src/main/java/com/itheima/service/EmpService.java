package com.itheima.service;

import com.itheima.entity.Emp;
import com.itheima.entity.EmpLoginInfo;
import com.itheima.entity.EmpQueryParam;
import com.itheima.entity.PageBean;

import java.util.List;

public interface EmpService {
    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    PageBean page(EmpQueryParam param);

    /**
     * 新增员工
     * @param emp
     */
    void save(Emp emp) throws Exception;

    /**
     * 删除员工
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 员工回显
     * @param id
     * @return
     */
    Emp getById(Integer id);

    /**
     * 修改员工
     * @param emp
     */
    void update(Emp emp);

    /**
     * 查询所有的员工数据
     */
    List<Emp> list();

    /**
     * 员工登录
     * @param emp
     * @return
     */
    EmpLoginInfo login(Emp emp);
}
