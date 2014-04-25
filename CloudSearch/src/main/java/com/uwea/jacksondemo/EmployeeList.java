package com.uwea.jacksondemo;

import java.util.List;

public class EmployeeList {

	public List<Employee> employeeList;

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	@Override
	public String toString() {
		return "EmployeeList [employeeList=" + employeeList.size() + "]";
	}
	
}
