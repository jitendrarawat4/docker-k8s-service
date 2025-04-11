package io.rawat.department_service.impl;

import io.rawat.department_service.entity.Department;
import io.rawat.department_service.model.DepartmentRequest;
import io.rawat.department_service.model.DepartmentResponse;
import io.rawat.department_service.repository.DepartmentRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.mockito.Mockito.never;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest extends AbstractTestContainersTest{

    @Mock
    private DepartmentRepository departmentRepository;

    private ModelMapper modelMapper = new ModelMapper();

    private DepartmentServiceImpl departmentServiceImpl;

    @BeforeEach
    void setup() {
        departmentServiceImpl = new DepartmentServiceImpl(departmentRepository, modelMapper);
    }

    @Test
    void createDepartment() {
        DepartmentRequest departmentRequest = DepartmentRequest.builder()
                .departmentName("Computer Science")
                .build();
        when(departmentRepository.save(any(Department.class)))
                .thenReturn(Department.builder()
                        .departmentName("Computer Science")
                        .departmentNumber(1)
                        .build());

        DepartmentResponse actualDepartmentResponse = departmentServiceImpl.createDepartment(departmentRequest);
        DepartmentResponse expectedResponse = DepartmentResponse.builder()
                .departmentName("Computer Science")
                .departmentNumber(1)
                .build();
        assertThat(actualDepartmentResponse.getDepartmentNumber()).isNotNull();
        assertThat(actualDepartmentResponse.getDepartmentName()).isEqualTo(expectedResponse.getDepartmentName());
        assertThat(actualDepartmentResponse.getDepartmentNumber()).isEqualTo(1);
    }

    @Test
    void listAll() {
        departmentServiceImpl.listAll();
        verify(departmentRepository).findAll();
    }

    @Test
    void getByDepartmentNumberShouldHaveValue() {
        //given
        Integer departmentNumber = 1;
        Department department = Department.builder()
                .departmentName("Computer Science")
                .departmentNumber(1)
                .build();
        when(departmentRepository.findById(anyInt())).thenReturn(Optional.of(department));
        //when
        DepartmentResponse departmentResponse = departmentServiceImpl.getByDepartmentNumber(departmentNumber);
        //then
        assertThat(departmentResponse.getDepartmentNumber()).isEqualTo(departmentNumber);
        assertThat(departmentResponse.getDepartmentName()).isEqualTo(department.getDepartmentName());
    }

    @Test
    void getByDepartmentNumberShouldThrowRuntimeException() {
        //given
        Integer departmentNumber = 1;
        when(departmentRepository.findById(anyInt())).thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> departmentServiceImpl.getByDepartmentNumber(departmentNumber))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Department with " + departmentNumber + " does not exist");
    }

    @Test
    void updateDepartment() {
        //given
        Integer departmentNumber = 1;
        DepartmentRequest departmentRequest = DepartmentRequest.builder()
                .departmentName("Information Technology")
                .build();
        Department department = Department.builder()
                .departmentName("Computer Science")
                .departmentNumber(1)
                .build();
        when(departmentRepository.findById(eq(departmentNumber))).thenReturn(Optional.of(department));
        when(departmentRepository.findByDepartmentName(eq(departmentRequest.getDepartmentName())))
                .thenReturn(Optional.empty());
        when(departmentRepository.save(any(Department.class)))
                .thenReturn(Department.builder()
                        .departmentName("Information Technology")
                        .departmentNumber(1)
                        .build());
        //when
        DepartmentResponse departmentResponse = departmentServiceImpl.updateDepartment(departmentNumber, departmentRequest);

        assertThat(departmentResponse.getDepartmentName()).isEqualTo(departmentRequest.getDepartmentName());

    }

    @Test
    void updateDepartmentWhenNameAlreadyExists() {
        //given
        Integer departmentNumber = 1;
        DepartmentRequest departmentRequest = DepartmentRequest.builder()
                .departmentName("Information Technology")
                .build();
        Department department = Department.builder()
                .departmentName("Information Technology")
                .departmentNumber(1)
                .build();
        when(departmentRepository.findById(eq(departmentNumber))).thenReturn(Optional.of(department));
        when(departmentRepository.findByDepartmentName(eq(departmentRequest.getDepartmentName())))
                .thenReturn(Optional.of(department));
        //when
        assertThatThrownBy(() -> departmentServiceImpl.updateDepartment(departmentNumber, departmentRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Department name " + departmentRequest.getDepartmentName() + " has already been taken");
        verify(departmentRepository, never()).save(any());
    }

    @Test
    void updateDepartmentWhenDepartmentDoesNotExists() {
        //given
        Integer departmentNumber = 1;
        DepartmentRequest departmentRequest = DepartmentRequest.builder()
                .departmentName("Information Technology")
                .build();

        when(departmentRepository.findById(eq(departmentNumber))).thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> departmentServiceImpl.updateDepartment(departmentNumber, departmentRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Department with " + departmentNumber + " does not exist");
        verify(departmentRepository, never()).save(any());
        verify(departmentRepository, never()).findByDepartmentName(anyString());
    }

    @Test
    void deleteDepartment() {
        Integer departmentNumber = 1;
        Map<String, Object> deleteDepartment = departmentServiceImpl.deleteDepartment(departmentNumber);
        verify(departmentRepository).deleteById(departmentNumber);
        assertThat(deleteDepartment).containsKey("success");
        assertThat(deleteDepartment).containsValue(true);
    }
}