package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    public List<Employee> employeeList = new ArrayList<>();
    public List<Employee> employeeListByGender = new ArrayList<>();
    public List<Employee> employeeListWithPaging = new ArrayList<>();
    public Employee employee = new Employee();

    @Mock
    private EmployeeService service;

    @Before
    public void setup() {
        EmployeeController employeeController = new EmployeeController(service);
        RestAssuredMockMvc.standaloneSetup(employeeController);

        Employee employee = new Employee();
        employee.setName("Test");
        employee.setCompanyId(1);

        employeeList.add(employee);

        employeeListByGender.add(employee);
        employeeListByGender.add(employee);
        employeeListByGender.add(employee);

        employeeListWithPaging.add(employee);
        employeeListWithPaging.add(employee);

        employee.setEmployeeId(1);
        employee.setName("Test");

    }

    @Test
    public void shouldReturn200_whenGetEmployeeById() throws Exception {
        doReturn(employee).when(service).getEmployeeById(any());
        MockMvcResponse response = given().contentType(ContentType.JSON).when().get("/employees/1");
        Employee actualResult = response.getBody().as(Employee.class);

        assertAll(
                () -> Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode())
        );
    }

    @Test
    public void shouldReturn404_whenGetEmployeeById() throws Exception {
        doThrow(Exception.class).when(service).getEmployeeById(any());
        MockMvcResponse response = given().contentType(ContentType.JSON).when().get("/employees/1");

        assertAll(
                () -> Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode())
        );
    }

    @Test
    public void shouldReturn200_whenGetEmployee() throws Exception {
        doReturn(employeeList).when(service).getSpecificEmployeeList(any(), any(), any());
        MockMvcResponse response = given().contentType(ContentType.JSON).when().get("/employees");
        List<Employee> actualResultList = response.getBody().as(List.class);

        assertAll(
                () -> Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode())
        );
    }

    @Test
    public void shouldReturn200_whenGetEmployeeByGender() throws Exception {
        doReturn(employeeListByGender).when(service).getSpecificEmployeeList(any(), any(), any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .param("gender", "Male")
                .when().get("/employees");
        List<Employee> actualResultList = response.getBody().as(List.class);

        assertAll(
                () -> Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode())
        );
    }

    @Test
    public void shouldReturn200_whenGetEmployeeWithPageSize() throws Exception {
        doReturn(employeeListWithPaging).when(service).getSpecificEmployeeList(any(), any(), any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .param("page", 1)
                .param("pageSize", 2)
                .when().get("/employees");
        List<Employee> actualResultList = response.getBody().as(List.class);

        assertAll(
                () -> Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode())
        );
    }

    @Test
    public void shouldReturn201_whenAddEmployee() throws Exception {
        Employee newEmployee = new Employee();
        newEmployee.setEmployeeId(6);
        newEmployee.setName("Test");
        doReturn(newEmployee).when(service).addEmployee(any());

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newEmployee)
                .when()
                .post("/employees");

        assertAll(
                () -> Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode())
        );
    }

    @Test
    public void shouldReturn400_whenAddEmployee() throws Exception {
        Employee newEmployee = new Employee();
        doThrow(Exception.class).when(service).addEmployee(any());

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newEmployee)
                .when()
                .post("/employees");

        assertAll(
                () -> Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode())
        );
    }

    @Test
    public void shouldReturn200_whenUpdateEmployeeById() throws Exception {
        doReturn(employee).when(service).updateEmployee(any(), any());

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(employee)
                .when().put("/employees/1");

        assertAll(
                () -> Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode())
        );
    }

    @Test
    public void shouldReturn404_whenUpdateEmployeeById() throws Exception {
        doThrow(Exception.class).when(service).updateEmployee(any(), any());

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(employee)
                .when().put("/employees/1");

        assertAll(
                () -> Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode())
        );
    }

    @Test
    public void shouldReturn204_givenDeleteIsSuccess_whenDeleteEmployeeById() throws Exception {
        doNothing().when(service).deleteEmployee(any());
        MockMvcResponse responseOfDelete = given().contentType(ContentType.JSON).when().delete("/employees/1");

        assertAll(
                () -> Assert.assertEquals(HttpStatus.NO_CONTENT.value(), responseOfDelete.getStatusCode())
        );
    }

    @Test
    public void shouldReturn404_givenDeleteIsFail_whenDeleteEmployeeById() throws Exception {
        doThrow(Exception.class).when(service).deleteEmployee(any());
        MockMvcResponse responseOfDelete = given().contentType(ContentType.JSON).when().delete("/employees/1");

        assertAll(
                () -> Assert.assertEquals(HttpStatus.NOT_FOUND.value(), responseOfDelete.getStatusCode())
        );
    }
}
