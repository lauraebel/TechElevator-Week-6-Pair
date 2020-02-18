package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.Project;
import com.techelevator.projects.model.ProjectDAO;

public class JDBCProjectDAO implements ProjectDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCProjectDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Project> getAllActiveProjects() {
		List<Project> projects = new ArrayList<>();
		
		String proj = "SELECT project_id, name, from_date, to_date FROM project";
		SqlRowSet results = jdbcTemplate.queryForRowSet(proj);
		
		while(results.next()) {
			projects.add(mapRowToProject (results));
		}
		return projects;
	}	
	
	@Override
	public void removeEmployeeFromProject(Long projectId, Long employeeId) {
		String sqlDeleteEmployee = "DELETE FROM project_employee WHERE project_id = ? AND employee_id = ?";
		jdbcTemplate.update(sqlDeleteEmployee, projectId, employeeId);
	}

	@Override
	public void addEmployeeToProject(Long projectId, Long employeeId) {
		String sqlInsertEmployee = "INSERT INTO project_employee VALUES(?,?)";
		jdbcTemplate.update(sqlInsertEmployee, projectId, employeeId);
	}
	
	private Project mapRowToProject(SqlRowSet results) {
		Project project = new Project();
		
		project.setId(results.getLong("project_id"));
		project.setName(results.getString("name"));

		return project;
		
	}

}
