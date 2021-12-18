package com.bt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.vo.ProcessManagementVo;

public interface ProcessManagementSQLMapper {
   
   public ProcessManagementVo selectByDn(int store_order_detail_no);
   
   public List<ProcessManagementVo> getReservationDetailInfo
      (@Param("emp_code")int emp_code, @Param("store_order_res_no")String store_order_res_no);
}