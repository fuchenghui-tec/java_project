package com.itheima.mapper;

import com.itheima.entity.EmpExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpExprMapper {
    /**
     * 批量插入员工经历数据
     * @param exprList
     */
    //基于XML开发-动态SQL--<foreach>
    void insertBatch(List<EmpExpr> exprList);

    /**
     * 根据员工id批量删除员工经历
     * @param empIds
     */
    //基于XML开发-动态SQL--<foreach>
    void deleteBatch(List<Integer> empIds);

    /**
     * 根据员工id查询员工经历
     * @param empId
     * @return
     */
    List<EmpExpr> getByEmpId(Integer empId);

    /**
     * 根据id删除员工经历
     * @param id
     */
    void deleteByEmpId(Integer id);
}
