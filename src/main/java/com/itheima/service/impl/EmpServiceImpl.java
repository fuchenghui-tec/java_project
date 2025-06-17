package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.entity.*;
import com.itheima.mapper.EmpExprMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.service.EmpLogService;
import com.itheima.service.EmpService;
import com.itheima.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;

    /**
     * 分页查询
     * @param page     页码  1、2、3
     * @param pageSize 每页记录数 5、10
     * @return
     */
    // @Override
    // public PageBean page(Integer page, Integer pageSize) {
    //     //1.调用mapper获取总记录数  total
    //     // select count(*) from emp;
    //     Long total = empMapper.count();
    //
    //     //2.调用mapper获取分页列表数据 rows
    //     //select e.name, e.gender,e.image,d.name,e.job from emp e left join dept d on e.dept_id = d.id limit 0, 5;
    //     Integer start = (page - 1) * pageSize;  //计算起始索引
    //     List<Emp> empList = empMapper.page(start, pageSize);
    //
    //     //3.封装PageBean对象并返回
    //     return new PageBean(total, empList);
    // }

    // @Override
    // public PageBean page(Integer page, Integer pageSize) {
    //     //1.设置分页参数
    //     PageHelper.startPage(page, pageSize);
    //
    //     //2.调用mapper的列表查询方法
    //     List<Emp> empList = empMapper.list();
    //     Page p = (Page) empList;        //强转对象，Page继承了ArrayList
    //
    //     //分页只会对PageHelper.startPage()下面的第一条select进行处理
    //     // List<Emp> empList2 = empMapper.list();
    //
    //     //3.封装PageBean对象并返回
    //     return new PageBean(p.getTotal(), p.getResult());
    //     // return new PageBean(p.getTotal(), p);
    //     // return new PageBean(p.getTotal(), empList);
    // }

    @Override
    public PageBean page(EmpQueryParam param) {
        //1.设置分页参数
        PageHelper.startPage(param.getPage(), param.getPageSize());

        //2.调用mapper的列表查询方法
        List<Emp> empList = empMapper.list(param);
        Page p = (Page) empList;        //强转对象，Page继承了ArrayList

        //3.封装PageBean对象并返回
        return new PageBean(p.getTotal(), p.getResult());
    }

    @Transactional(rollbackFor = Exception.class)  //开启事务,rollbackFor指定处理所有异常，默认情况下只能处理运行时异常
    @Override
    public void save(Emp emp) throws Exception{
        try {
            //1.保存员工的基本信息到emp表
            //1.1 补充缺失的字段
            emp.setPassword("123456");
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            //1.2 调用mapper,保存员工的基本信息到emp表
            empMapper.insert(emp);

            Integer id = emp.getId();
            log.info("id={}", id);

            // 模拟异常
            // int i = 1/0;
            // 模拟非运行时异常
            // if (true){
            //     throw new Exception("出错了！！！");
            // }

            //2.保存员工的工作经历信息到emp_expr表
            List<EmpExpr> exprList = emp.getExprList();
            //2.1 关联员工id
            if (!CollectionUtils.isEmpty(exprList)) {
                exprList.forEach((expr)->{
                    expr.setEmpId(id);
                });
                //2.2 调用mapper方法,批量保存工作经历数据
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            // 无论新增员工成功与否，都需要添加操作日志
            EmpLog empLog = new EmpLog();
            empLog.setOperateTime(LocalDateTime.now());
            empLog.setInfo("插入员工信息: "+emp);
            empLogService.insertLog(empLog);
        }
    }

    @Transactional  //0.开启事务（涉及到多张表的删除操作）
    public void delete(List<Integer> ids) {
        //1.删除员工的基本信息数据 emp表
        empMapper.deleteBatch(ids);

        //2.删除员工的经历信息数据  emp_expr表
        empExprMapper.deleteBatch(ids);
    }

    @Override
    public Emp getById(Integer id) {
        // 方式一：联表查询
        // 调用mapper的查询方法，获取员工基本信息以及经历列表信息
        // return empMapper.getById(id);

        //方式二：单表查询
        //1.查询员工基本信息，封装到Emp对象中
        Emp emp = empMapper.getById2(id);

        //2.查询员工经历列表信息，封装到Emp对象中
        List<EmpExpr> empExprList = empExprMapper.getByEmpId(id);
        emp.setExprList(empExprList);

        //3.返回员工Emp对象
        return emp;
    }

    @Transactional  //开启事务
    public void update(Emp emp) {
        //1.修改员工的基本信息 --- emp表
        //1.1 补充基础属性--更新时间
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.update(emp);

        //2.修改员工的工作经历信息 --- emp_expr表
        //先删后增
        empExprMapper.deleteByEmpId(emp.getId());
        List<EmpExpr> exprList = emp.getExprList();

        if(!CollectionUtils.isEmpty(exprList)) {
            //关联员工id
            exprList.forEach(expr -> {
                expr.setEmpId(emp.getId());
            });
            empExprMapper.insertBatch(exprList);
        }
    }

    @Override
    public List<Emp> list() {
        return empMapper.list(null);
    }

    @Override
    public EmpLoginInfo login(Emp emp) {
        //1.调用mapper查询员工
        Emp empDB = empMapper.selectUsernameAndPassword(emp);

        //2.判断用户密码是否正确（其实就是判断查出来的数据是否为空）
        if (empDB != null) {
            //完善登录逻辑，登录成功需要生成JWT令牌
            //自定义有效载荷
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", empDB.getId());
            claims.put("username", empDB.getUsername());
            //调用JWT工具类，生成令牌
            String jwt = JwtUtils.generateJwt(claims);

            //3.如果查到数据，就构造EmpLoginInfo对象并返回
            return new EmpLoginInfo(empDB.getId(), empDB.getUsername(), empDB.getName(), jwt);
        }

        //3.如果数据查出来为空，代表用户名密码错误，直接返回null
        return null;
    }
}
