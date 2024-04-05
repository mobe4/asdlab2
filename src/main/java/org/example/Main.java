package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    private static List<Employee> employees = new ArrayList<>();

    public static void main(String[] args) {
        initializeEmployees();
        printAllEmployees();
        printUpcomingEnrollees();
    }

    private static void initializeEmployees() {
        Employee employee1 = new Employee(1, "Daniel", "Agar", LocalDate.of(2018, 1, 17), 105945.50, new PensionPlan("EX1089", LocalDate.of(2023, 1, 17), 100.00));
        Employee employee2 = new Employee(2, "Benard", "Shaw", LocalDate.of(2019, 4, 3), 197750.00, null);
        Employee employee3 = new Employee(3, "Carly", "Agar", LocalDate.of(2014, 5, 16), 842000.75, new PensionPlan("SM2307", LocalDate.of(2019, 11, 4), 1555.50));

        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
    }

    private static void printAllEmployees() {
        employees.stream()
                .sorted(Comparator.comparing(Employee::lastName)
                        .thenComparing(Employee::yearlySalary, Comparator.reverseOrder()))
                .forEach(employee -> System.out.println(employeeToJson(employee)));
    }

    private static String employeeToJson(Employee employee) {
        return String.format("{\"employeeId\": %d, \"firstName\": \"%s\", \"lastName\": \"%s\", \"employmentDate\": \"%s\", \"yearlySalary\": %.2f, \"pensionPlan\": %s}",
                employee.employeeId(), employee.firstName(), employee.lastName(), employee.employmentDate(), employee.yearlySalary(),
                employee.pensionPlan() == null ? "null" : pensionPlanToJson(employee.pensionPlan()));
    }

    private static String pensionPlanToJson(PensionPlan pensionPlan) {
        return String.format("{\"planReferenceNumber\": \"%s\", \"enrollmentDate\": \"%s\", \"monthlyContribution\": %.2f}",
                pensionPlan.planReferenceNumber(), pensionPlan.enrollmentDate(), pensionPlan.monthlyContribution());
    }

    private static void printUpcomingEnrollees() {
        LocalDate nextMonth = LocalDate.now().plusMonths(1);
        LocalDate startOfNextMonth = LocalDate.of(nextMonth.getYear(), nextMonth.getMonth(), 1);
        LocalDate endOfNextMonth = startOfNextMonth.plusMonths(1).minusDays(1);

        employees.stream()
                .filter(employee -> employee.pensionPlan() == null)
                .filter(employee -> {
                    LocalDate eligibilityDate = employee.employmentDate().plusYears(5);
                    return (eligibilityDate.isAfter(startOfNextMonth) || eligibilityDate.isEqual(startOfNextMonth))
                            && eligibilityDate.isBefore(endOfNextMonth.plusDays(1));
                })
                .sorted(Comparator.comparing(Employee::employmentDate))
                .forEach(employee -> System.out.println(employeeToJson(employee)));
    }


}
