package com.bt.sales.vo;

public class CustomerVo {
	
	private int customer_code;
	private int customer_no;
	private String customer_name;
	private String customer_phone;
	private String customer_address;
	public CustomerVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CustomerVo(int customer_code, int customer_no, String customer_name, String customer_phone,
			String customer_address) {
		super();
		this.customer_code = customer_code;
		this.customer_no = customer_no;
		this.customer_name = customer_name;
		this.customer_phone = customer_phone;
		this.customer_address = customer_address;
	}
	public int getCustomer_code() {
		return customer_code;
	}
	public void setCustomer_code(int customer_code) {
		this.customer_code = customer_code;
	}
	public int getCustomer_no() {
		return customer_no;
	}
	public void setCustomer_no(int customer_no) {
		this.customer_no = customer_no;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getCustomer_phone() {
		return customer_phone;
	}
	public void setCustomer_phone(String customer_phone) {
		this.customer_phone = customer_phone;
	}
	public String getCustomer_address() {
		return customer_address;
	}
	public void setCustomer_address(String customer_address) {
		this.customer_address = customer_address;
	}

	
}
