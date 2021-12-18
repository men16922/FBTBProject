package com.bt.sales.vo;

import java.util.Date;

public class Store_out_itemVo {
	private int store_out_item_no;
	private int product_no;
	private int out_item_qty;
	private int emp_code;
	private Date out_item_date;
	public Store_out_itemVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Store_out_itemVo(int store_out_item_no, int product_no, int out_item_qty, int emp_code, Date out_item_date) {
		super();
		this.store_out_item_no = store_out_item_no;
		this.product_no = product_no;
		this.out_item_qty = out_item_qty;
		this.emp_code = emp_code;
		this.out_item_date = out_item_date;
	}
	public int getStore_out_item_no() {
		return store_out_item_no;
	}
	public void setStore_out_item_no(int store_out_item_no) {
		this.store_out_item_no = store_out_item_no;
	}
	public int getProduct_no() {
		return product_no;
	}
	public void setProduct_no(int product_no) {
		this.product_no = product_no;
	}
	public int getOut_item_qty() {
		return out_item_qty;
	}
	public void setOut_item_qty(int out_item_qty) {
		this.out_item_qty = out_item_qty;
	}
	public int getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(int emp_code) {
		this.emp_code = emp_code;
	}
	public Date getOut_item_date() {
		return out_item_date;
	}
	public void setOut_item_date(Date out_item_date) {
		this.out_item_date = out_item_date;
	}
	
}
