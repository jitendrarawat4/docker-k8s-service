package io.rawat.department_service.repository;

import io.rawat.department_service.entity.Department;
import io.rawat.department_service.impl.AbstractTestContainersTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DepartmentRepositoryTest extends AbstractTestContainersTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setup(){
     Department department =  Department.builder().departmentName("Computer Science").build();
     departmentRepository.save(department);
    }

    @AfterEach
    void tearDown(){
        departmentRepository.deleteAll();
    }

    @Test
    void shouldFindByDepartmentName() {
         Optional<Department> byDepartmentName = departmentRepository.findByDepartmentName("Computer Science");
         assertThat(byDepartmentName).isPresent();
    }

    @Test
    void shouldNotFindByDepartmentName() {
        Optional<Department> byDepartmentName = departmentRepository.findByDepartmentName("Information Technology");
        assertThat(byDepartmentName).isNotPresent();
    }
}