package com.bt.production.vo;

import java.util.Date;

public class FactoryInItemVo {
   
   private int factory_in_item_code;
   private int factory_order_detail_no;
   private int factory_in_item_qty;
   private int emp_code;
   private Date factory_in_item_date;

   public FactoryInItemVo() {
      super();
   }

   public FactoryInItemVo(int factory_in_item_code, int factory_order_detail_no, int factory_in_item_qty, int emp_code,
         Date factory_in_item_date) {
      super();
      this.factory_in_item_code = factory_in_item_code;
      this.factory_order_detail_no = factory_order_detail_no;
      this.factory_in_item_qty = factory_in_item_qty;
      this.emp_code = emp_code;
      this.factory_in_item_date = factory_in_item_date;
   }

   public int getFactory_in_item_code() {
      return factory_in_item_code;
   }

   public void setFactory_in_item_code(int factory_in_item_code) {
      this.factory_in_item_code = factory_in_item_code;
   }

   public int getFactory_order_detail_no() {
      return factory_order_detail_no;
   }

   public void setFactory_order_detail_no(int factory_order_detail_no) {
      this.factory_order_detail_no = factory_order_detail_no;
   }

   public int getFactory_in_item_qty() {
      return factory_in_item_qty;
   }

   public void setFactory_in_item_qty(int factory_in_item_qty) {
      this.factory_in_item_qty = factory_in_item_qty;
   }

   public int getEmp_code() {
      return emp_code;
   }

   public void setEmp_code(int emp_code) {
      this.emp_code = emp_code;
   }

   public Date getFactory_in_item_date() {
      return factory_in_item_date;
   }

   public void setFactory_in_item_date(Date factory_in_item_date) {
      this.factory_in_item_date = factory_in_item_date;
   }

}