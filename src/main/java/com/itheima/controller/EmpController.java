package com.itheima.controller;

import com.itheima.annotation.Log;
import com.itheima.entity.Emp;
import com.itheima.entity.EmpQueryParam;
import com.itheima.entity.PageBean;
import com.itheima.entity.Result;
import com.itheima.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/emps")
@RestController
public class EmpController {
    @Autowired
    private EmpService empService;

    /**
     * 分页查询
     * @param param
     * @return
     */
    @GetMapping
    public Result page(EmpQueryParam param){
        log.info("分页查询：{}, {}, {},{}, {}, {}", param.getName(), param.getGender(), param.getBegin(), param.getEnd(), param.getPage(), param.getPageSize());
        PageBean pageBean = empService.page(param);
        return Result.success(pageBean);
    }

    /**
     * 新增员工
     * @param emp
     * @return
     */
    @Log
    @PostMapping
    public Result save(@RequestBody Emp emp) throws Exception {
        log.info("新增员工：{}", emp);
        empService.save(emp);
        return Result.success();
    }

    @Log
    @DeleteMapping
    // 直接通过数组即可接收前端传递的数组值
    // public Result delete(Integer[] ids){
    // 通过集合类型接收前端传递的数组值，需要加注解 @RequestParam
    public Result delete(@RequestParam List<Integer> ids){
        log.info("删除员工，ids = {}", ids);
        empService.delete(ids);
        return Result.success();
    }

    /**
     * 员工回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("回显员工，id = {}", id);
        Emp emp = empService.getById(id);
        return Result.success(emp);
    }

    /**
     * 修改员工
     * @param emp
     * @return
     */
    @Log
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("修改员工：emp={}", emp);
        empService.update(emp);
        return Result.success();
    }

    /**
     * 查询所有的员工
     */
    @GetMapping("/list")
    public Result list(){
        log.info("查询所有的员工数据");
        List<Emp> empList = empService.list();
        return Result.success(empList);
    }
}
