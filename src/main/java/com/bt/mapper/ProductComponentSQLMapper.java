package com.bt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.production.vo.ProductComponentVo;

public interface ProductComponentSQLMapper {

   public List<ProductComponentVo> selectComp();

   public ProductComponentVo selectCompNo(int comp_no);

   public ProductComponentVo selectCompName(String comp_name);

   public ProductComponentVo getFactoryOrderDetailInfo(@Param("emp_code") int emp_code,
         @Param("factory_order_res_no") String factory_order_res_no,
         @Param("factory_order_detail_no") int factory_order_detail_no);
   
   public void insert(
		   @Param("comp_no") int comp_no,
		   @Param("comp_name") String comp_name,
		   @Param("comp_price") String comp_price);
   
   public int createKey();

}