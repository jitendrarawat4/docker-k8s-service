package io.rawat.department_service.impl;

import io.rawat.department_service.entity.Department;
import io.rawat.department_service.model.DepartmentRequest;
import io.rawat.department_service.model.DepartmentResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import io.rawat.department_service.repository.DepartmentRepository;
import io.rawat.department_service.service.DepartmentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest) {
        Department deptReq = modelMapper.map(departmentRequest, Department.class);
        Department saved = departmentRepository.save(deptReq);
        return modelMapper.map(saved, DepartmentResponse.class);
    }

    @Override
    public List<DepartmentResponse> listAll() {

        return departmentRepository.findAll()
                .stream()
                .map(department -> modelMapper.map(department, DepartmentResponse.class))
                .toList();
    }

    @Override
    public DepartmentResponse getByDepartmentNumber(Integer departmentNumber) {
        Department department = departmentRepository.findById(departmentNumber)
                .orElseThrow(() -> new RuntimeException("Department with " + departmentNumber + " does not exist"));
        return modelMapper.map(department, DepartmentResponse.class);
    }

    @Override
    public DepartmentResponse updateDepartment(Integer departmentNumber, DepartmentRequest departmentRequest) {
        Department department = departmentRepository.findById(departmentNumber)
                .orElseThrow(() -> new RuntimeException("Department with " + departmentNumber + " does not exist"));
        boolean departmentNameExists = departmentRepository.findByDepartmentName(departmentRequest.getDepartmentName()).isPresent();
        if (departmentNameExists) {
            throw new RuntimeException("Department name " + departmentRequest.getDepartmentName() + " has already been taken");
        }
        department.setDepartmentName(departmentRequest.getDepartmentName());

        Department updated = departmentRepository.save(department);
        return modelMapper.map(updated, DepartmentResponse.class);
    }

    @Override
    public Map<String, Object> deleteDepartment(Integer departmentNumber) {
        departmentRepository.deleteById(departmentNumber);
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        return map;
    }
}
