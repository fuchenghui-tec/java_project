package com.itheima.controller;

import com.itheima.annotation.Log;
import com.itheima.entity.Dept;
import com.itheima.entity.Result;
import com.itheima.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 请求处理类
 */
@Slf4j              //会自动生成log对象，用于打日志
@RequestMapping("/depts")   //公共路径抽取，完整路径=类上的+方法上的
@RestController     //等价于@Controller+@ResponseBody
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 获取部门列表
     * @return
     */
    @GetMapping
    public Result list(){
        //1.调用service，获取数据
        List<Dept> depts = deptService.list();

        //3.响应数据（json格式）
        return Result.success(depts);
    }

    // 方式一：原始方式获取请求参数
    // @RequestMapping(value = "/depts", method = RequestMethod.DELETE)
    // @DeleteMapping("/depts")
    // public Result delete(HttpServletRequest request){
    //     String id = request.getParameter("id");
    //     int idInt = Integer.parseInt(id);
    //
    //     System.out.println("id="+idInt);
    //     return Result.success();
    // }

    // 方式二：通过spring提供的@RequestParam注解获取请求参数
    // @RequestParam加上之后，required默认值为true代表前端就必须要传递该参数，否则报错400，Bad Request, 如果不需要限制，可以将其设置为required=false
    // public Result delete(@RequestParam(value = "id", required = false) Integer deptId){
    //     System.out.println("deptId = " + deptId);
    //     //调用service的删除方法
    //     deptService.delete(deptId);
    //     return Result.success();
    // }

    // 方式三：形参变量名与请求参数名一致，可以自动封装简单参数值【推荐】
    @Log
    @DeleteMapping
    public Result delete(Integer id){
        log.info("id = {}", id);
        //调用service的删除方法
        deptService.delete(id);
        return Result.success();
    }

    /**
     * 新增部门
     * @param dept
     * @return
     */
    // @RequestBody 作用：用来接收json格式数据
    @Log
    @PostMapping
    public Result save(@RequestBody Dept dept){
        log.info("dept = {}", dept);
        //调用service
        deptService.save(dept);
        return Result.success();
    }

    /**
     * 回显
     * @param id
     * @return
     */
    // @PathVariable 作用：用于接收路径参数值
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("id = {}", id);
        //调用service的方法
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    /**
     * 修改部门
     * @param dept
     * @return
     */
    @Log
    @PutMapping
    public Result update(@RequestBody Dept dept){
        log.info("dept = {}", dept);
        deptService.update(dept);
        return Result.success();
    }
}
