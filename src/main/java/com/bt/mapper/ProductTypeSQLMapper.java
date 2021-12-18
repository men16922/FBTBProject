package com.bt.mapper;

import com.bt.vo.ProductTypeVo;


public interface ProductTypeSQLMapper {


   public ProductTypeVo selectAll();

   public ProductTypeVo selectByProductNo(int product_no);


}