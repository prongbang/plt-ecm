package com.plt.ecm.dao;
 

import com.plt.ecm.entities.Employee;

public interface EmployeeDao extends AbstractDao<Employee, Integer>{ 
	
	public Employee findLastPk();
	
}
