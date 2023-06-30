package com.example.Broadband.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Broadband.Model.Addons;
import com.example.Broadband.Model.BaseResponse;
import com.example.Broadband.Model.Customer;
import com.example.Broadband.Model.Plan;
import com.example.Broadband.services.BroadbandService;

@RequestMapping("/broadband")
@RestController
public class BroadbandController {
	@Autowired
	BroadbandService broadbandService;
	
	@PostMapping(value="/displaypackages")
	public List<Plan> displayPackages() {
		List<Plan> planList = broadbandService.displayPackages();
		return planList;
	}
	@PostMapping(value="/addpackage")
	public Plan addPackage(@RequestBody Plan planRequest) {
		return broadbandService.addPackage(planRequest);

	}
	
	@PostMapping(value="/insertaddons")
	public Addons insertAddons(@RequestBody Addons addonsRequest) {
		return broadbandService.insertAddons(addonsRequest);

	}
	@PostMapping(value="/getaddons")
	public Addons getAddons(@RequestBody Map<String, String> addOnName) {
		return broadbandService.getAddons(addOnName.get("packageName"));
		
	}
	@PostMapping(value="/updateaddons")
	public Addons updateAddons(@RequestBody Map<String, String> request) {
		Addons addonsResponse = new Addons();
		if(request.get("action").equalsIgnoreCase("ADD")) {
			 addonsResponse = broadbandService.addAddons(request.get("addonId"));
		} else if(request.get("action").equalsIgnoreCase("REMOVE")) {
			 addonsResponse = broadbandService.deleteAddons(request.get("addonId"));
		}
		
		return addonsResponse;
	}
	@PostMapping(value="/addcustomer")
	public boolean addCustomer(@RequestBody Customer customer) {
		if (customer.getCustomerName() == null ||customer.getAddress()==null||customer.getEmail()==null || customer.getPhoneNo()==null)
			return false;
		return broadbandService.addCustomer(customer);
	}
	@PostMapping(value="/bookappointment")
	public boolean bookAppointment(@RequestBody Map<String, String> appointmentRequest) {
		if(appointmentRequest.isEmpty()|| appointmentRequest.get("bookingDate")==null|| 
				appointmentRequest.get("bookingDate").isEmpty() || appointmentRequest.get("bookingTime")==null
				|| appointmentRequest.get("bookingTime").isEmpty())
			return false;
		return broadbandService.bookAppointment(appointmentRequest);
	}
	
	@PostMapping(value="/paybill")
	public BaseResponse payBill(@RequestBody Map<String, String> payBillRequest) {
		
		return broadbandService.payBill(payBillRequest);
	}

	@PostMapping(value="/verifypackage")
	public BaseResponse verifyPackage() {
		BaseResponse baseResponse = broadbandService.searchMasterByCustomerName();
		return baseResponse;
	}
	
	@PostMapping(value="/thankyou")
	public BaseResponse thankYou() {
		BaseResponse baseResponse = broadbandService.thankYou();
		return baseResponse;
	}
	
	
}
