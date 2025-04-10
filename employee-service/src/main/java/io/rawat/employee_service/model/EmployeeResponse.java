package io.rawat.employee_service.model;

import io.rawat.employee_service.entity.Gender;
import jakarta.persistence.*;
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
public class EmployeeResponse {

    private Integer employeeNumber;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private Gender gender;

    @Past(message= "birth date needs to be past date")
    private LocalDate birthDate;

    @PastOrPresent(message= "hire date needs to be past or present")
    private LocalDate hireDate;
}
