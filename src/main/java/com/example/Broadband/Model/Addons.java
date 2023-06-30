package com.example.Broadband.Model;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@JsonInclude(value = Include.NON_NULL)
@Table(name = "addons")
public class Addons {
	@NonNull
	@Id
	private String addonId;
	@NonNull
	private String packageName;
	@NonNull
	private String speed;
	@NonNull
	private String monthlyCharge;
	@NonNull
	@Column(name = "addon_name")
	private String addonName;
	public String getAddonId() {
		return addonId;
	}
	public void setAddonId(String addonId) {
		this.addonId = addonId;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getMonthlyCharge() {
		return monthlyCharge;
	}
	public void setMonthlyCharge(String monthlyCharge) {
		this.monthlyCharge = monthlyCharge;
	}
	public String getAddonName() {
		return addonName;
	}
	public void setAddonName(String addonName) {
		this.addonName = addonName;
	}
	
}
