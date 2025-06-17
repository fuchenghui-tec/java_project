package com.itheima.mapper;

import com.itheima.entity.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {
    /**
     * 查询部门列表
     * @return
     */
    // 数据封装方式一：手动结果映射
    // @Results({
    //         @Result(column = "create_time", property = "createTime"),
    //         @Result(column = "update_time", property = "updateTime")
    // })

    // 数据封装方式二：起别名
    // @Select("select id, name, create_time createTime, update_time updateTime from dept")

    // 数据封装方式三：全局配置，开启驼峰命名规则映射
    @Select("select * from dept")
    public List<Dept> list();

    /**
     * 根据id删除部门
     * @param id
     */
    @Delete("delete from dept where id = #{id}")
    void delete(Integer id);
    // Integer delete(Integer id);

    /**
     * 新增部门
     * @param dept
     */
    @Insert("insert into dept(name, create_time, update_time) values (#{name}, #{createTime}, #{updateTime})")
    void insert(Dept dept);

    /**
     * 根据id查询部门数据
     * @param id
     * @return
     */
    @Select("select * from dept where id = #{id}")
    Dept getById(Integer id);

    /**
     * 根据id更新部门
     * @param dept
     */
    // @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    // @Update("update dept set name = #{name} where id = #{id}")
    void update(Dept dept);
}
