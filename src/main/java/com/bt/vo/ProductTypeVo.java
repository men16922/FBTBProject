package com.bt.vo;

public class ProductTypeVo {
	private int product_type_no;
	private String product_type_name;
	
	public ProductTypeVo() {
		// TODO Auto-generated constructor stub
	}

	public ProductTypeVo(int product_type_no, String product_type_name) {
		super();
		this.product_type_no = product_type_no;
		this.product_type_name = product_type_name;
	}

	public int getProduct_type_no() {
		return product_type_no;
	}

	public void setProduct_type_no(int product_type_no) {
		this.product_type_no = product_type_no;
	}

	public String getProduct_type_name() {
		return product_type_name;
	}

	public void setProduct_type_name(String product_type_name) {
		this.product_type_name = product_type_name;
	}
	
	
	
}
