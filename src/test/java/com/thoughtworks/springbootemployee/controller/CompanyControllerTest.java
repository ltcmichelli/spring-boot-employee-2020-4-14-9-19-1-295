package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
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
import java.util.Arrays;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyControllerTest {
    public static final int ORIGINAL_EMPLOYEE_LIST_SIZE = 2;
    private Company company;
    private List<Employee> employeeList;
    private List<Company> companyList;

    @Mock
    private CompanyService service;

    @Before
    public void setup() {
        CompanyController companyController = new CompanyController(service);
        RestAssuredMockMvc.standaloneSetup(companyController);

        employeeList = Arrays.asList(new Employee(1, "Test", 18, "Male", 8000));
        company = new Company(1, "Test", 100, employeeList);
        companyList = Arrays.asList(company);
    }

    @Test
    public void shouldReturn200_whenGetCompanyById() throws Exception {
        doReturn(company).when(service).getCompanyById(any());

        MockMvcResponse response = given().contentType(ContentType.JSON).when().get("/companies/1");

        Company actualResult = response.getBody().as(Company.class);

        assertAll(
                () -> Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode()),
                () -> Assert.assertEquals(1, actualResult.getCompanyId().intValue())
        );
    }

    @Test
    public void shouldReturn404_givenInvalidId_whenGetCompanyById() throws Exception {
        doThrow(Exception.class).when(service).getCompanyById(any());

        MockMvcResponse response = given().contentType(ContentType.JSON).when().get("/companies/1");

        assertAll(
                () -> Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode())
        );
    }

    @Test
    public void shouldReturn200_whenGetCompanyList() throws Exception {
        doReturn(companyList).when(service).getSpecificCompanyList(any(), any());

        MockMvcResponse response = given().contentType(ContentType.JSON).when().get("/companies");

        assertAll(
                () -> Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode())
        );
    }

    @Test
    public void shouldReturn200_givenPageAndPageSizeParam_whenGetCompanyList() throws Exception {
        doReturn(companyList).when(service).getSpecificCompanyList(any(), any());

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .param("page", 1)
                .param("pageSize", 2)
                .when().get("/companies");

        assertAll(
                () -> Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode())
        );
    }

    @Test
    public void shouldReturn404_whenGetCompanyList() throws Exception {
        doThrow(Exception.class).when(service).getSpecificCompanyList(any(), any());

        MockMvcResponse response = given().contentType(ContentType.JSON).when().get("/companies");

        assertAll(
                () -> Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode())
        );
    }

    @Test
    public void shouldReturn200_whenGetEmployeeListByCompanyId() throws Exception {
        doReturn(employeeList).when(service).getEmployeeListByCompanyId(any());

        MockMvcResponse response = given().contentType(ContentType.JSON).when().get("/companies/1/employees");

        assertAll(
                () -> Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode())
        );
    }

    @Test
    public void shouldReturn404_whenGetEmployeeListByCompanyId() throws Exception {
        doThrow(Exception.class).when(service).getEmployeeListByCompanyId(any());

        MockMvcResponse response = given().contentType(ContentType.JSON).when().get("/companies/1/employees");

        assertAll(
                () -> Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode())
        );
    }

    @Test
    public void shouldReturn201_whenAddCompany() throws Exception {
        Company newCompany = new Company();
        doReturn(company).when(service).addCompany(any());

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newCompany)
                .when().post("/companies");

        assertAll(
                () -> Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode())
        );
    }

    @Test
    public void shouldReturn400_whenAddCompany() throws Exception {
        doThrow(Exception.class).when(service).addCompany(any());

        MockMvcResponse response = given().contentType(ContentType.JSON).when().post("/companies");

        assertAll(
                () -> Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode())
        );
    }

    @Test
    public void shouldReturn200_whenUpdateCompany() throws Exception {
        Company newCompany = new Company();
        doReturn(company).when(service).updateCompany(any(), any());

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newCompany)
                .when().put("/companies/1");

        assertAll(
                () -> Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode())
        );
    }

    @Test
    public void shouldReturn404_whenUpdateCompany() throws Exception {
        Company newCompany = new Company();
        doThrow(Exception.class).when(service).updateCompany(any(), any());

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newCompany)
                .when().put("/companies/1");

        assertAll(
                () -> Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode())
        );
    }

    @Test
    public void shouldReturn204_whenDeleteCompany() throws Exception {
        doNothing().when(service).deleteCompany(any());

        MockMvcResponse response = given().contentType(ContentType.JSON).when().delete("/companies/1");

        assertAll(
                () -> Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode())
        );
    }

    @Test
    public void shouldReturn404_whenDeleteCompany() throws Exception {
        doThrow(Exception.class).when(service).deleteCompany(any());

        MockMvcResponse response = given().contentType(ContentType.JSON).when().delete("/companies/1");

        assertAll(
                () -> Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode())
        );
    }
}
