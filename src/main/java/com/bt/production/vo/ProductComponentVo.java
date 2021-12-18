package com.bt.production.vo;

public class ProductComponentVo {
	
	private int comp_no;
	private String comp_name;
	private String comp_price;
	
	public ProductComponentVo() {
		super();
	}

	public ProductComponentVo(int comp_no, String comp_name, String comp_price) {
		super();
		this.comp_no = comp_no;
		this.comp_name = comp_name;
		this.comp_price = comp_price;
	}

	public int getComp_no() {
		return comp_no;
	}

	public void setComp_no(int comp_no) {
		this.comp_no = comp_no;
	}

	public String getComp_name() {
		return comp_name;
	}

	public void setComp_name(String comp_name) {
		this.comp_name = comp_name;
	}

	public String getComp_price() {
		return comp_price;
	}

	public void setComp_price(String comp_price) {
		this.comp_price = comp_price;
	}

	
	
}
