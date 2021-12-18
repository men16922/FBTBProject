package com.bt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.production.vo.FactoryOutItemVo;

public interface FactoryOutItemSQLMapper {
   
   public int createKey();
   
   public void insert(
		   @Param("factory_out_item_code") int factory_out_item_code,
		   @Param("factory_out_item_no") int factory_out_item_no,
		   @Param("product_no") int product_no,
		   @Param("factory_out_item_qty") int factory_out_item_qty,
		   @Param("emp_code") int emp_code,
		   @Param("factory_out_item_date") String factory_out_item_date,
		   @Param("branch_no") int branch_no	 
		   );
   
   public List<FactoryOutItemVo> selectAll(
         @Param("branch_no") int branch_no,
         @Param("currPage") int currPage,
         @Param("sort") String sort);
   
   public List<FactoryOutItemVo> selectOutItem(
         @Param("factory_out_item_no") int factory_out_item_no,
         @Param("product_no") int product_no,
         @Param("branch_no") int branch_no,
         @Param("start_date") String start_date,
         @Param("end_date") String end_date,
         @Param("session_branch_no") int session_branch_no,
         @Param("currPage") int currPage,
         @Param("sort") String sort);
   
   public int selectAllCount(int branch_no);
   
   public int selectSearchCount(
         @Param("factory_out_item_no") int factory_out_item_no,
         @Param("product_no") int product_no,
         @Param("branch_no") int branch_no,
         @Param("start_date") String start_date,
         @Param("end_date") String end_date,
         @Param("session_branch_no") int session_branch_no);
}