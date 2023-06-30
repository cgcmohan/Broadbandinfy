package com.example.Broadband.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Broadband.Model.Addons;
import com.example.Broadband.Model.BaseResponse;
import com.example.Broadband.Model.Customer;
import com.example.Broadband.Model.Plan;
import com.example.Broadband.Repo.AddonsRepo;
import com.example.Broadband.Repo.BaseResponseRepo;
import com.example.Broadband.Repo.CustomerRepo;
import com.example.Broadband.Repo.PlanRepo;

@Service
public class BroadbandService {
	@Autowired
	PlanRepo planRepo;
	@Autowired
	CustomerRepo customerRepo;
	@Autowired
	AddonsRepo addonsRepo;
	@Autowired
	BaseResponseRepo baseResponseRepo;

	public List<Plan> displayPackages() {
		List<Plan> planList = new ArrayList<>();
		planRepo.findAll().forEach(action -> planList.add(action));
		return planList;
	}

	public Plan addPackage(Plan planRequest) {
		Plan plan = planRepo.save(planRequest);
		return plan;
	}

	public Addons getAddons(String addOnName) {
		List<Addons> addonsList = addonsRepo.findAddOnByName(addOnName);
		// Addons addonTest = addonsRepo.findById("201").get();
		Addons addons = new Addons();
		if (addonsList.size() > 0) {
			addons = addonsList.get(0);
		}
		return addons;
	}

	public boolean addCustomer(Customer customer) {
		
		Customer cust = customerRepo.save(customer);
		return true;
	}

	public Addons addAddons(String addonId) {

		return addonsRepo.findById(addonId).get();
	}

	public Addons deleteAddons(String addonId) {
		 
		Addons ad = new Addons();
		Addons addonData = addonsRepo.findById(addonId).get();
		ad.setPackageName(addonData.getPackageName());
		/**
		 * addonsRepo.deleteById(addonId); comment this line, If you want to remove entry from database
		 */
		//addonsRepo.deleteById(addonId);
		return ad;
	}

	public boolean bookAppointment(Map<String, String> appointmentRequest) {
		if (appointmentRequest.get("bookingId") != null) {
			return true;
		}
		BaseResponse baseResponse = new BaseResponse();
		Plan planData = planRepo.findById(Long.valueOf(appointmentRequest.get("bookingId"))).get();
		baseResponse.setPackageName(planData.getPackageName());
		baseResponse.setPrice(planData.getPrice());
		baseResponse.setSpeed(planData.getSpeed());
		Customer customerData = new Customer();
		List<Customer> cd = customerRepo.findByCustomerName(appointmentRequest.get("customerName"));
		if (cd.size() > 0) {
			customerData = cd.get(0);
		}
		baseResponse.setCustomerName(customerData.getCustomerName());
		baseResponse.setPhoneNo(customerData.getPhoneNo());
		baseResponse.setBookingDate(appointmentRequest.get("bookingDate"));
		baseResponse.setBookingTime(appointmentRequest.get("bookingtTime"));
		baseResponseRepo.save(baseResponse);
		return true;
	}

	public BaseResponse payBill(Map<String, String> payBillRequest) {
		BaseResponse baseResponse = new BaseResponse();
		Customer customerData = new Customer();
		List<Customer> cd = customerRepo.findByCustomerName(payBillRequest.get("customerName"));

		if (cd.size() > 0) {
			customerData = cd.get(0);
		}
		baseResponse.setCustomerName(customerData.getCustomerName());
		baseResponse.setPhoneNo(customerData.getPhoneNo());
		baseResponse.setAddress(customerData.getAddress());
		baseResponse.setEmail(customerData.getEmail());
		baseResponse.setPaymentStatus("PAYMENT RECEIVED");
		UUID uuid = UUID.randomUUID();
		String paymentId = uuid.toString();
		baseResponse.setPaymentId(paymentId);
		Plan plan = planRepo.findById(Long.valueOf(payBillRequest.get("pin"))).get();
		baseResponse.setPackageName(plan.getPackageName());
		baseResponse.setSpeed(plan.getSpeed());
		baseResponse.setPrice(plan.getPrice());
		baseResponseRepo.save(baseResponse);
		return baseResponseRepo.findById(payBillRequest.get("customerName")).get();
	}

	public void newAddon(Addons addonsData) {
		// TODO Auto-generated method stub
		addonsRepo.save(addonsData);
	}

	public BaseResponse searchMasterByCustomerName() {
		// TODO Auto-generated method stub
		List<BaseResponse> li = new ArrayList<>();
		baseResponseRepo.findAll().forEach(action -> li.add(action));
		List<BaseResponse> br = baseResponseRepo.searchMasterByCustomerName(li.get(0).getCustomerName());
		BaseResponse baseResponse = new BaseResponse();
		if (br.size() > 0) {
			baseResponse = br.get(0);
		}
		BaseResponse verifyPackage = new BaseResponse();
		verifyPackage.setCustomerName(baseResponse.getCustomerName());
		verifyPackage.setEmail(baseResponse.getEmail());
		verifyPackage.setPhoneNo(baseResponse.getEmail());
		verifyPackage.setAddress(baseResponse.getAddress());
		verifyPackage.setPackageName(baseResponse.getPackageName());
		verifyPackage.setPrice(baseResponse.getPrice());
		verifyPackage.setSpeed(baseResponse.getSpeed());
		return verifyPackage;
	}

	public BaseResponse thankYou() {
		List<BaseResponse> li = new ArrayList<>();
		baseResponseRepo.findAll().forEach(action -> li.add(action));
		;
		List<BaseResponse> br = baseResponseRepo.searchMasterByCustomerName(li.get(0).getCustomerName());
		BaseResponse baseResponse = new BaseResponse();
		if (br.size() > 0) {
			baseResponse = br.get(0);
		}
		BaseResponse thankYou = new BaseResponse();
		thankYou.setCustomerName(baseResponse.getCustomerName());
		thankYou.setEmail(baseResponse.getEmail());
		thankYou.setPhoneNo(baseResponse.getEmail());
		thankYou.setAddress(baseResponse.getAddress());
		thankYou.setPackageName(baseResponse.getPackageName());
		thankYou.setPrice(baseResponse.getPrice());
		thankYou.setSpeed(baseResponse.getSpeed());
		return thankYou;
	}

	public Addons insertAddons(Addons addonsRequest) {
		Addons addonsData = addonsRepo.save(addonsRequest);
		return addonsData;
	}
}
