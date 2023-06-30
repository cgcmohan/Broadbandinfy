package com.example.Broadband.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.Broadband.Model.BaseResponse;

public interface BaseResponseRepo extends CrudRepository<BaseResponse, String>{

	//@Modifying
	//@Transactional
	@Query(value = "Select * from master where customer_name= :customerName", nativeQuery = true)
	public List<BaseResponse> searchMasterByCustomerName(@Param("customerName") String customerName);
}
