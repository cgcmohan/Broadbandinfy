package com.example.Broadband.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Broadband.Model.Customer;
@Repository
public interface CustomerRepo extends CrudRepository<Customer, Long>{

	//@Modifying
	//@Transactional
	@Query(value = "select * from customer where customer_name= :customerName", nativeQuery = true)
	public List<Customer> findByCustomerName(@Param("customerName") String customerName);
}
