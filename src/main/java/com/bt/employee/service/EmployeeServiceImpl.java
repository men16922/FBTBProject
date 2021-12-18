package com.bt.employee.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.mapper.EmployeeSQLMapper;
import com.bt.mapper.FactoryOrderSQLMapper;
import com.bt.mapper.HomepageNoticeSQLMapper;
import com.bt.mapper.StoreOrderReservationSQLMapper;
import com.bt.production.vo.FactoryOrderVo;
import com.bt.vo.BranchVo;
import com.bt.vo.DeptTypeVo;
import com.bt.vo.DeptVo;
import com.bt.vo.EmployeeRankVo;
import com.bt.vo.EmployeeVo;
import com.bt.vo.HomepageNoticeVo;
import com.bt.vo.StoreOrderReservationVo;

@Service
public class EmployeeServiceImpl {
	@Autowired
	private EmployeeSQLMapper employeeSQLMapper;
	@Autowired
	private StoreOrderReservationSQLMapper storeOrderReservationSQLMapper;
	@Autowired
	private FactoryOrderSQLMapper factoryOrderSQLMapper;
	@Autowired
	private HomepageNoticeSQLMapper homepageNoticeSQLMapper;

	public Map<String, Object> getIdPw(EmployeeVo employeevo) {

		Map<String, Object> map = new HashMap<String, Object>();

		EmployeeVo employeeVo = employeeSQLMapper.selectByIdPwForConfirm(employeevo.getEmp_no(), employeevo.getEmp_password());
		DeptVo deptVo = employeeSQLMapper.selectByDeptNo(employeeVo.getDept_no());
		DeptTypeVo deptTypeVo = employeeSQLMapper.selectByDeptTypeNo(deptVo.getDept_type_no());

		System.out.println("직급넘버 나오는지 테스트: " + employeeVo.getRank_no());

		EmployeeRankVo employeeRankVo = employeeSQLMapper.selectByEmpRankNo(employeeVo.getRank_no());
		BranchVo branchVo = employeeSQLMapper.selectByBranchNo(employeeVo.getBranch_no());

		map.put("employeeVo", employeeVo);
		map.put("deptVo", deptVo);
		map.put("deptTypeVo", deptTypeVo);
		map.put("employeeRankVo", employeeRankVo);
		map.put("branchVo", branchVo);

		return map;

	}

	public EmployeeVo getIdPwConfirm(int emp_no, String emp_password) {

		/**
		 * String hashCode = FBMessageDigest.digest(emp_password);
		 * 
		 * 
		 * 
		 * //==================비밀번호 해싱================== try {
		 * 
		 * 
		 * 
		 * StringBuilder sb = new StringBuilder();
		 * 
		 * MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
		 * messageDigest.reset(); messageDigest.update(emp_password.getBytes());
		 * 
		 * byte[] chars = messageDigest.digest();
		 * 
		 * for(int i=0; i<chars.length; i++) {
		 * 
		 * String tmp = Integer.toHexString(chars[i]&0xff); //비트연산자 사용
		 * 
		 * if(tmp.length()==1) { sb.append("0"); } //항상 똑같은 길이의 문자를 만들기 위해
		 * 
		 * sb.append(tmp); }
		 * 
		 * hashCode = sb.toString();
		 * 
		 * //memberVo.setMember_pw(hashCode);
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 * 
		 * emp_password = hashCode;
		 * 
		 * 
		 **/
		System.out.println(emp_no + emp_password);
		EmployeeVo employeeVo = employeeSQLMapper.selectByIdPwForConfirm(emp_no, emp_password);
		return employeeVo;

	}

	public EmployeeVo Findpassword(String emp_name, String emp_no, String emp_email) {

		EmployeeVo employeeVo = employeeSQLMapper.Findpassword(emp_name, emp_no, emp_email);
		return employeeVo;

	}

	public EmployeeVo getEmpCodeAndPassword(int emp_code, String emp_password) {

		EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCodeAndPw(emp_code, emp_password);

		return employeeVo;
	}

	public void updateEmpPassword(int emp_code, String new_emp_password) {

		employeeSQLMapper.updatePasswordByEmpCode(emp_code, new_emp_password);
	}

	public void addEmployee(String emp_name, String emp_email, String emp_phone, String start_date, String emp_address,
			String emp_sex, int dept_no, int branch_no, int rank_no, String emp_residentnum, int emp_no) {
		employeeSQLMapper.insertEmployee(emp_name, emp_email, emp_phone, start_date, emp_address, emp_sex, dept_no,
				branch_no, rank_no, emp_residentnum, emp_no);
	}

	public int selectMaxEmpNoforCreateNewEmpNo() {
		// TODO Auto-generated method stub
		return employeeSQLMapper.selectMaxEmpNoforCreateNewEmpNo();
	}

	public void resignationEmployee(int emp_code, String emp_name, int emp_no) {
		// TODO Auto-generated method stub
		employeeSQLMapper.resignationEmployee(emp_code, emp_name, emp_no);
	}

	public EmployeeVo selectByEmpCode(int emp_code) {
		return employeeSQLMapper.selectByEmpCode(emp_code);
		// TODO Auto-generated method stub

	}

	public EmployeeVo selectByEmpNo_for_confirm(int emp_no) {
		// TODO Auto-generated method stub
		return employeeSQLMapper.selectByEmpNo_for_confirm(emp_no);
	}

	public DeptVo selectDeptName(int emp_no) {
		// TODO Auto-generated method stub
		return employeeSQLMapper.selectDeptName(emp_no);
	}

	public EmployeeRankVo selectRankByEmpNo(int emp_no) {
		// TODO Auto-generated method stub
		return employeeSQLMapper.selectRankByEmpNo(emp_no);
	}

	public BranchVo selectBranchByEmpNo(int emp_no) {
		// TODO Auto-generated method stub
		return employeeSQLMapper.selectBranchByEmpNo(emp_no);
	}

	public void modifyEmployee(int emp_no, String emp_name, int dept_no, int branch_no, int rank_no, String emp_email,
			String emp_phone, String emp_address) {
		employeeSQLMapper.modifyEmployee(emp_no, emp_name, dept_no, branch_no, rank_no, emp_email, emp_phone,
				emp_address);

	}

	public List<Map<String, Object>> selectEmployeeByBranchNo_forStore() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<EmployeeVo> boardList = employeeSQLMapper.selectEmployeeByBranchNo_forStore();
		for (EmployeeVo employeeVo : boardList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("employeeVo", employeeVo);
			list.add(map);
		}
		return list;
	}

	public List<Map<String, Object>> selectEmployeeByBranchNo_forFactory() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<EmployeeVo> boardList = employeeSQLMapper.selectEmployeeByBranchNo_forFactory();
		for (EmployeeVo employeeVo : boardList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("employeeVo", employeeVo);
			list.add(map);
		}
		return list;
	}

	public EmployeeVo selectEmployeeByBranch_no(int branch_no) {
		// TODO Auto-generated method stub
		return employeeSQLMapper.selectEmployeeByBranch_no(branch_no);
	}

	public List<Map<String, Object>> selectEmployeeByBranchNo_forStore_AllList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<EmployeeVo> boardList = employeeSQLMapper.selectEmployeeByBranchNo_forStore_AllList();
		for (EmployeeVo employeeVo : boardList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("employeeVo", employeeVo);
			list.add(map);
		}
		return list;
	}

	public List<Map<String, Object>> getReservationInfo(int emp_code, int curr_page) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<StoreOrderReservationVo> boardList = storeOrderReservationSQLMapper.getStoreOrderReservationInfo(emp_code,
				curr_page);
		for (StoreOrderReservationVo storeOrderReservationVo : boardList) {
			Map<String, Object> map = new HashMap<String, Object>();
			EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCodeForStoreReservationPage(emp_code);

			map.put("employeeVo", employeeVo);
			map.put("StoreOrderReservationVo", storeOrderReservationVo);

			list.add(map);
		}

		return list;
	}

	public int getReservationInfo_paging(int emp_code) {
		return employeeSQLMapper.getReservationInfo_paging(emp_code);
	}

	public List<Map<String, Object>> getFactoryOrderInfo(int emp_code, int curr_page) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<FactoryOrderVo> boardList = factoryOrderSQLMapper.getFactoryOrderInfo(emp_code, curr_page);
		for (FactoryOrderVo factoryOrderVo : boardList) {
			Map<String, Object> map = new HashMap<String, Object>();
			EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCodeForFactoryOrderPage(emp_code);

			map.put("employeeVo", employeeVo);
			map.put("FactoryOrderVo", factoryOrderVo);

			list.add(map);
		}
		return list;
	}

	public int getFactoryOrderInfo_paging(int emp_code) {
		// TODO Auto-generated method stub
		return employeeSQLMapper.getFactoryOrderInfo_paging(emp_code);

	}

	public List<Map<String, Object>> selectEmployeeByBranchNo_forFactory_AllList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<EmployeeVo> boardList = employeeSQLMapper.selectEmployeeByBranchNo_forFactory_AllList();
		for (EmployeeVo employeeVo : boardList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("employeeVo", employeeVo);
			list.add(map);
		}
		return list;
	}

	public int selectCountByBranchNo(int branch_no) {
		// TODO Auto-generated method stub
		return employeeSQLMapper.selectCountByBranchNo(branch_no);
	}

	public List<Map<String, Object>> selectEmployeeByBranchNo_forStore(int branch_no) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<EmployeeVo> boardList = employeeSQLMapper.selectEmployeeByBranchNo_forStore(branch_no);
		for (EmployeeVo employeeVo : boardList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("employeeVo", employeeVo);
			list.add(map);
		}
		return list;
	}

	public List<Map<String, Object>> selectEmployeeByBranchNo_forFactory(int branch_no) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<EmployeeVo> boardList = employeeSQLMapper.selectEmployeeByBranchNo_forFactory(branch_no);
		for (EmployeeVo employeeVo : boardList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("employeeVo", employeeVo);
			list.add(map);
		}
		return list;
	}

	public String getempcodebyempno(String emp_no) {

		return employeeSQLMapper.getempcodebyempno(emp_no);
	}

	public Integer getCountByMonth(int mon) {
		// TODO Auto-generated method stub
		return employeeSQLMapper.getCountByMonth(mon);
	}

	public List<Map<String, Object>> getHomepageNotice(int hp_notice_type_no) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<HomepageNoticeVo> boardList = homepageNoticeSQLMapper.getHomepageNotice(hp_notice_type_no);
		for (HomepageNoticeVo homepageNoticeVo : boardList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("homepageNoticeVo", homepageNoticeVo);
			list.add(map);
		}
		return list;
	}

	public void homepageNotice_modify_process(int hp_notice_type_no, String hp_notice_title, String hp_notice_content) {
		homepageNoticeSQLMapper.homepageNotice_modify_process(hp_notice_type_no, hp_notice_title, hp_notice_content);
	}
	
}