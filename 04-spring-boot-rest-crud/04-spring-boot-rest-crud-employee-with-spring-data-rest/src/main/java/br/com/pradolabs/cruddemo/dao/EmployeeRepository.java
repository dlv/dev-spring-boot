package br.com.pradolabs.cruddemo.dao;

import br.com.pradolabs.cruddemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

// @RepositoryRestResource(path = "members")
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
