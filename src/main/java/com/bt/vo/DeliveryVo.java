package com.bt.vo;

import java.util.Date;

public class DeliveryVo {

   private int delivery_no;
   private int invoice_no;
   private int emp_code;
   private int store_order_detail_no;
   private Date delivery_date;
   private Date arrive_delivery_date;
   
   public DeliveryVo() {
      super();
   }

   public DeliveryVo(int delivery_no, int invoice_no, int emp_code, int store_order_detail_no, Date delivery_date,
         Date arrive_delivery_date) {
      super();
      this.delivery_no = delivery_no;
      this.invoice_no = invoice_no;
      this.emp_code = emp_code;
      this.store_order_detail_no = store_order_detail_no;
      this.delivery_date = delivery_date;
      this.arrive_delivery_date = arrive_delivery_date;
   }

   public int getDelivery_no() {
      return delivery_no;
   }

   public void setDelivery_no(int delivery_no) {
      this.delivery_no = delivery_no;
   }

   public int getInvoice_no() {
      return invoice_no;
   }

   public void setInvoice_no(int invoice_no) {
      this.invoice_no = invoice_no;
   }

   public int getEmp_code() {
      return emp_code;
   }

   public void setEmp_code(int emp_code) {
      this.emp_code = emp_code;
   }

   public int getStore_order_detail_no() {
      return store_order_detail_no;
   }

   public void setStore_order_detail_no(int store_order_detail_no) {
      this.store_order_detail_no = store_order_detail_no;
   }

   public Date getDelivery_date() {
      return delivery_date;
   }

   public void setDelivery_date(Date delivery_date) {
      this.delivery_date = delivery_date;
   }

   public Date getArrive_delivery_date() {
      return arrive_delivery_date;
   }

   public void setArrive_delivery_date(Date arrive_delivery_date) {
      this.arrive_delivery_date = arrive_delivery_date;
   }

   
   
}