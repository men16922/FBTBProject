package com.bt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.production.vo.FactoryOrderDetailVo;


public interface FactoryOrderDetailSQLMapper {
   
   public void insert(
         @Param("factory_order_code") int factory_order_code,
         @Param("comp_no") int comp_no,
         @Param("supplier_no") int supplier_no,
         @Param("factory_order_qty") int factory_order_qty,
         @Param("factory_order_note") String factory_order_note);

   public List<FactoryOrderDetailVo> getFactoryOrderDetailInfo(@Param("emp_code")int emp_code, @Param("factory_order_res_no")String factory_order_res_no);
   
   public List<FactoryOrderDetailVo> getOrderDetail(int factory_order_code);
   
   public List<FactoryOrderDetailVo> getNoneOrder(int branch_no);
   
   public void update(int factory_order_detail_no);
   
   public FactoryOrderDetailVo selectByFodn(int factory_order_detail_no);
   
   public int selectOrderCount(int branch_no);
   
}