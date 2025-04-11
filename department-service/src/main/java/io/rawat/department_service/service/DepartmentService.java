package io.rawat.department_service.service;

import io.rawat.department_service.model.DepartmentRequest;
import io.rawat.department_service.model.DepartmentResponse;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    DepartmentResponse createDepartment(DepartmentRequest departmentrequest);

    List<DepartmentResponse> listAll();

    DepartmentResponse getByDepartmentNumber(Integer departmentNumber);

    DepartmentResponse updateDepartment(Integer departmentNumber, DepartmentRequest departmentRequest);

    Map<String, Object> deleteDepartment(Integer departmentNumber);
}
