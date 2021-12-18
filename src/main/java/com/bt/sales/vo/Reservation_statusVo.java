package com.bt.sales.vo;

public class Reservation_statusVo {
	private int res_status_no;
	private String res_step;
	public Reservation_statusVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Reservation_statusVo(int res_status_no, String res_step) {
		super();
		this.res_status_no = res_status_no;
		this.res_step = res_step;
	}
	public int getRes_status_no() {
		return res_status_no;
	}
	public void setRes_status_no(int res_status_no) {
		this.res_status_no = res_status_no;
	}
	public String getRes_step() {
		return res_step;
	}
	public void setRes_step(String res_step) {
		this.res_step = res_step;
	}
	
	
}
