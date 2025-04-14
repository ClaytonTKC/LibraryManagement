package org.isaacwallace.librarymanagement.Employee.Presentation.Models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.isaacwallace.librarymanagement.Employee.DataAccess.EmployeeTitle;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeRequestModel extends RepresentationModel<EmployeeRequestModel> {
    String first_name;
    String last_name;

    LocalDate dob;

    String email;
    EmployeeTitle title;
    Double salary;
}