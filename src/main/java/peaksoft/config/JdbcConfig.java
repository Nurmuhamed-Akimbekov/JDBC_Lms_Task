package peaksoft.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConfig {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "1234");
        } catch (SQLException e) {
           throw new RuntimeException(e.getMessage());
        }
    }

    //models:  Employee, Job
    //
    //Employee:
    //private Long id;
    //private String firstName;
    //private String lastName;
    //private int age;
    //private String email;
    //private int jobId;(reference)
    //
    //
    //Job:
    //private Long id;
    //private String position;("Mentor","Management","Instructor") ушундай маанилер берилсин
    //private String profession;("Java","JavaScript")
    //private String description;("Backend developer","Fronted developer")
    //private int experience;(1,2,3........) опыт работы
    //
    //
    //EmployeeDao:
    //
    //void createEmployee();
    //void addEmployee(Employee employee);
    //void dropTable();
    //void cleanTable();
    //void updateEmployee(Long id,Employee employee);
    //List<Employee>getAllEmployees();
    //Employee findByEmail(String email);
    //Map<Employee, Job> getEmployeeById(Long employeeId);
    //List<Employee> getEmployeeByPosition(String position);
    //
    //
    //JobDao:
    //
    //void createJobTable();
    //void addJob(Job job);
    //Job getJobById(Long jobId);
    //List<Job> sortByExperience(String ascOrDesc);
    //Job getJobByEmployeeId(Long employeeId);
    //void deleteDescriptionColumn();
}
