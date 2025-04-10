package io.rawat.employee_service.impl;

import io.rawat.employee_service.entity.Employee;
import io.rawat.employee_service.model.EmployeeCreateRequest;
import io.rawat.employee_service.model.EmployeeResponse;
import io.rawat.employee_service.repository.EmployeeRepository;
import io.rawat.employee_service.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeResponse createEmployee(EmployeeCreateRequest employeeCreateRequest) {
        Employee empReq = modelMapper.map(employeeCreateRequest, Employee.class);
        var savedEmployee = employeeRepository.save(empReq);
        return modelMapper.map(savedEmployee, EmployeeResponse.class);
    }

    @Override
    public List<EmployeeResponse> listAll() {
        return employeeRepository.findAll()
                .stream()
                .map(e -> modelMapper.map(e, EmployeeResponse.class))
                .toList();
    }

    @Override
    public EmployeeResponse findByEmployeeNumber(Integer employeeNumber) {
        return  employeeRepository.findById(employeeNumber)
                .map(e-> modelMapper.map(e, EmployeeResponse.class))
                .orElseThrow(()-> new RuntimeException("Employee Not Found"));
    }

    @Override
    public String deleteByEmployeeNumber(Integer employeeNumber) {
        employeeRepository.deleteById(employeeNumber);
        return "Success";
    }
}
