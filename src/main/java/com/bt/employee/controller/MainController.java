package com.bt.employee.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	
	@Autowired
	private EmployeeController employeeController;
	
	@RequestMapping("/")
	public String home(HttpSession session) {
		
		return employeeController.loginPage(session);
	}
}
