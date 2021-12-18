package com.bt.message.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bt.message.service.MessageServiceImpl;
import com.bt.production.service.ProductionServiceImpl;
import com.bt.sales.service.SalesServiceImpl;
import com.bt.vo.EmployeeVo;

@Controller
@ResponseBody
@RequestMapping("/message/*")
public class RESTMessageController {


   @Autowired
   private MessageServiceImpl messageServiceImpl;
   
   @Autowired
   private SalesServiceImpl salesService;
   @Autowired
   private ProductionServiceImpl productionService;
   
   
   @RequestMapping("countMessageAlert.do")
   public int countUnreadMessage(HttpSession session) {
      
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");
      int receiver = employeeVo.getEmp_code();
      
      int count = messageServiceImpl.getCountMessageCheckN(receiver);
      
      return count;
   }
   
   @RequestMapping("countMessageAlert2.do")
   public int countUnreadMessage2(HttpSession session) {
      
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      int count = salesService.countreservation();

      return count;
   }
   
   @RequestMapping("countMessageAlert3.do")
   public int countUnreadMessage3(HttpSession session) {
      
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");
 
      int count = salesService.storeincount(Integer.toString(employeeVo.getBranch_no()));

      return count;
   }
   
   // 생산부 미입고 부품 발주건 메세지 갯수
   @RequestMapping("productionMessageAlert.do")
   public int CountProductionMessage(HttpSession session) {
	   Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
	   EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");
	 
	   int count = productionService.countDoList(employeeVo.getBranch_no());
	   
	   return count;
   }
   
   // 생산부 제품 발주건 메세지 갯수
   @RequestMapping("productionOrderMessageAlert.do")
   public int CountProductionOrderMessage(HttpSession session) {
	   Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
	   EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");
	 
	   int count = productionService.CountProductList(employeeVo.getBranch_no());
	   
	   return count;
   }
}