package org.isaacwallace.librarymanagement.Employee.Business;


import org.isaacwallace.librarymanagement.Employee.Presentation.Models.EmployeeRequestModel;
import org.isaacwallace.librarymanagement.Employee.Presentation.Models.EmployeeResponseModel;

import java.util.List;

public interface EmployeeService {
    public List<EmployeeResponseModel> getAllEmployees();
    public EmployeeResponseModel getEmployeeById(String employeeid);
    public EmployeeResponseModel addEmployee(EmployeeRequestModel employeeRequestModel);
    public EmployeeResponseModel editEmployee(String employeeid, EmployeeRequestModel employeeRequestModel);
    public void deleteEmployee(String employeeid);
}
