package com.bt.vo;

public class EmployeeRankVo {

	private int rank_no;
	private String rank_name;
	
	public EmployeeRankVo() {
		// TODO Auto-generated constructor stub
	}

	public EmployeeRankVo(int rank_no, String rank_name) {
		super();
		this.rank_no = rank_no;
		this.rank_name = rank_name;
	}

	public int getRank_no() {
		return rank_no;
	}

	public void setRank_no(int rank_no) {
		this.rank_no = rank_no;
	}

	public String getRank_name() {
		return rank_name;
	}

	public void setRank_name(String rank_name) {
		this.rank_name = rank_name;
	}
	
	
	
}
