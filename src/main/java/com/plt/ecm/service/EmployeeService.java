package com.plt.ecm.service;

import com.plt.ecm.entities.Employee;

public interface EmployeeService extends AbstractService<Employee, Integer> {

	public Employee findLastPk();

}
