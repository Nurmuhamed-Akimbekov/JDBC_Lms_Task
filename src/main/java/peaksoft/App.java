package peaksoft;

import peaksoft.model.Employee;
import peaksoft.model.Job;
import peaksoft.service.EmployeeService;
import peaksoft.service.JobService;
import peaksoft.service.serviceImpl.EmployeeServiceImpl;
import peaksoft.service.serviceImpl.JobServiceImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        JobService jobService = new JobServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();

        while (true) {
            System.out.println("""
                    1. create table jobs
                    2. add jobs
                    3. find job by id
                    4. sort by experience
                    5. get job by employee id
                    6. delete column description
                    7. drop table jobs
                    8. create table employees
                    9. add employee
                    10. update employee
                    11. get all employees
                    12. get by email
                    13. get by employee id
                    14. get employee by position
                    15. clean table employee
                    16. drop table employee""");
            try {
                switch (new Scanner(System.in).nextInt()) {
                    case 1 -> jobService.createJobTable();
                    case 2 -> {
                        Job job = new Job();
                        System.out.println("write position");
                        job.setPosition(new Scanner(System.in).nextLine());
                        System.out.println("write profession");
                        job.setProfession(new Scanner(System.in).nextLine());
                        System.out.println("write description");
                        job.setDescription(new Scanner(System.in).nextLine());
                        System.out.println("write experience");
                        job.setExperience(new Scanner(System.in).nextInt());
                        jobService.addJob(job);
                    }
                    case 3 -> {
                        System.out.println("write job id");
                        System.out.println(jobService.getJobById(new Scanner(System.in).nextLong()));
                    }
                    case 4 -> {
                        System.out.println("write 'asc' or 'desc' ");
                        System.out.println(jobService.sortByExperience(new Scanner(System.in).nextLine()));
                    }
                    case 5 -> {
                        System.out.println("write employee id");
                        System.out.println(jobService.getJobByEmployeeId(new Scanner(System.in).nextLong()));
                    }
                    case 6 -> jobService.deleteDescriptionColumn();
                    case 7 -> jobService.dropTable();
                    case 8 -> employeeService.createEmployee();
                    case 9 -> {
                        Employee employee = new Employee();
                        System.out.println("write first name ");
                        employee.setFirstName(new Scanner(System.in).nextLine());
                        System.out.println("write last name");
                        employee.setLastName(new Scanner(System.in).nextLine());
                        System.out.println("write age");
                        employee.setAge(new Scanner(System.in).nextInt());
                        System.out.println("write email");
                        employee.setEmail(new Scanner(System.in).nextLine());
                        System.out.println("write job id");
                        employee.setJobId(new Scanner(System.in).nextLong());
                        employeeService.addEmployee(employee);
                    }
                    case 10 -> {
                        System.out.println("write employee id");
                        Long id = new Scanner(System.in).nextLong();
                        Employee employee = new Employee();
                        System.out.println("write first name ");
                        employee.setFirstName(new Scanner(System.in).nextLine());
                        System.out.println("write last name");
                        employee.setLastName(new Scanner(System.in).nextLine());
                        System.out.println("write age");
                        employee.setAge(new Scanner(System.in).nextInt());
                        System.out.println("write email");
                        employee.setEmail(new Scanner(System.in).nextLine());
                        System.out.println("write job id");
                        employee.setJobId(new Scanner(System.in).nextLong());
                        employeeService.addEmployee(employee);
                        employeeService.updateEmployee(id, employee);
                    }
                    case 11 -> System.out.println(employeeService.getAllEmployees());
                    case 12 -> {
                        System.out.println("write email");
                        System.out.println(employeeService.findByEmail(new Scanner(System.in).nextLine()));
                    }
                    case 13 -> {
                        System.out.println("write employee id");
                        System.out.println(employeeService.getEmployeeById(new Scanner(System.in).nextLong()));
                    }
                    case 14 -> {
                        System.out.println("write position");
                        System.out.println(employeeService.getEmployeeByPosition(new Scanner(System.in).nextLine()));
                    }
                    case 15-> employeeService.cleanTable();
                    case 16->employeeService.dropTable();
                    default -> System.out.println("write correctly");
                }
            }catch (InputMismatchException e){
                System.out.println("write number");
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }

        }

    }
}
