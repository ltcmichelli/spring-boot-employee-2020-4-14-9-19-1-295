package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.jupiter.api.Assertions.assertAll;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    public static final int ORIGINAL_EMPLOYEE_LIST_SIZE = 5;

    public void init() {
        EmployeeController employeeController = new EmployeeController();
        RestAssuredMockMvc.standaloneSetup(employeeController);
    }

    @Test
    public void shouldReturnEmployee_whenGetEmployeeById() {
        init();
        MockMvcResponse response = given().contentType(ContentType.JSON).when().get("/employees/1");
        Employee actualResult = response.getBody().as(Employee.class);

        assertAll(
                () -> Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode()),
                () -> Assert.assertEquals(1, actualResult.getEmployeeId())
        );
    }

    @Test
    public void shouldReturnEmployeeList_whenGetEmployee() {
        init();
        MockMvcResponse response = given().contentType(ContentType.JSON).when().get("/employees");
        List<Employee> actualResultList = response.getBody().as(List.class);

        assertAll(
                () -> Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode()),
                () -> Assert.assertEquals(ORIGINAL_EMPLOYEE_LIST_SIZE, actualResultList.size())
        );
    }

    @Test
    public void shouldReturnEmployeeList_whenGetEmployeeByGender() {
        init();
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .param("gender", "Male")
                .when().get("/employees");
        List<Employee> actualResultList = response.getBody().as(List.class);

        assertAll(
                () -> Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode()),
                () -> Assert.assertEquals(3, actualResultList.size())
        );
    }

    @Test
    public void shouldReturnEmployeeListWithPaging_whenGetEmployeeWithPageSize() {
        init();
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .param("page", 1)
                .param("pageSize", 2)
                .when().get("/employees");
        List<Employee> actualResultList = response.getBody().as(List.class);

        assertAll(
                () -> Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode()),
                () -> Assert.assertEquals(2, actualResultList.size())
        );
    }

    @Test
    public void shouldAddNewEmployee_whenAddEmployee() {
        init();
        Employee newEmployee = new Employee();
        newEmployee.setEmployeeId(6);
        newEmployee.setName("Test");

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newEmployee)
                .when()
                .post("/employees");


        List<Employee> actualResultList = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        assertAll(
                () -> Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode()),
                () -> Assert.assertEquals(ORIGINAL_EMPLOYEE_LIST_SIZE + 1, actualResultList.size()),
                () -> Assert.assertEquals(newEmployee.getEmployeeId(), actualResultList.get(5).getEmployeeId())
        );
    }

    @Test
    public void shouldUpdateEmployee_whenUpdateEmployeeById() {
        init();
        MockMvcResponse response = given().contentType(ContentType.JSON).when().get("/employees/1");
        Employee targetEmployee = response.getBody().as(Employee.class);
        targetEmployee.setName("Test");

        given().contentType(ContentType.JSON)
                .body(targetEmployee)
                .when().put("/employees/1");

        MockMvcResponse newResponse = given().contentType(ContentType.JSON).when().get("/employees/1");
        Employee updatedEmployee = newResponse.getBody().as(Employee.class);

        assertAll(
                () -> Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode()),
                () -> Assert.assertEquals(targetEmployee.getName(), updatedEmployee.getName())
        );

    }

    @Test
    public void shouldDeleteEmployee_whenDeleteEmployeeById() {
        init();
        MockMvcResponse responseOfDelete = given().contentType(ContentType.JSON).when().delete("/employees/1");

        MockMvcResponse responseOfGetAllList = given().contentType(ContentType.JSON).when().get("/employees");
        List<Employee> actualResultList = responseOfGetAllList.getBody().as(List.class);

        assertAll(
                () -> Assert.assertEquals(HttpStatus.OK.value(), responseOfDelete.getStatusCode()),
                () -> Assert.assertEquals(ORIGINAL_EMPLOYEE_LIST_SIZE - 1, actualResultList.size())
        );

    }
}
