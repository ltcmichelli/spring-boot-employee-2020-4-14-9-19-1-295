package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.jupiter.api.Assertions.assertAll;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyControllerTest {

    public void init() {
        CompanyController companyController = new CompanyController();
        RestAssuredMockMvc.standaloneSetup(companyController);
    }

    @Test
    public void shouldGetCompanyById_whenGetCompanyById(){
        init();
        MockMvcResponse response = given().contentType(ContentType.JSON).when().get("/companies/1");
        Company actualResult = response.getBody().as(Company.class);

        assertAll(
                () -> Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode()),
                () -> Assert.assertEquals(1, actualResult.getCompanyId())
        );

    }
}
