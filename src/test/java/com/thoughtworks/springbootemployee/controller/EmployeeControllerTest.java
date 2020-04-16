package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.jupiter.api.Assertions.assertAll;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.standaloneSetup(employeeController);
    }

    @Test
    public void shouldReturnEmployee_whenGetEmployeeById() {
        MockMvcResponse response = given().contentType(ContentType.JSON).when().get("/employees/1");
        Employee actualResult = response.getBody().as(Employee.class);

        assertAll(
                () -> Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode()),
                () -> Assert.assertEquals(1, actualResult.getEmployeeId())
        );
    }

    @Test
    public void shouldReturnEmployeeList_whenGetEmployee() {
        MockMvcResponse response = given().contentType(ContentType.JSON).when().get("/employees");
        List<Employee> actualResultList = response.getBody().as(List.class);

        assertAll(
                () -> Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode()),
                () -> Assert.assertEquals(5, actualResultList.size())
        );
    }

    @Test
    public void shouldReturnEmployeeList_whenGetEmployeeByGender() {
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
}
