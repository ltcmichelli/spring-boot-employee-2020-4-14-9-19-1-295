package com.thoughtworks.springbootemployee.reposotory;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeRepositoryTest {
    public static final int ORIGINAL_EMPLOYEE_LIST_SIZE = 5;

    @Test
    public void shouldReturnAllEmployeeList_whenFindAll() {
        EmployeeRepository repository = new EmployeeRepository();
        List<Employee> resultList = repository.findAll();

        assertAll(
                () -> Assert.assertEquals(ORIGINAL_EMPLOYEE_LIST_SIZE, resultList.size())
        );
    }

    @Test
    public void shouldReturnEmployee_whenFindById() throws Exception {
        EmployeeRepository repository = new EmployeeRepository();
        Employee resultEmployee = repository.findById(1);

        assertAll(
                () -> Assert.assertEquals(1, resultEmployee.getEmployeeId().intValue()),
                () -> Assert.assertEquals("Xiaoming", resultEmployee.getName())
        );
    }

    @Test
    public void shouldAddEmployee_whenSave() throws Exception {
        EmployeeRepository repository = new EmployeeRepository();
        Employee employee = new Employee(6, "Test", 18, "Male", 8000);
        repository.save(employee);

        assertAll(
                () -> Assert.assertEquals(ORIGINAL_EMPLOYEE_LIST_SIZE + 1, repository.getEmployeeList().size())
        );
    }

    @Test
    public void shouldRemoveEmployee_whenDelete() throws Exception {
        EmployeeRepository repository = new EmployeeRepository();
        repository.deleteById(1);

        assertAll(
                () -> Assert.assertEquals(ORIGINAL_EMPLOYEE_LIST_SIZE - 1, repository.getEmployeeList().size())
        );
    }

    @Test
    public void shouldReturnEmployeeList_whenFindByGender() {
        EmployeeRepository repository = new EmployeeRepository();
        List<Employee> resultList = repository.findByGender("Male");

        assertAll(
                () -> Assert.assertEquals(3, resultList.size()),
                () -> Assert.assertEquals("Male", resultList.get(0).getGender())
        );
    }
}
