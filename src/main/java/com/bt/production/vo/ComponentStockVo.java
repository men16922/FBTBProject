package com.bt.production.vo;

public class ComponentStockVo {
	
	private int comp_stock_no;
	private int branch_no;
	private int comp_no;
	private int comp_qty;
	
	public ComponentStockVo() {
		super();
	}

	public ComponentStockVo(int comp_stock_no, int branch_no, int comp_no, int comp_qty) {
		super();
		this.comp_stock_no = comp_stock_no;
		this.branch_no = branch_no;
		this.comp_no = comp_no;
		this.comp_qty = comp_qty;
	}

	public int getComp_stock_no() {
		return comp_stock_no;
	}

	public void setComp_stock_no(int comp_stock_no) {
		this.comp_stock_no = comp_stock_no;
	}

	public int getBranch_no() {
		return branch_no;
	}

	public void setBranch_no(int branch_no) {
		this.branch_no = branch_no;
	}

	public int getComp_no() {
		return comp_no;
	}

	public void setComp_no(int comp_no) {
		this.comp_no = comp_no;
	}

	public int getComp_qty() {
		return comp_qty;
	}

	public void setComp_qty(int comp_qty) {
		this.comp_qty = comp_qty;
	}
	
	
	
}
