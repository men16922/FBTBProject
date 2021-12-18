package com.bt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.vo.StoreOrderDetailVo;


public interface StoreOrderDetailSQLMapper {

   public List<StoreOrderDetailVo> detailList(int branch_no);
   
   public List<StoreOrderDetailVo> readyDelivery(int branch_no);
   
   public List<StoreOrderDetailVo> selectBranchName();
   
   public StoreOrderDetailVo selectByno(int store_order_res_code);
   
   public Map<String,Object> getEnroll(int branch_no, int store_order_detail_no);
   
   public StoreOrderDetailVo selectBySodn(int store_order_detail_no);
   
   public List<StoreOrderDetailVo> selectAllOrder(
		     @Param("currPage") int currPage,
		     @Param("branch_no") int branch_no,
	         @Param("store_order_res_no") String store_order_res_no,
	         @Param("branch_name") String branch_name,
	         @Param("start_date") String start_date,
	         @Param("end_date") String end_date
	         );
   
   public List<StoreOrderDetailVo> selectBySORC(
         @Param("branch_no") int branch_no,
         @Param("store_order_res_code")int store_order_res_code
         );
   
   public int getAllCount(int branch_no);
   
   public int getSelectCount(
		     @Param("branch_no") int branch_no,
	         @Param("store_order_res_no") String store_order_res_no,
	         @Param("branch_name") String branch_name,
	         @Param("start_date") String start_date,
	         @Param("end_date") String end_date);
   
   public int getRequestCount(int branch_no);
   
   public int getreadyCount(int branh_no);
}