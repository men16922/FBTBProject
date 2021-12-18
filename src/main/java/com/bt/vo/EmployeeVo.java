package com.bt.vo;

import java.util.Date;

public class EmployeeVo {
	
	private int emp_code;
	private int emp_no;
	private String emp_name;
	private int dept_no;
	private int rank_no;
	private Date start_date;
	private char check_resignation;
	private String emp_phone;
	private String emp_address;
	private char emp_sex;
	private String emp_resident_num;
	private String emp_password;
	private String emp_email;
	private int branch_no;
	
		
	
	public EmployeeVo() {
		// TODO Auto-generated constructor stub
	}



	public EmployeeVo(int emp_code, int emp_no, String emp_name, int dept_no, int rank_no, Date start_date,
			char check_resignation, String emp_phone, String emp_address, char emp_sex, String emp_resident_num,
			String emp_password, String emp_email, int branch_no) {
		super();
		this.emp_code = emp_code;
		this.emp_no = emp_no;
		this.emp_name = emp_name;
		this.dept_no = dept_no;
		this.rank_no = rank_no;
		this.start_date = start_date;
		this.check_resignation = check_resignation;
		this.emp_phone = emp_phone;
		this.emp_address = emp_address;
		this.emp_sex = emp_sex;
		this.emp_resident_num = emp_resident_num;
		this.emp_password = emp_password;
		this.emp_email = emp_email;
		this.branch_no = branch_no;
	}



	public int getEmp_code() {
		return emp_code;
	}



	public void setEmp_code(int emp_code) {
		this.emp_code = emp_code;
	}



	public int getEmp_no() {
		return emp_no;
	}



	public void setEmp_no(int emp_no) {
		this.emp_no = emp_no;
	}



	public String getEmp_name() {
		return emp_name;
	}



	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}



	public int getDept_no() {
		return dept_no;
	}



	public void setDept_no(int dept_no) {
		this.dept_no = dept_no;
	}



	public int getRank_no() {
		return rank_no;
	}



	public void setRank_no(int rank_no) {
		this.rank_no = rank_no;
	}



	public Date getStart_date() {
		return start_date;
	}



	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}



	public char getCheck_resignation() {
		return check_resignation;
	}



	public void setCheck_resignation(char check_resignation) {
		this.check_resignation = check_resignation;
	}



	public String getEmp_phone() {
		return emp_phone;
	}



	public void setEmp_phone(String emp_phone) {
		this.emp_phone = emp_phone;
	}



	public String getEmp_address() {
		return emp_address;
	}



	public void setEmp_address(String emp_address) {
		this.emp_address = emp_address;
	}



	public char getEmp_sex() {
		return emp_sex;
	}



	public void setEmp_sex(char emp_sex) {
		this.emp_sex = emp_sex;
	}



	public String getEmp_resident_num() {
		return emp_resident_num;
	}



	public void setEmp_resident_num(String emp_resident_num) {
		this.emp_resident_num = emp_resident_num;
	}



	public String getEmp_password() {
		return emp_password;
	}



	public void setEmp_password(String emp_password) {
		this.emp_password = emp_password;
	}



	public String getEmp_email() {
		return emp_email;
	}



	public void setEmp_email(String emp_email) {
		this.emp_email = emp_email;
	}



	public int getBranch_no() {
		return branch_no;
	}



	public void setBranch_no(int branch_no) {
		this.branch_no = branch_no;
	}

	



}
