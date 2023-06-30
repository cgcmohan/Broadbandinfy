package com.example.Broadband.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Broadband.Model.Addons;
@Repository
public interface AddonsRepo extends JpaRepository<Addons, String>{

	
	//@Transactional
	@Query(value = "select * from addons where addon_name= :addonName", nativeQuery = true)
	public List<Addons> findAddOnByName(@Param("addonName") String addonName);
	
}
