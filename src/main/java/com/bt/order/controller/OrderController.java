package com.bt.order.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bt.order.service.OrderServiceImpl;
import com.bt.production.service.ProductionServiceImpl;
import com.bt.production.vo.DeliveryVo;
import com.bt.vo.EmployeeVo;
import com.bt.vo.ProcessStatusVo;

@Controller
@RequestMapping("/order/*")
public class OrderController {

	@Autowired
	private OrderServiceImpl orderServiceImpl;
	@Autowired
	private ProductionServiceImpl productionService;

	public boolean deptcheck(int dept_no) {

		if (dept_no / 10 == 2) {
			return true;
		} else if (dept_no == 40) {
			return true;
		} else if (dept_no == 30) {
			return true;
		} else {
			return false;
		}

	}

	// 발주상세페이지
	@RequestMapping("/read_detail_order_page.do")
	public String readDetailOrderPage(int store_order_detail_no, Model model, HttpSession session,
			@RequestParam(value = "return_page", defaultValue = "0", required = false) int return_page) {
		
		System.out.println("발주상세페이지 store_detail_no" + store_order_detail_no);
		
		// 세션처리하기!(부서,로그아웃)
		Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");

		EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}
		if(return_page == 0 ) {
			return_page = 1;
		}
		
		Map<String, Object> detailMap = orderServiceImpl.getDetailOrder(store_order_detail_no);
		List<Map<String, Object>> statusList = orderServiceImpl.getDetailOrderStatus(store_order_detail_no);

		model.addAttribute("detailMap", detailMap);
		model.addAttribute("statusList", statusList);
		model.addAttribute("returnPage", return_page);

		if (employeeVo.getDept_no() == 40) {
			return "/production/detail_order_page";
		} else if (employeeVo.getDept_no() / 10 == 2) {
			return "/logistics/detail_order_page";
		} else if (employeeVo.getDept_no() == 30) {
			return "/sales/detail_order_page";
		}

		return null;

	}

	// 팝업창
	@RequestMapping("/order_popup.do")
	public String openPopUp(int store_order_detail_no, Model model) {

		List<Map<String, Object>> list = orderServiceImpl.getProcessStaus(store_order_detail_no);

		model.addAttribute("list", list);

		return "commons/order_popup";
	}

	// 발주 상세 페이지에서 출고요청승인버튼 눌렀을때
	@RequestMapping("/delivery_request_process.do")
	public String delivery_request_process(ProcessStatusVo processStatusVo) {

		// 승인버튼 누르면 insert됨
		productionService.changeState(processStatusVo);

		return "redirect:/order/read_detail_order_page.do?store_order_detail_no="+processStatusVo.getStore_order_detail_no();
	}

	// 발주 상세 페이지에서 출고요청거절버튼 눌렀을때
	@RequestMapping("/delivery_reject_process.do")
	public String delivery_reject_process(ProcessStatusVo processStatusVo) {

		// 거절버튼 누르면 요청거절됨
		productionService.rejectState(processStatusVo);

		return "redirect:/order/read_detail_order_page.do?store_order_detail_no="+processStatusVo.getStore_order_detail_no();
	}

	// 배송 등록 페이지
	@RequestMapping("/delivery_enroll_page.do")
	public String delivery_enroll_page(int store_order_detail_no, Model model, DeliveryVo deliveryVo,
			HttpSession session) {

		Map<String, Object> map = productionService.getEnroll(store_order_detail_no);
		model.addAttribute("map", map);

		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");

		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		return "production/delivery_enroll_page";
	}

}
