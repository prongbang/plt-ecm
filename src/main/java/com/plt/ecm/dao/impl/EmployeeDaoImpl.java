package com.plt.ecm.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate; 
import org.springframework.stereotype.Repository;

import com.plt.ecm.dao.AbstractDao;
import com.plt.ecm.dao.EmployeeDao;
import com.plt.ecm.entities.Employee;

@Repository
public class EmployeeDaoImpl implements EmployeeDao, AbstractDao<Employee, Integer> {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("rawtypes")
	public List<Employee> findAll() {

		String sql = "SELECT * FROM EMPLOYEE";

		List<Employee> employees = new ArrayList<Employee>();

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map row : rows) {
			Employee employee = new Employee();
			employee.setId(Integer.parseInt(String.valueOf(row.get("ID"))));
			employee.setName((String) row.get("NAME"));
			employee.setAge(Integer.parseInt(String.valueOf(row.get("AGE"))));
			employees.add(employee);
		}

		return employees;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Employee findByPK(Integer pk) throws Exception {
		
		String sql = "SELECT * FROM EMPLOYEE WHERE ID = ?";

		Employee employee = (Employee) jdbcTemplate.queryForObject(sql, new Object[] { pk }, new BeanPropertyRowMapper(Employee.class));

		return employee;
	}

	public int save(Employee entity) throws Exception {
		
		String sql = "INSERT INTO EMPLOYEE (ID, NAME, AGE) VALUES (?, ?, ?)";

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {           

                @Override
                public PreparedStatement createPreparedStatement(Connection connection)
                        throws SQLException {
                    //PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement ps = connection.prepareStatement(sql.toString(), new String[]{"ID"});
                    ps.setString(1, person.getUsername());
                    ps.setString(2, person.getPassword());
                    ps.setString(3, person.getEmail());
                    ps.setLong(4, person.getRole().getId());
                    return ps;
                }
            }, holder);

			Long newPersonId = holder.getKey().longValue();	

		// return jdbcTemplate.update( sql, new Object[] { entity.getId(), entity.getName(), entity.getAge() });
	}

	public int update(Employee entity) throws Exception {

		int index = 0;
		int indexLastCommar = -1;
		String columns = "";
		StringBuilder sql = new StringBuilder();
		Map<String, Integer> mapIndex = new HashMap<String, Integer>();

		sql.append(" UPDATE EMPLOYEE SET ");

		if(entity.getName() != null) {
			columns += " name = ?, ";
			mapIndex.put("name", index++);
		}

		if(entity.getAge() != null) {
			columns += " age = ?, ";
			mapIndex.put("age", index++);
		}

		indexLastCommar = columns.lastIndexOf(",");
		columns = columns.substring(0, indexLastCommar - (columns.length - indexLastCommar));

		sql.append(columns);
		sql.append(" WHERE id = ? ");
		mapIndex.put("age", index++);

		int result = jdbcTemplate.update( new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				
				PreparedStatement ps = con.prepareStatement( sql );
				
				Integer idxName = getInteger(mapIndex, "name");
				Integer idxAge = getInteger(mapIndex, "age");
				Integer idxId = getInteger(mapIndex, "id");

				if(idxName != null) {
					ps.setString(idxName, entity.getName());
				}

				if(idxAge != null) {
					ps.getInt(idxId, entity.getAge());
				}

				if(idxId != null) {
					ps.getInt(idxId, entity.getId());
				}

				return ps;
				
			}
			
		});

		return result;
	}

	public static Integer getInteger(Map<String, Integer> map, String key) {
		return map != null ? map.get(key).toString() : null;
	}

	public int delete(Employee entity) throws Exception { 
		
		String sqlDelete = "DELETE FROM EMPLOYEE WHERE ID = ?";
		
		return jdbcTemplate.update(sqlDelete, entity.getId());
	}

	public Employee findLastPk() {
		String sql = "SELECT TOP 1 * FROM EMPLOYEE ORDER BY ID DESC";

		Employee employee = new Employee();

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> row : rows) { 
			employee.setId(Integer.parseInt(String.valueOf(row.get("ID"))));
			employee.setName((String) row.get("NAME"));
			employee.setAge(Integer.parseInt(String.valueOf(row.get("AGE"))));
		}
		return employee;
	} 

}
