package com.bt.sales.vo;

import java.util.Date;

public class Customer_reservation_listVo {
	private int res_no;
	private int order_status_no;
	private int product_no;
	private int customer_res_qty;
	private int emp_code;
	private int customer_no;
	private Date customer_res_date;
	public Customer_reservation_listVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Customer_reservation_listVo(int res_no, int order_status_no, int product_no, int customer_res_qty,
			int emp_code, int customer_no, Date customer_res_date) {
		super();
		this.res_no = res_no;
		this.order_status_no = order_status_no;
		this.product_no = product_no;
		this.customer_res_qty = customer_res_qty;
		this.emp_code = emp_code;
		this.customer_no = customer_no;
		this.customer_res_date = customer_res_date;
	}
	public int getRes_no() {
		return res_no;
	}
	public void setRes_no(int res_no) {
		this.res_no = res_no;
	}
	public int getOrder_status_no() {
		return order_status_no;
	}
	public void setOrder_status_no(int order_status_no) {
		this.order_status_no = order_status_no;
	}
	public int getProduct_no() {
		return product_no;
	}
	public void setProduct_no(int product_no) {
		this.product_no = product_no;
	}
	public int getCustomer_res_qty() {
		return customer_res_qty;
	}
	public void setCustomer_res_qty(int customer_res_qty) {
		this.customer_res_qty = customer_res_qty;
	}
	public int getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(int emp_code) {
		this.emp_code = emp_code;
	}
	public int getCustomer_no() {
		return customer_no;
	}
	public void setCustomer_no(int customer_no) {
		this.customer_no = customer_no;
	}
	public Date getCustomer_res_date() {
		return customer_res_date;
	}
	public void setCustomer_res_date(Date customer_res_date) {
		this.customer_res_date = customer_res_date;
	}
	
	
}
