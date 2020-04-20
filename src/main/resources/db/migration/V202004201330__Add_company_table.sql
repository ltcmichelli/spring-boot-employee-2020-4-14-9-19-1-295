CREATE TABLE IF NOT EXISTS company(
    company_id int auto_increment not null,
    company_name varchar(30) not null,
    employees_number varchar(30) not null,
    PRIMARY KEY(company_id)
)