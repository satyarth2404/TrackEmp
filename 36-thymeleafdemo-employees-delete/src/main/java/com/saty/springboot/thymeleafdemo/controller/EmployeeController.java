package com.saty.springboot.thymeleafdemo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.saty.springboot.thymeleafdemo.entity.Employee;
import com.saty.springboot.thymeleafdemo.service.EmployeeService;

@Controller
@RequestMapping("employees")
public class EmployeeController {
	
	private EmployeeService employeeService;
	
	@Autowired
	public EmployeeController(EmployeeService theEmployeeService) {
		employeeService = theEmployeeService;
	}
	
	
	//add mapping for "/list"
	
	@GetMapping("/list")
	public String listEmployees(Model model) {
		
		List<Employee> theEmployees = employeeService.findAll();
		
		//add to the spring model
		model.addAttribute("employees",theEmployees);
		
		return "employees/list-employees";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		//create a model attribute to bind form data
		
		Employee theEmployee = new Employee();
		
		theModel.addAttribute("employee",theEmployee);
		
		return "employees/employee-form";
	}

	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {
		
		//save the employee
		employeeService.save(theEmployee);
		
		//use the redirect to prevent duplicate submissions
		return "redirect:/employees/list";
		
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId, Model model) {
		
		//get the employee from the service
		Employee theEmployee = employeeService.findById(theId);
		
		//set employee as a model attribute to pre-populate the form
		model.addAttribute("employee",theEmployee);
		
		//send over to our form
		return "employees/employee-form";
		
	}
	
	@GetMapping("/delete")
	public String deleteEmployee(@RequestParam("employeeId") int theId) {

		//delete the employee
		employeeService.deleteById(theId);
		
		//redirect to /employees/list
		return "redirect:/employees/list";
	}
}
