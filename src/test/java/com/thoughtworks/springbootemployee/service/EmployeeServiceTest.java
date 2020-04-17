package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository repository;

    private List<Employee> employeeList = new ArrayList<>();
    ;
    private List<Employee> employeeListWithGender = new ArrayList<>();
    ;

    @Before
    public void setup() {
        employeeList.add(new Employee(1, "Xiaoming", 20, "Male", 9000));
        employeeList.add(new Employee(2, "Xiaohong", 19, "Female", 9000));
        employeeList.add(new Employee(3, "Xiaozhi", 15, "Male", 9000));
        employeeList.add(new Employee(4, "Xiaogang", 16, "Male", 9000));
        employeeList.add(new Employee(5, "Xiaoxia", 15, "Female", 9000));

        employeeListWithGender.add(new Employee(1, "Xiaoming", 20, "Male", 9000));
        employeeListWithGender.add(new Employee(3, "Xiaozhi", 15, "Male", 9000));
        employeeListWithGender.add(new Employee(4, "Xiaogang", 16, "Male", 9000));

    }

    @Test
    public void shouldGetAllList_givenNoParam_whenGetSpecificEmployeeList() throws Exception {
        EmployeeService service = new EmployeeService(repository);
        doReturn(employeeList).when(repository).findAll();
        List<Employee> resultList = service.getSpecificEmployeeList(null, null, null);

        assertAll(
                () -> Assert.assertEquals(employeeList, resultList)
        );
    }

    @Test
    public void shouldGetAllList_givenGender_whenGetSpecificEmployeeList() throws Exception {
        EmployeeService service = new EmployeeService(repository);
        doReturn(employeeListWithGender).when(repository).findAllByGender(any());
        List<Employee> resultList = service.getSpecificEmployeeList("Male", null, null);

        assertAll(
                () -> Assert.assertEquals(employeeListWithGender, resultList)
        );
    }

    @Test
    public void shouldGetAllList_givenPageAndPageSize_whenGetSpecificEmployeeList() throws Exception {
        EmployeeService service = new EmployeeService(repository);
        doReturn(employeeList).when(repository).findAll();
        List<Employee> resultList = service.getSpecificEmployeeList(null, 1, 2);

        assertAll(
                () -> Assert.assertEquals(2, resultList.size())
        );
    }
}
