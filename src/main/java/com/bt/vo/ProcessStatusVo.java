package com.bt.vo;

import java.util.Date;

public class ProcessStatusVo {

	private int process_status_no;
	private int store_order_detail_no;
	private int process_no;
	private Date confirm_date;
	private char flag;
	
	public ProcessStatusVo() {
		// TODO Auto-generated constructor stub
	}

	public ProcessStatusVo(int process_status_no, int store_order_detail_no, int process_no, Date confirm_date,
			char flag) {
		super();
		this.process_status_no = process_status_no;
		this.store_order_detail_no = store_order_detail_no;
		this.process_no = process_no;
		this.confirm_date = confirm_date;
		this.flag = flag;
	}

	public int getProcess_status_no() {
		return process_status_no;
	}

	public void setProcess_status_no(int process_status_no) {
		this.process_status_no = process_status_no;
	}

	public int getStore_order_detail_no() {
		return store_order_detail_no;
	}

	public void setStore_order_detail_no(int store_order_detail_no) {
		this.store_order_detail_no = store_order_detail_no;
	}

	public int getProcess_no() {
		return process_no;
	}

	public void setProcess_no(int process_no) {
		this.process_no = process_no;
	}

	public Date getConfirm_date() {
		return confirm_date;
	}

	public void setConfirm_date(Date confirm_date) {
		this.confirm_date = confirm_date;
	}

	public char getFlag() {
		return flag;
	}

	public void setFlag(char flag) {
		this.flag = flag;
	}
	
	
	
}
