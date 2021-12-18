package com.bt.vo;

public class ProductVo {

	private int product_no;
	private String product_name;
	private String product_detail;
	private int product_type_no;
	private int product_price;
	
	public ProductVo() {
		// TODO Auto-generated constructor stub
	}

	public ProductVo(int product_no, String product_name, String product_detail, int product_type_no,
			int product_price) {
		super();
		this.product_no = product_no;
		this.product_name = product_name;
		this.product_detail = product_detail;
		this.product_type_no = product_type_no;
		this.product_price = product_price;
	}

	public int getProduct_no() {
		return product_no;
	}

	public void setProduct_no(int product_no) {
		this.product_no = product_no;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_detail() {
		return product_detail;
	}

	public void setProduct_detail(String product_detail) {
		this.product_detail = product_detail;
	}

	public int getProduct_type_no() {
		return product_type_no;
	}

	public void setProduct_type_no(int product_type_no) {
		this.product_type_no = product_type_no;
	}

	public int getProduct_price() {
		return product_price;
	}

	public void setProduct_price(int product_price) {
		this.product_price = product_price;
	}
	
	
	
	
}
