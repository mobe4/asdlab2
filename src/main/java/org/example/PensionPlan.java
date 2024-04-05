package org.example;

import java.time.LocalDate;

public record PensionPlan(String planReferenceNumber, LocalDate enrollmentDate, double monthlyContribution) {}
