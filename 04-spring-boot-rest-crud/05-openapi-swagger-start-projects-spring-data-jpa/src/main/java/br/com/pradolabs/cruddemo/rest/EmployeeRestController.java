package br.com.pradolabs.cruddemo.rest;

import br.com.pradolabs.cruddemo.entity.Employee;
import br.com.pradolabs.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class EmployeeRestController {

    private EmployeeService employeeService;
    private JsonMapper jsonMapper;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService,  JsonMapper jsonMapper) {
        this.employeeService = employeeService;
        this.jsonMapper = jsonMapper;
    }

    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {
        var employee = employeeService.findById(employeeId);

        if(employee == null){
            throw new RuntimeException("Employee not found");
        }

        return employee;
    }

    @PostMapping("/employees")
    private Employee addEmployee(@RequestBody Employee employee) {
        employee.setId(0);
        return employeeService.save(employee);
    }

    @PutMapping("/employees")
    private Employee updateEmployee(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @PatchMapping("/employees/{employeeId}")
    private Employee patchEmployee(@PathVariable int employeeId, @RequestBody Map<String, Object> patchPayload) {
        var employee = employeeService.findById(employeeId);

        if(employee == null){
            throw new RuntimeException("Employee id not found - "+ employeeId);
        }

        if(patchPayload.containsKey("id")){
            throw new RuntimeException("Employee id not allowed in request body - "+ employeeId);
        }

        Employee patchedEmployee = jsonMapper.updateValue(employee, patchPayload);

        return employeeService.save(patchedEmployee);
    }

    @DeleteMapping("/employees/{employeeId}")
    private String deleteEmployee(@PathVariable int employeeId) {
        var employee = employeeService.findById(employeeId);

        if (employee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }

        employeeService.deleteById(employeeId);
        return "Deleted employee id - " + employeeId;
    }
}
