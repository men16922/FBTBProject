package com.bt.mapper;

import com.bt.vo.EmployeeRankVo;


public interface EmployeeRankSQLMapper {

	public EmployeeRankVo selectByRankNo(int rank_no);

}
