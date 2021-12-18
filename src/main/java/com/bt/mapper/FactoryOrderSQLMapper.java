package com.bt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.production.vo.FactoryOrderVo;

public interface FactoryOrderSQLMapper {

   public int maxSeq();

   public int createKey();

   public void insert(
         @Param("factory_order_code") int factory_order_code,
         @Param("factory_order_res_no") String factory_order_res_no,
         @Param("emp_code") int emp_code
         );

   public FactoryOrderVo selectByFoc(int factory_order_code);
   
   public List<FactoryOrderVo> searchOrder(
         @Param("branch_no") int branch_no,
         @Param("factory_order_res_no") String factory_order_res_no,
         @Param("emp_code") int emp_code,
         @Param("start_date") String start_date,
         @Param("end_date") String end_date,
         @Param("currPage") int currPage,
         @Param("sort") String sort
         );
   
   public List<FactoryOrderVo> getFactoryOrderInfo(@Param("emp_code") int emp_code, @Param("curr_page") int curr_page);

   public FactoryOrderVo getFactoryOrderDetailInfo(@Param("emp_code") int emp_code,
         @Param("factory_order_res_no") String factory_order_res_no,
         @Param("factory_order_detail_no") int factory_order_detail_no);
   
   public List<FactoryOrderVo> selectAll(
         @Param("branch_no") int branch_no,
         @Param("currPage") int currPage,
         @Param("sort") String sort);
   
   public int selectAllCount(int branch_no);
   
   public int selectSearchCount(
         @Param("branch_no") int branch_no,
         @Param("factory_order_res_no") String factory_order_res_no,
         @Param("emp_code") int emp_code,
         @Param("start_date") String start_date,
         @Param("end_date") String end_date
         );
   
}