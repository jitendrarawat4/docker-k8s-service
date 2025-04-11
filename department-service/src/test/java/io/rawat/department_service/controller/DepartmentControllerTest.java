package io.rawat.department_service.controller;

import io.rawat.department_service.impl.AbstractTestContainersTest;
import io.rawat.department_service.model.DepartmentRequest;
import io.rawat.department_service.model.DepartmentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class DepartmentControllerTest extends AbstractTestContainersTest {

    private static final String API_BASE_PATH = "/api/department";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void createDepartment() {
        //given
        DepartmentRequest departmentRequest = DepartmentRequest.builder()
                .departmentName("Business Commerce")
                .build();
        ResponseEntity<DepartmentResponse> response = testRestTemplate.exchange(API_BASE_PATH, POST, new HttpEntity<>(departmentRequest),
                new ParameterizedTypeReference<>() {
                });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //when
        ResponseEntity<List<DepartmentResponse>> listResponse = testRestTemplate.exchange(
                API_BASE_PATH, GET, null,
                new ParameterizedTypeReference<>() {
                });
        assertThat(listResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        DepartmentResponse createdDepartmentResponse = Objects.requireNonNull(listResponse.getBody())
                .stream()
                .filter(dr -> dr.getDepartmentName().equals(departmentRequest.getDepartmentName()))
                .findFirst()
                .orElseThrow();
        assertThat(createdDepartmentResponse.getDepartmentNumber()).isNotNull();
        assertThat(createdDepartmentResponse.getDepartmentName()).isNotNull().isEqualTo(departmentRequest.getDepartmentName());
    }

    @Test
    void getByDepartmentNumber() {
        //given
        DepartmentRequest departmentRequest = DepartmentRequest.builder()
                .departmentName("Computer Science")
                .build();
        ResponseEntity<DepartmentResponse> response = testRestTemplate.exchange(API_BASE_PATH, POST,
                new HttpEntity<>(departmentRequest),
                new ParameterizedTypeReference<>() {
                });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        DepartmentResponse departmentResponse = Objects.requireNonNull(response.getBody());
        ResponseEntity<DepartmentResponse> getByDepartmentNumberResponse = testRestTemplate.exchange(
                API_BASE_PATH + "/" + departmentResponse.getDepartmentNumber(), GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        assertThat(getByDepartmentNumberResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        DepartmentResponse departmentByNumber = Objects.requireNonNull(getByDepartmentNumberResponse.getBody());
        assertThat(departmentByNumber.getDepartmentNumber()).isEqualTo(departmentResponse.getDepartmentNumber());

    }

    @Test
    void update() {
        // given
        DepartmentRequest departmentRequest = DepartmentRequest.builder()
                .departmentName("Engineering")
                .build();

        ResponseEntity<DepartmentResponse> response = testRestTemplate.exchange(
                API_BASE_PATH, POST,
                new HttpEntity<>(departmentRequest),
                new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DepartmentResponse departmentResponse = Objects.requireNonNull(response.getBody());

        // Create the update request properly
        DepartmentRequest updateRequest = DepartmentRequest.builder()
                .departmentName("Tourism and Hospitality")
                .build();

        ResponseEntity<DepartmentResponse> updateResponse = testRestTemplate.exchange(
                API_BASE_PATH + "/" + departmentResponse.getDepartmentNumber(), PUT,
                new HttpEntity<>(updateRequest),
                new ParameterizedTypeReference<>() {
                });

        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DepartmentResponse updatedDepartmentResponse = Objects.requireNonNull(updateResponse.getBody());
        assertThat(updatedDepartmentResponse.getDepartmentName()).isEqualTo("Tourism and Hospitality");
        assertThat(updatedDepartmentResponse.getDepartmentNumber()).isEqualTo(departmentResponse.getDepartmentNumber());
    }


    @Test
    void deleteDepartment() {
        //given
        DepartmentRequest departmentRequest = DepartmentRequest.builder()
                .departmentName("Information Technology")
                .build();
        ResponseEntity<DepartmentResponse> response = testRestTemplate.exchange(API_BASE_PATH, POST,
                new HttpEntity<>(departmentRequest),
                new ParameterizedTypeReference<>() {
                });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //when
        DepartmentResponse departmentResponse = Objects.requireNonNull(response.getBody());
        ResponseEntity<Map<String, Object>> deleteResponse = testRestTemplate.exchange(
                API_BASE_PATH + "/" + departmentResponse.getDepartmentNumber(), DELETE,
                new HttpEntity<>(departmentRequest),
                new ParameterizedTypeReference<>() {
                });
        Map<String, Object> deleteResponseMap = Objects.requireNonNull(deleteResponse.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deleteResponseMap).containsKey("success");
        assertThat(deleteResponseMap).containsValue(true);
    }
}