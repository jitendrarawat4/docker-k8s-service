package io.rawat.department_service.repository;

import io.rawat.department_service.entity.Department;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Container
    @ServiceConnection
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:latest"));

    @Test void canEstablishConnection(){
        assertThat(mysqlContainer.isCreated()).isTrue();
        assertThat(mysqlContainer.isRunning()).isTrue();
    }

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
        Optional<Department> byDepartmentName = departmentRepository.findByDepartmentName("Infomation Technology");
        assertThat(byDepartmentName).isNotPresent();
    }
}