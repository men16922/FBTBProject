package com.bt.mapper;

import com.bt.vo.ProcessListVo;

public interface ProcessListSQLMapper {

	public ProcessListVo selectByPrNo(int process_no);
	
	public ProcessListVo selectByMax(int store_order_detail_no);
}
