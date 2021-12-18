package com.bt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.production.vo.DeliveryVo;

public interface DeliverySQLMapper {
	
	public void insert(DeliveryVo deliveryVo);
	
	public List<DeliveryVo> selectAll(
			@Param("session_branch_no") int session_branch_no,
			@Param("currPage") int currPage
			);
	
	public List<DeliveryVo> selectBySearch(
			@Param("store_order_detail_no") int store_order_detail_no,
			@Param("invoice_no") int invoice_no,
			@Param("start_date") String start_date,
		    @Param("end_date") String end_date,
			@Param("session_branch_no") int session_branch_no,
			@Param("branch_no") int branch_no,
			@Param("currPage") int currPage
			);
	
	public List<DeliveryVo> selectBySodn(int store_order_detail_no);
	
	public int selectAllCount(int branch_no);
	
	public int selectSearchCount(
			@Param("store_order_detail_no") int store_order_detail_no,
			@Param("invoice_no") int invoice_no,
			@Param("start_date") String start_date,
		    @Param("end_date") String end_date,
			@Param("session_branch_no") int session_branch_no,
			@Param("branch_no") int branch_no
			);
}
