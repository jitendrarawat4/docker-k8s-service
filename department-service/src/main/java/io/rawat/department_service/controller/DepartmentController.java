package io.rawat.department_service.controller;

import org.springframework.web.bind.annotation.*;
import io.rawat.department_service.service.DepartmentService;
import io.rawat.department_service.model.DepartmentResponse;
import io.rawat.department_service.model.DepartmentRequest;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController (DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    @PostMapping
    public DepartmentResponse createDepartment(@RequestBody DepartmentRequest departmentrequest){
        return departmentService.createDepartment(departmentrequest);
    }

    @GetMapping
    List<DepartmentResponse> list(){
        return departmentService.listAll();
    }

    @GetMapping("{departmentNumber}")
    public DepartmentResponse getByDepartmentNumber(@PathVariable Integer departmentNumber){
        return departmentService.getByDepartmentNumber(departmentNumber);
    }

    @PutMapping("{departmentNumber}")
    public DepartmentResponse update(@PathVariable Integer departmentNumber, @RequestBody DepartmentRequest departmentRequest){
       return departmentService.updateDepartment(departmentNumber, departmentRequest);
    }

    @DeleteMapping("{departmentNumber}")
    public Map<String, Object> deleteDepartment(@PathVariable Integer departmentNumber){
        return departmentService.deleteDepartment(departmentNumber);
    }
}
