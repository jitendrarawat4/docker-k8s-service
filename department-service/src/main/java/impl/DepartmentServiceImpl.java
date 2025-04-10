package impl;

import org.springframework.stereotype.Service;
import repository.DepartmentRepository;
import service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl (DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }
}
