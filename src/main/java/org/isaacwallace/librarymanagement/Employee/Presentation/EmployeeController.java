package org.isaacwallace.librarymanagement.Employee.Presentation;

import org.isaacwallace.librarymanagement.Employee.Business.EmployeeService;
import org.isaacwallace.librarymanagement.Employee.Presentation.Models.EmployeeRequestModel;
import org.isaacwallace.librarymanagement.Employee.Presentation.Models.EmployeeResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    public ResponseEntity<List<EmployeeResponseModel>> GetEmployees() {
        return ResponseEntity.status(HttpStatus.OK).body(this.employeeService.getAllEmployees());
    }

    @GetMapping("{employeeid}")
    public ResponseEntity<EmployeeResponseModel> GetEmployee(@PathVariable String employeeid) {
        return ResponseEntity.status(HttpStatus.OK).body(this.employeeService.getEmployeeById(employeeid));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponseModel> AddUser(@RequestBody EmployeeRequestModel employeeRequestModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.employeeService.addEmployee(employeeRequestModel));
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity DeleteEmployee(@RequestBody String employeeId) {
        this.employeeService.deleteEmployee(employeeId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PutMapping(value = "{employeeid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponseModel> EditPost(@PathVariable String employeeid, @RequestBody EmployeeRequestModel employeeRequestModel) {
        return ResponseEntity.status(HttpStatus.OK).body(this.employeeService.editEmployee(employeeid, employeeRequestModel));
    }
}
