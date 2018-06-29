package com.plt.ecm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plt.ecm.dao.EmployeeDao;
import com.plt.ecm.entities.Employee;
import com.plt.ecm.service.EmployeeService; 

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService { 
	
	@Autowired
	private EmployeeDao employeeDao;

	public Employee findByPK(Integer pk) throws Exception {
		return employeeDao.findByPK(pk);
	}

	public int save(Employee entity) throws Exception {
		return employeeDao.save(entity);
	}

	public int update(Employee entity) throws Exception {
		return employeeDao.update(entity);
	}

	public int delete(Employee entity) throws Exception {
		return employeeDao.delete(entity);
	}

	public List<Employee> findAll() throws Exception {
		return employeeDao.findAll();
	}

	public Employee findLastPk() {
		return employeeDao.findLastPk();
	}

}
