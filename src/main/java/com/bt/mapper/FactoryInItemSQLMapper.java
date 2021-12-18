package com.bt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.production.vo.FactoryInItemVo;

public interface FactoryInItemSQLMapper {
   
   public int createKey();
   
   public List<FactoryInItemVo> selectAll(
         @Param("branch_no") int branch_no,
         @Param("currPage") int currPage,
         @Param("sort") String sort
         );
   
   public void insert(
         @Param("factory_order_detail_no") int factory_order_detail_no, 
         @Param("factory_in_item_qty") int factory_in_item_qty, 
         @Param("emp_code") int emp_code);
   
   public List<FactoryInItemVo> selectInItem(
         @Param("factory_in_item_code") int factory_in_item_code,
         @Param("comp_no") int comp_no,
         @Param("start_date") String start_date,
         @Param("end_date") String end_date,
         @Param("emp_code") int emp_code,
         @Param("supplier_no") int supplier_no,
         @Param("branch_no") int branch_no,
         @Param("currPage") int currPage,
         @Param("sort") String sort);
   
   public int selectAllCount(int branch_no);
   
   public int selectSearchCount(
         @Param("factory_in_item_code") int factory_in_item_code,
         @Param("comp_no") int comp_no,
         @Param("start_date") String start_date,
         @Param("end_date") String end_date,
         @Param("emp_code") int emp_code,
         @Param("supplier_no") int supplier_no,
         @Param("branch_no") int branch_no
         );
   
}