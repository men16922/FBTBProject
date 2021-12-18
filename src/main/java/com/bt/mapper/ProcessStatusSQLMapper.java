package com.bt.mapper;

import java.util.List;

import com.bt.vo.ProcessStatusVo;
import com.bt.vo.StoreOrderDetailVo;

public interface ProcessStatusSQLMapper {

   public ProcessStatusVo selectByODn(int store_order_detail_no);
   
   public void insert(ProcessStatusVo processStatusVo);
   
   public int createKey();
   
   public int selectPn(StoreOrderDetailVo storeOrderDetailVo);
   
   public ProcessStatusVo getProcessNo(int store_order_detail_no);
   
   public void insertDelivery(ProcessStatusVo processStatusVo);
   
   public void reject(ProcessStatusVo processStatusVo);
   
   public List<ProcessStatusVo> selectBySodn(int store_order_detail_no);
}