package com.bt.mapper;

import org.apache.ibatis.annotations.Param;

import com.bt.vo.AreaVo;


public interface AreaSQLMapper {

	public AreaVo selectAll();

	public void addArea(
			@Param("dept_no") int dept_no, 
			@Param("branch_name") String branch_name
			);

	public AreaVo selectByDeptNo(int dept_no);

}