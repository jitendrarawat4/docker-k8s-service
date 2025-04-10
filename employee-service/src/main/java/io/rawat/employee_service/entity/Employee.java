package io.rawat.employee_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="tbl_employees")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Employee {

    @Id
    @Column(name= "emp_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeNumber;

    @Column(name= "first_name")
    @NotEmpty
    private String firstName;

    @Column(name= "last_name")
    @NotEmpty
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name= "birth_date")
    @Past(message= "birth date needs to be past date")
    private LocalDate birthDate;

    @Column(name= "hire_date")
    @PastOrPresent(message= "hire date needs to be past or present")
    private LocalDate hireDate;


}