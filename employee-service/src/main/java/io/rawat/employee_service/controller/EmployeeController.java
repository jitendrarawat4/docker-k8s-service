package io.rawat.employee_service.controller;

import io.rawat.employee_service.entity.Employee;
import io.rawat.employee_service.model.EmployeeCreateRequest;
import io.rawat.employee_service.model.EmployeeResponse;
import io.rawat.employee_service.model.EmployeeWithDepartment;
import io.rawat.employee_service.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping
    public EmployeeResponse create(@RequestBody @Valid EmployeeCreateRequest employeeCreateRequest){
        return employeeService.createEmployee(employeeCreateRequest);
    }

    @GetMapping
    public List<EmployeeResponse> list(){
        return employeeService.listAll();
    }

    @GetMapping("{employeeNumber}")
    public EmployeeResponse findByEmployeeNumber(@PathVariable (value="employeeNumber") Integer employeeNumber){
        return employeeService.findByEmployeeNumber(employeeNumber);
    }

    @GetMapping("with-department/fetch")
    public  List<EmployeeWithDepartment> getEmployeeWithDepartment(){
        return employeeService.getEmployeeWithDepartment();
    }

    @DeleteMapping("{employeeNumber}")
    public String deleteByEmployeeNumber(@PathVariable (value="employeeNumber") Integer employeeNumber){
        return employeeService.deleteByEmployeeNumber(employeeNumber);
    }


}
