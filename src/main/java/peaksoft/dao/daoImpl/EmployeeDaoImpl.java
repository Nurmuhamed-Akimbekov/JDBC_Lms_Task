package peaksoft.dao.daoImpl;

import peaksoft.config.JdbcConfig;
import peaksoft.dao.EmployeeDao;
import peaksoft.model.Employee;
import peaksoft.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {
    private final Connection connection = JdbcConfig.getConnection();

    @Override
    public void createEmployee() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("create table if not exists employees(" +
                    "id serial primary key," +
                    "first_name varchar," +
                    "last_name varchar," +
                    "age int," +
                    "email varchar unique," +
                    "job_id int references jobs(id))");
            System.out.println("successfully created ");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addEmployee(Employee employee) {
        String sql = """
                insert into employees(first_name, last_name, age, email,job_id)
                values(?, ?, ?, ?, ?);""";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setLong(5, employee.getJobId());
            preparedStatement.executeUpdate();
            System.out.println("Successfully added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void dropTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                "drop table if exists employees;")) {
            preparedStatement.executeUpdate();
            int deleteTable = preparedStatement.executeUpdate();
            if (deleteTable > 0) {
                System.out.println("table successfully deleted");
            } else {
                System.out.println("table not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void cleanTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                "truncate table  employees")) {
            int executeUpdate = preparedStatement.executeUpdate();
            if (executeUpdate > 0) {
                System.out.println("table successfully cleaned");
            } else {
                System.out.println("table not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEmployee(Long id, Employee employee) {
        String sql = """
                update employees set first_name = ?,
                 last_name = ?, 
                 age = ?,
                 email = ?,
                 job_id = ? where id = ?
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setLong(5, employee.getJobId());
            preparedStatement.setLong(6, id);
            int empId =  preparedStatement.executeUpdate();
            if (empId>0){
            System.out.println("successfully updated");
            }else {
                throw new RuntimeException("employee by id "+id+" not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                "select *from employees")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getLong("job_id"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employees;
    }

    @Override
    public Employee findByEmail(String email) {
        Employee employee = new Employee();
        try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                "select * from employees where email =?")) {
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) throw new RuntimeException("employee by email "+email+" not found");
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getLong("job_id"));
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return employee;
    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        Map<Employee, Job> employeeJobMap = new HashMap<>();
        String sql = "select *, j.* from employees e inner join jobs j on e.job_id = j.id where e.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) throw new RuntimeException("employee id "+employeeId+" not found");
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getLong("job_id"));

                Job job = new Job();
                job.setPosition(resultSet.getString("position"));
                job.setProfession(resultSet.getString("profession"));
                job.setDescription(resultSet.getString("description"));
                job.setExperience(resultSet.getInt("experience"));
                job.setId(resultSet.getLong("job_id"));
                employeeJobMap.put(employee, job);

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return employeeJobMap;
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        List<Employee > employees = new ArrayList<>();
        String sql="select * from employees e inner join jobs j on j.id=e.job_id where j.position=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,position);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) throw new RuntimeException("employee by position "+position+" not found");
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getLong("job_id"));
                employees.add(employee);



        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return employees;
    }
}
