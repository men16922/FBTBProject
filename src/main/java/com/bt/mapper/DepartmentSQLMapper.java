package com.bt.mapper;

import java.util.List;

import com.bt.vo.DeptVo;

public interface DepartmentSQLMapper {

	public List<DeptVo> selectAll();

	public DeptVo selectByDeptNo(int dept_no);

	public List<DeptVo> selectByLogisticsName();

	public DeptVo selectByDeptNo_All();

	public DeptVo selectByBranchNo(int branchNo);

}