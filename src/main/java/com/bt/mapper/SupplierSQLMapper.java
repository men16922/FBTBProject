package com.bt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.bt.production.vo.SupplierVo;

public interface SupplierSQLMapper {
   
   public SupplierVo selectBySn(int supplier_no);
   
   public List<SupplierVo> selectAll();

   public List<SupplierVo> selectByCompNo(int comp_no);
   
   public SupplierVo getFactoryOrderDetailInfo
   (@Param("emp_code")int emp_code, @Param("factory_order_res_no")String factory_order_res_no, @Param("factory_order_detail_no")int factory_order_detail_no);
}