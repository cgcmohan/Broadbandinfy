package com.example.Broadband.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.Broadband.Model.Plan;
@Repository
public interface PlanRepo extends CrudRepository<Plan, Long>{

}
