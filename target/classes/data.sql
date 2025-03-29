-- Insert default manager
INSERT INTO employees (first_name, last_name, email, password, role, annual_leave_balance, sick_leave_balance, personal_leave_balance)
VALUES ('Admin', 'User', 'admin@example.com', '$2a$10$rDkPvvAFV6GgJkKqX3.3/.KqX3.3/.KqX3.3/.KqX3.3/.KqX3.3/.', 'ROLE_MANAGER', 20, 10, 5);

-- Insert sample employee
INSERT INTO employees (first_name, last_name, email, password, role, annual_leave_balance, sick_leave_balance, personal_leave_balance)
VALUES ('John', 'Doe', 'john@example.com', '$2a$10$rDkPvvAFV6GgJkKqX3.3/.KqX3.3/.KqX3.3/.KqX3.3/.KqX3.3/.', 'ROLE_EMPLOYEE', 20, 10, 5); 