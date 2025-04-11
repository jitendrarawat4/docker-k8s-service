package io.rawat.employee_service.model;

import io.rawat.employee_service.entity.Gender;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeWithDepartment {

    private Integer employeeNumber;

    private String firstName;

    private String lastName;

    private Gender gender;

    private LocalDate birthDate;

    private LocalDate hireDate;

    private Integer departmentId;

    private DepartmentResponse departmentResponse;
}
