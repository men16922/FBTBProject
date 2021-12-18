package com.bt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.vo.StoreOrderReservationVo;

public interface StoreOrderReservationSQLMapper {

	public StoreOrderReservationVo selectByno(int store_order_res_code);
	
	List<StoreOrderReservationVo> getStoreOrderReservationInfo
	   (@Param("emp_code")int emp_code, @Param("curr_page")int curr_page);
}
