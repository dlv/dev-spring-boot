package br.com.pradolabs.cruddemo.rest;

import br.com.pradolabs.cruddemo.entity.Employee;
import br.com.pradolabs.cruddemo.service.EmployeeService;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeRestControllerTest {

	@Test
	void findAllReturnsListOfEmployees() {
		EmployeeService employeeService = mock(EmployeeService.class);

		List<Employee> employees = List.of(
				new Employee("John", "Doe", "john.doe@example.com"),
				new Employee("Jane", "Smith", "jane.smith@example.com")
		);

		when(employeeService.findAll()).thenReturn(employees);

		EmployeeRestController controller = new EmployeeRestController();
		controller.setEmployeeService(employeeService, null);

		List<Employee> result = controller.findAll();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("John", result.get(0).getFirstName());
		assertEquals("Jane", result.get(1).getFirstName());

		verify(employeeService, times(1)).findAll();
	}

	@Test
	void findAllReturnsEmptyListWhenNoEmployees() {
		EmployeeService employeeService = mock(EmployeeService.class);

		when(employeeService.findAll()).thenReturn(Collections.emptyList());

		EmployeeRestController controller = new EmployeeRestController();
		controller.setEmployeeService(employeeService, null);

		List<Employee> result = controller.findAll();

		assertNotNull(result);
		assertTrue(result.isEmpty());

		verify(employeeService, times(1)).findAll();
	}

	@Test
	void findAllReturnsNullWhenServiceReturnsNull() {
		EmployeeService employeeService = mock(EmployeeService.class);

		when(employeeService.findAll()).thenReturn(null);

		EmployeeRestController controller = new EmployeeRestController();
		controller.setEmployeeService(employeeService, null);

		List<Employee> result = controller.findAll();

		assertNull(result);

		verify(employeeService, times(1)).findAll();
	}
}


