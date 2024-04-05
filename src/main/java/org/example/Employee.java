package org.example;

import java.time.LocalDate;

public record Employee(long employeeId, String firstName, String lastName, LocalDate employmentDate, double yearlySalary, PensionPlan pensionPlan) {}

