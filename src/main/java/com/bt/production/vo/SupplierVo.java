package com.bt.production.vo;

public class SupplierVo {

	private int supplier_no;
	private String supplier_name;
	private String supplier_address;
	private String supplier_phone;
	private int comp_no;
	
	public SupplierVo() {
		super();
	}

	public SupplierVo(int supplier_no, String supplier_name, String supplier_address, String supplier_phone,
			int comp_no) {
		super();
		this.supplier_no = supplier_no;
		this.supplier_name = supplier_name;
		this.supplier_address = supplier_address;
		this.supplier_phone = supplier_phone;
		this.comp_no = comp_no;
	}

	public int getSupplier_no() {
		return supplier_no;
	}

	public void setSupplier_no(int supplier_no) {
		this.supplier_no = supplier_no;
	}

	public String getSupplier_name() {
		return supplier_name;
	}

	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

	public String getSupplier_address() {
		return supplier_address;
	}

	public void setSupplier_address(String supplier_address) {
		this.supplier_address = supplier_address;
	}

	public String getSupplier_phone() {
		return supplier_phone;
	}

	public void setSupplier_phone(String supplier_phone) {
		this.supplier_phone = supplier_phone;
	}

	public int getComp_no() {
		return comp_no;
	}

	public void setComp_no(int comp_no) {
		this.comp_no = comp_no;
	}
	
	
}
