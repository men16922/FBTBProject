package com.bt.vo;

public class BranchStockVo {

   private int branch_stock_no;
   private int branch_no;
   private int product_no;
   private int branch_stock_qty;
   
   public BranchStockVo() {
      super();
   }
   
   public BranchStockVo(int branch_stock_no, int branch_no, int product_no, int branch_stock_qty) {
      super();
      this.branch_stock_no = branch_stock_no;
      this.branch_no = branch_no;
      this.product_no = product_no;
      this.branch_stock_qty = branch_stock_qty;
   }

   public int getBranch_stock_no() {
      return branch_stock_no;
   }

   public void setBranch_stock_no(int branch_stock_no) {
      this.branch_stock_no = branch_stock_no;
   }

   public int getBranch_no() {
      return branch_no;
   }

   public void setBranch_no(int branch_no) {
      this.branch_no = branch_no;
   }

   public int getProduct_no() {
      return product_no;
   }

   public void setProduct_no(int product_no) {
      this.product_no = product_no;
   }

   public int getBranch_stock_qty() {
      return branch_stock_qty;
   }

   public void setBranch_stock_qty(int branch_stock_qty) {
      this.branch_stock_qty = branch_stock_qty;
   }
   
   
   
   
}