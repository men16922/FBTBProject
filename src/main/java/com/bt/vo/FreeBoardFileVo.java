package com.bt.vo;

import java.util.Date;

public class FreeBoardFileVo {
	private int freeboard_file_no;
	private int freeboard_no;
	private String freeboard_file_link_path;
	private String freeboard_file_real_path;
	private Date freeboard_file_upload_date;
	public FreeBoardFileVo() {
		super();
	}
	public FreeBoardFileVo(int freeboard_file_no, int freeboard_no, String freeboard_file_link_path,
			String freeboard_file_real_path, Date freeboard_file_upload_date) {
		super();
		this.freeboard_file_no = freeboard_file_no;
		this.freeboard_no = freeboard_no;
		this.freeboard_file_link_path = freeboard_file_link_path;
		this.freeboard_file_real_path = freeboard_file_real_path;
		this.freeboard_file_upload_date = freeboard_file_upload_date;
	}
	public int getFreeboard_file_no() {
		return freeboard_file_no;
	}
	public void setFreeboard_file_no(int freeboard_file_no) {
		this.freeboard_file_no = freeboard_file_no;
	}
	public int getFreeboard_no() {
		return freeboard_no;
	}
	public void setFreeboard_no(int freeboard_no) {
		this.freeboard_no = freeboard_no;
	}
	public String getFreeboard_file_link_path() {
		return freeboard_file_link_path;
	}
	public void setFreeboard_file_link_path(String freeboard_file_link_path) {
		this.freeboard_file_link_path = freeboard_file_link_path;
	}
	public String getFreeboard_file_real_path() {
		return freeboard_file_real_path;
	}
	public void setFreeboard_file_real_path(String freeboard_file_real_path) {
		this.freeboard_file_real_path = freeboard_file_real_path;
	}
	public Date getFreeboard_file_upload_date() {
		return freeboard_file_upload_date;
	}
	public void setFreeboard_file_upload_date(Date freeboard_file_upload_date) {
		this.freeboard_file_upload_date = freeboard_file_upload_date;
	}
	
	
}
