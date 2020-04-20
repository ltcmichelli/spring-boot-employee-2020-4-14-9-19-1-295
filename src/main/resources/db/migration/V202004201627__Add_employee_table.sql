CREATE TABLE IF NOT EXISTS employee(
    employee_id int auto_increment not null,
    name varchar(30) not null,
    age int not null,
    gender varchar(30) not null,
    salary int not null,
    company_id int not null,
    parking_boy_id int not null,
    PRIMARY KEY(employee_id)
)