package com.bt.mapper;

import com.bt.vo.BranchStockVo;

public interface BranchStockSQLMapper {

   public int store_branch_stock_list_paging(int branch_no);

   public BranchStockVo selectByBranchNo(int branch_no);

   public BranchStockVo selectByProductNo(int product_no);
   
   public BranchStockVo selectAll();
   


}