package com.bt.vo;

import java.util.Date;

public class ProductFileVo {
	private int product_file_no; 
	private int product_no;
	private String product_file_link_path;
	private String product_file_real_path;
	private Date product_file_upload_date;
	
	public ProductFileVo() {
		// TODO Auto-generated constructor stub
	}

	public ProductFileVo(int product_file_no, int product_no, String product_file_link_path,
			String product_file_real_path, Date product_file_upload_date) {
		super();
		this.product_file_no = product_file_no;
		this.product_no = product_no;
		this.product_file_link_path = product_file_link_path;
		this.product_file_real_path = product_file_real_path;
		this.product_file_upload_date = product_file_upload_date;
	}

	public int getProduct_file_no() {
		return product_file_no;
	}

	public void setProduct_file_no(int product_file_no) {
		this.product_file_no = product_file_no;
	}

	public int getProduct_no() {
		return product_no;
	}

	public void setProduct_no(int product_no) {
		this.product_no = product_no;
	}

	public String getProduct_file_link_path() {
		return product_file_link_path;
	}

	public void setProduct_file_link_path(String product_file_link_path) {
		this.product_file_link_path = product_file_link_path;
	}

	public String getProduct_file_real_path() {
		return product_file_real_path;
	}

	public void setProduct_file_real_path(String product_file_real_path) {
		this.product_file_real_path = product_file_real_path;
	}

	public Date getProduct_file_upload_date() {
		return product_file_upload_date;
	}

	public void setProduct_file_upload_date(Date product_file_upload_date) {
		this.product_file_upload_date = product_file_upload_date;
	}
	
	
	
}
