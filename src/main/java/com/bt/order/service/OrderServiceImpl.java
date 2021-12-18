package com.bt.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.mapper.EmployeeSQLMapper;
import com.bt.mapper.LogisticsSQLMapper;
import com.bt.mapper.ProcessListSQLMapper;
import com.bt.mapper.ProcessManagementSQLMapper;
import com.bt.mapper.ProcessStatusSQLMapper;
import com.bt.vo.BranchVo;
import com.bt.vo.DeliveryVo;
import com.bt.vo.EmployeeVo;
import com.bt.vo.ProcessListVo;
import com.bt.vo.ProcessManagementVo;
import com.bt.vo.ProcessStatusVo;
import com.bt.vo.ProductTypeVo;
import com.bt.vo.ProductVo;
import com.bt.vo.StoreOrderDetailVo;
import com.bt.vo.StoreOrderReservationVo;

@Service
public class OrderServiceImpl {

	@Autowired
	private LogisticsSQLMapper logisticsSQLMapper;

	@Autowired
	private EmployeeSQLMapper employeeSQLMapper;

	@Autowired
	private ProcessStatusSQLMapper processStatusSQLMapper;
	
	@Autowired
	private ProcessListSQLMapper processListSQLMapper;
	
	@Autowired
	private ProcessManagementSQLMapper processManagementSQLMapper;
	
	
	// 발주상세번호 조회
	public Map<String, Object> getDetailOrder(int store_order_detail_no) {

		Map<String, Object> detailOrderMap = new HashMap<String, Object>();

		StoreOrderDetailVo orderDetailVo = logisticsSQLMapper.selectByDetailNo(store_order_detail_no);

		ProductVo productVo = logisticsSQLMapper.selectByProductNo(orderDetailVo.getProduct_no());
		ProductTypeVo productTypeVo = logisticsSQLMapper.selectByProductTypeNo(productVo.getProduct_type_no());
		StoreOrderReservationVo orderResVo = logisticsSQLMapper.selectByResNo(orderDetailVo.getStore_order_res_code());
		EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCode(orderResVo.getEmp_code());
		BranchVo branchVo = employeeSQLMapper.selectByBranchNo(employeeVo.getBranch_no());

		// 프로세스 Max인 status
		ProcessStatusVo processStatusVo = logisticsSQLMapper.selectFlagNameByMaxNo(store_order_detail_no);

		if (processStatusVo != null) {

			System.out.println("맥스 상태 프로세스넘버" + processStatusVo.getProcess_status_no());
			detailOrderMap.put("processStatusVo", processStatusVo);

		}

		// 프로세스 이름 뽑아오기
		ProcessListVo processListVo = logisticsSQLMapper.selectStatusNameByMaxNo(store_order_detail_no);
		if (processListVo != null) {

			System.out.println("맥스 상태 프로세스이름" + processListVo.getProcess_name());
			detailOrderMap.put("processListVo", processListVo);
		}

		// management 등록 된 이후 값들
		ProcessManagementVo processManagementVo = processManagementSQLMapper.selectByDn(store_order_detail_no);
		if (processManagementVo != null) {

			// 1차검토한 사람 정보
			int firstCheckEmpCode = processManagementVo.getFirst_emp_code();
			System.out.println("1차검토 사원코드" + firstCheckEmpCode);
			EmployeeVo firstCheckEmployeeVo = employeeSQLMapper.selectByEmpCode(firstCheckEmpCode);
			int firstCheckBranchNo = firstCheckEmployeeVo.getBranch_no();
			BranchVo firstCheckBranchVo = employeeSQLMapper.selectByBranchNo(firstCheckBranchNo);

			detailOrderMap.put("firstCheckEmployeeVo", firstCheckEmployeeVo);
			detailOrderMap.put("firstCheckBranchVo", firstCheckBranchVo);

			// 출고요청한 사람 정보
			int lastCheckEmpCode = processManagementVo.getEnd_emp_code();
			if (lastCheckEmpCode != 0) {

				
				EmployeeVo lastCheckEmployeeVo = employeeSQLMapper.selectByEmpCode(lastCheckEmpCode);
				int lastCheckBranchNo = lastCheckEmployeeVo.getBranch_no();
				BranchVo lastCheckBranchVo = employeeSQLMapper.selectByBranchNo(lastCheckBranchNo);
				
				detailOrderMap.put("lastCheckEmployeeVo", lastCheckEmployeeVo);
				detailOrderMap.put("lastCheckBranchVo", lastCheckBranchVo);
			}

			int factory_branch_no = processManagementVo.getBranch_no();
			System.out.println("공장 branch_no" + factory_branch_no);
			if (factory_branch_no != 0) {

				BranchVo factoryBranchVo = employeeSQLMapper.selectByBranchNo(factory_branch_no);

				// 공장책임자 정보
				int factoryBranchEmpCode = factoryBranchVo.getEmp_code();
				EmployeeVo factoryEmployeeVo = employeeSQLMapper.selectByCode(factoryBranchEmpCode);

				detailOrderMap.put("factoryEmployeeVo", factoryEmployeeVo);
				detailOrderMap.put("factoryBranchVo", factoryBranchVo);

			}

			System.out.println("배달확인영 상세번호" + store_order_detail_no);

			DeliveryVo deliveryVo = logisticsSQLMapper.selectDeliveryByDetailNo(store_order_detail_no);
			System.out.println(deliveryVo);
			if (deliveryVo != null) {

				// 배달 등록한 사람정보
				int delivery_emp_code = deliveryVo.getEmp_code();
				System.out.println("배달등록자code" + delivery_emp_code);
				EmployeeVo deliveryEmployeeVo = employeeSQLMapper.selectByCode(delivery_emp_code);
				System.out.println("배달등록자" + deliveryEmployeeVo.getEmp_name());

				detailOrderMap.put("deliveryEmployeeVo", deliveryEmployeeVo);
				detailOrderMap.put("deliveryVo", deliveryVo);

			}

			detailOrderMap.put("processManagementVo", processManagementVo);

		}

		detailOrderMap.put("orderResEemployeeVo", employeeVo);
		detailOrderMap.put("storeOrderReservationVo", orderResVo);
		detailOrderMap.put("storeOrderDetailVo", orderDetailVo);
		detailOrderMap.put("productVo", productVo);
		detailOrderMap.put("productTypeVo", productTypeVo);
		detailOrderMap.put("branchVo", branchVo);

		return detailOrderMap;
	}

	public List<Map<String, Object>> getDetailOrderStatus(int store_order_detail_no) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<ProcessStatusVo> statusList = logisticsSQLMapper.selectProcessListByDetailNo(store_order_detail_no);

		for (ProcessStatusVo data : statusList) {

			System.out.println("리스트찾기:" + data);
			Map<String, Object> map = new HashMap<String, Object>();

			ProcessListVo processListVo = processListSQLMapper.selectByPrNo(data.getProcess_no());

			map.put("processStatusVo", data);
			map.put("processListVo", processListVo);

			list.add(map);
		}

		return list;

	}
	
	 // 팝업창
	   public List<Map<String,Object>> getProcessStaus(int store_order_detail_no) {
	      List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	      List<ProcessStatusVo> processList = processStatusSQLMapper.selectBySodn(store_order_detail_no);
	      
	      for(ProcessStatusVo processStatusVo : processList) {
	         ProcessListVo processListVo = processListSQLMapper.selectByPrNo(processStatusVo.getProcess_no());
	         
	         Map<String,Object> map = new HashMap<String,Object>();
	         
	         map.put("processStatusVo", processStatusVo);
	         map.put("processListVo", processListVo);
	         
	         list.add(map);
	      }
	      
	      return list;
	   }


}
