package com.bt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.vo.ProductVo;

public interface ProductSQLMapper {

   public List<ProductVo> selectAll(int curr_page);

   public void addProduct(ProductVo productvo);

   public ProductVo selectByProduct_no(int product_no);

   public int createKey();

   public int getBoardList_product_paging();

   public List<ProductVo> selectPTN(int emp_code);

   public ProductVo selectByPn(int product_no);
   
   public List<ProductVo> selectByBranchNo(@Param("branch_no")int branch_no,@Param("curr_page") int curr_page);

   public int store_branch_stock_list_paging(int branch_no);

   public int factory_branch_stock_list_paging(int branch_no);
   
   public ProductVo selectBySodn(int store_order_detail_no);
   
   public ProductVo getReservationDetailInfo
   (@Param("emp_code")int emp_code, @Param("store_order_res_no")String store_order_res_no,@Param("store_order_detail_no") int store_order_detail_no);

   public int createKey_product();

public void modifyProduct(ProductVo productVo);

public void deleteProduct(int product_no);
}