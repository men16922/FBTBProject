package com.bt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.vo.BranchTypeVo;
import com.bt.vo.BranchVo;

public interface BranchTypeSQLMapper {
	
	public BranchTypeVo	selectByNo(int branch_no);

	public List<BranchVo> selectByBranchName(
			@Param("dept_no") int dept_no, 
			@Param("branch_name") String branch_name);
	
	/*
	public List<BranchVo> selectByBranchName(
			@Param("dept_no") int dept_no, 
			@Param("branch_name") String branch_name);

	 */
	}
