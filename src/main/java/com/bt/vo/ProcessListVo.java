package com.bt.vo;

public class ProcessListVo {
	
	 private int process_no;
	 private String process_name;
	
	 
	 public ProcessListVo() {
		// TODO Auto-generated constructor stub
	}


	public ProcessListVo(int process_no, String process_name) {
		super();
		this.process_no = process_no;
		this.process_name = process_name;
	}


	public int getProcess_no() {
		return process_no;
	}


	public void setProcess_no(int process_no) {
		this.process_no = process_no;
	}


	public String getProcess_name() {
		return process_name;
	}


	public void setProcess_name(String process_name) {
		this.process_name = process_name;
	}
	 
	 
	 
	 
}
