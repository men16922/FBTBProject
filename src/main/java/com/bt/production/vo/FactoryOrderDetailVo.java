package com.bt.production.vo;

public class FactoryOrderDetailVo {

	private int factory_order_detail_no;
	private int factory_order_code;
	private int comp_no;
	private int supplier_no;
	private int factory_order_qty;
	private String factory_order_note;
	private String check_approval;
	public FactoryOrderDetailVo() {
		super();
	}
	public FactoryOrderDetailVo(int factory_order_detail_no, int factory_order_code, int comp_no, int supplier_no,
			int factory_order_qty, String factory_order_note, String check_approval) {
		super();
		this.factory_order_detail_no = factory_order_detail_no;
		this.factory_order_code = factory_order_code;
		this.comp_no = comp_no;
		this.supplier_no = supplier_no;
		this.factory_order_qty = factory_order_qty;
		this.factory_order_note = factory_order_note;
		this.check_approval = check_approval;
	}
	public int getFactory_order_detail_no() {
		return factory_order_detail_no;
	}
	public void setFactory_order_detail_no(int factory_order_detail_no) {
		this.factory_order_detail_no = factory_order_detail_no;
	}
	public int getFactory_order_code() {
		return factory_order_code;
	}
	public void setFactory_order_code(int factory_order_code) {
		this.factory_order_code = factory_order_code;
	}
	public int getComp_no() {
		return comp_no;
	}
	public void setComp_no(int comp_no) {
		this.comp_no = comp_no;
	}
	public int getSupplier_no() {
		return supplier_no;
	}
	public void setSupplier_no(int supplier_no) {
		this.supplier_no = supplier_no;
	}
	public int getFactory_order_qty() {
		return factory_order_qty;
	}
	public void setFactory_order_qty(int factory_order_qty) {
		this.factory_order_qty = factory_order_qty;
	}
	public String getFactory_order_note() {
		return factory_order_note;
	}
	public void setFactory_order_note(String factory_order_note) {
		this.factory_order_note = factory_order_note;
	}
	public String getCheck_approval() {
		return check_approval;
	}
	public void setCheck_approval(String check_approval) {
		this.check_approval = check_approval;
	}
	
	
}
