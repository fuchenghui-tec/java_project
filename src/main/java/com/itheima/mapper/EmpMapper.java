package com.itheima.mapper;

import com.itheima.entity.Emp;
import com.itheima.entity.EmpQueryParam;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
    /**
     * 统计员工总记录数
     * @return
     */
    // @Select("select count(*) from emp")
    // Long count();

    /**
     * 分页查询
     * @param start
     * @param pageSize
     * @return
     */
    // @Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id limit #{start}, #{pageSize}")
    // List<Emp> page(Integer start, Integer pageSize);

    /**
     * 列表查询
     * @return
     */
    // @Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id")
    // 基于XML开发--动态SQL
    List<Emp> list(EmpQueryParam param);

    /**
     * 新增员工基本信息
     * @param emp
     */
    // @Options(useGeneratedKeys = true, keyProperty = "id")   //获取主键的值并赋给id属性
    // @Insert("insert into emp values (null,#{username},#{password},#{name},#{gender},#{phone},#{job},#{salary},#{image}," +
    //         "#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);

    /**
     * 批量删除员工信息
     * @param ids
     */
    // @Delete("delete from emp where id in (1,2,3)")
    // 基于XML开发--动态sql--foreach
    void deleteBatch(List<Integer> ids);

    /**
     * 根据id查询员工基本信息以及经历列表信息
     * @param id
     * @return
     */
    // @Select("select * from emp e left join emp_expr ee on e.id = ee.emp_id where e.id = #{id}")
    Emp getById(Integer id);

    @Select("select * from emp where id = #{id}")
    Emp getById2(Integer id);

    /**
     * 更新员工--动态SQL
     * @param emp
     */
    void update(Emp emp);

    /**
     * 统计各职位员工人数
     * @return
     */
    @MapKey("pos")  //可以不加，不影响业务...
    List<Map> countByJob();

    @MapKey("name")
    List<Map> countByGender();

    /**
     * 根据部门id统计员工人数
     * @param deptId
     * @return
     */
    @Select("select count(*) from emp where dept_id = #{deptId}")
    Integer countByDeptId(Integer deptId);

    /**
     * 根据用户名和密码查询员工--登录
     * @param emp
     * @return
     */
    @Select("select * from emp where username = #{username} and password = #{password}")
    Emp selectUsernameAndPassword(Emp emp);

}
