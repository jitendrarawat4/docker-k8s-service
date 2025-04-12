package io.rawat.employee_service.impl;

import io.rawat.employee_service.entity.Employee;
import io.rawat.employee_service.model.DepartmentResponse;
import io.rawat.employee_service.model.EmployeeCreateRequest;
import io.rawat.employee_service.model.EmployeeResponse;
import io.rawat.employee_service.model.EmployeeWithDepartment;
import io.rawat.employee_service.repository.EmployeeRepository;
import io.rawat.employee_service.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final WebClient webClient;
    private final RestClient restClient;
    private final RestTemplate restTemplate;

    @Value("${department-service-host-url}")
    private String departmentServiceHostUrl;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper, WebClient webClient,
                               RestClient restClient, RestTemplate restTemplate) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.webClient = webClient;
        this.restClient = restClient;
        this.restTemplate = restTemplate;
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
        return employeeRepository.findById(employeeNumber)
                .map(e -> modelMapper.map(e, EmployeeResponse.class))
                .orElseThrow(() -> new RuntimeException("Employee Not Found"));
    }

    @Override
    public String deleteByEmployeeNumber(Integer employeeNumber) {
        employeeRepository.deleteById(employeeNumber);
        return "Success";
    }

    @Override
    public List<EmployeeWithDepartment> getEmployeeWithDepartment() {
        List<EmployeeWithDepartment> employeeList = employeeRepository.findAll()
                .stream()
                .map(e -> modelMapper.map(e, EmployeeWithDepartment.class))
                .toList();
        //Using Web Client
        // List<DepartmentResponse> departmentList = getDepartmentUsingWebClient();

        //Using Rest Client
        List<DepartmentResponse> departmentList = getDepartmentUsingRestClient();

        //Using Rest Template
        //List<DepartmentResponse> departmentList = getDepartmentUsingRestTemplate();

        Map<Integer, DepartmentResponse> departmentMap = departmentList.stream()
                .collect(Collectors.toMap(DepartmentResponse::getDepartmentNumber, Function.identity()));

        employeeList.forEach(emp -> {
            DepartmentResponse dept = departmentMap.get(emp.getDepartmentId());
            emp.setDepartmentResponse(dept);
        });

        return employeeList;
    }

    //Using Web Client
    private List<DepartmentResponse> getDepartmentUsingWebClient() {
        log.info("Making call via Web Client");
        return webClient.get()
                .uri(departmentServiceHostUrl+"/api/department")
                .retrieve()
                .bodyToFlux(DepartmentResponse.class)
                .collectList()
                .block();
    }

    //Using Rest Client
    private List<DepartmentResponse> getDepartmentUsingRestClient() {
        DepartmentResponse[] departments = restClient.get()
                .uri(departmentServiceHostUrl+"/api/department")
                .retrieve()
                .body(DepartmentResponse[].class);
        log.info("Making call via Rest Client");
        return departments != null ? Arrays.asList(departments) : Collections.emptyList();
    }

    //Using Rest Template
    private List<DepartmentResponse> getDepartmentUsingRestTemplate() {
        log.info("Making call via Rest Template");
        DepartmentResponse[] departments = restTemplate.getForObject(
                departmentServiceHostUrl+"/api/department",
                DepartmentResponse[].class
        );
        return departments != null ? Arrays.asList(departments) : Collections.emptyList();
    }
}
