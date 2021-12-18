package com.bt.production.vo;

public class FactoryVo {
	
	private int factory_no;
	private int product_type_no;
	private int branch_no;
	
	public FactoryVo() {
		super();
	}

	public FactoryVo(int factory_no, int product_type_no, int branch_no) {
		super();
		this.factory_no = factory_no;
		this.product_type_no = product_type_no;
		this.branch_no = branch_no;
	}

	public int getFactory_no() {
		return factory_no;
	}

	public void setFactory_no(int factory_no) {
		this.factory_no = factory_no;
	}

	public int getProduct_type_no() {
		return product_type_no;
	}

	public void setProduct_type_no(int product_type_no) {
		this.product_type_no = product_type_no;
	}

	public int getBranch_no() {
		return branch_no;
	}

	public void setBranch_no(int branch_no) {
		this.branch_no = branch_no;
	}
	
	
}
