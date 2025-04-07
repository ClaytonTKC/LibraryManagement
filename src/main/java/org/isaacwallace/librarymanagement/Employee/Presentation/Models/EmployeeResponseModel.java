package org.isaacwallace.librarymanagement.Employee.Presentation.Models;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
public class EmployeeResponseModel extends RepresentationModel<EmployeeResponseModel> {
    private String employeeid;

    private String first_name;
    private String last_name;

    private LocalDate dob;
    private int age;

    private String email;
    private String title;
}