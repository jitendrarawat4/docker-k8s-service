package io.rawat.employee_service.service;

import io.rawat.employee_service.model.EmployeeCreateRequest;
import io.rawat.employee_service.model.EmployeeResponse;
import io.rawat.employee_service.model.EmployeeWithDepartment;
import jakarta.validation.Valid;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse createEmployee(@Valid EmployeeCreateRequest employeeCreateRequest);

    List<EmployeeResponse> listAll();

    EmployeeResponse findByEmployeeNumber(Integer employeeNumber);

    String deleteByEmployeeNumber(Integer employeeNumber);

    List<EmployeeWithDepartment> getEmployeeWithDepartment();
}