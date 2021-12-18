package com.bt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.production.vo.ComponentStockVo;

public interface ComponentStockSQLMapper {

	public List<ComponentStockVo> selectByBn(int branch_no);

	public void update(@Param("factory_in_item_qty") int factory_in_item_qty, @Param("comp_no") int comp_no);

	public ComponentStockVo selectByCn(int comp_no);

	public List<ComponentStockVo> selectByBranchNo(@Param("branch_no") int branch_no,
			@Param("curr_page") int curr_page);

	public int factory_branch_stock_list_paging(int branch_no);

	public void minusComp(int factory_out_item_qty);
	
	public void insert(
			@Param("branch_no") int branch_no, 
			@Param("comp_no") int comp_no,
			@Param("comp_qty") int comp_qty);
	
}