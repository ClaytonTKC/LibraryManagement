package org.isaacwallace.librarymanagement.Employee.Business;

import org.isaacwallace.librarymanagement.Book.Business.BookServiceImpl;
import org.isaacwallace.librarymanagement.Employee.DataAccess.Employee;
import org.isaacwallace.librarymanagement.Employee.DataAccess.EmployeeIdentifier;
import org.isaacwallace.librarymanagement.Employee.DataAccess.EmployeeRepository;
import org.isaacwallace.librarymanagement.Employee.Mapper.EmployeeRequestMapper;
import org.isaacwallace.librarymanagement.Employee.Mapper.EmployeeResponseMapper;
import org.isaacwallace.librarymanagement.Employee.Presentation.Models.EmployeeRequestModel;
import org.isaacwallace.librarymanagement.Employee.Presentation.Models.EmployeeResponseModel;
import org.isaacwallace.librarymanagement.Utils.Exceptions.InUseException;
import org.isaacwallace.librarymanagement.Utils.Exceptions.InvalidInputException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeResponseMapper employeeResponseMapper;
    private final EmployeeRequestMapper employeeRequestMapper;

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeResponseMapper employeeResponseMapper, EmployeeRequestMapper employeeRequestMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeResponseMapper = employeeResponseMapper;
        this.employeeRequestMapper = employeeRequestMapper;
    }

    public List<EmployeeResponseModel> getAllEmployees() {
        return this.employeeResponseMapper.entityListToResponseModelList(this.employeeRepository.findAll());
    }

    public EmployeeResponseModel getEmployeeById(String employeeid) {
        Employee employee = this.employeeRepository.findEmployeeByEmployeeIdentifier_Employeeid(employeeid);

        if (employee == null) {
            throw new InvalidInputException("Unknown userId: " + employeeid);
        }

        return this.employeeResponseMapper.entityToResponseModel(employee);
    }

    public EmployeeResponseModel addEmployee(EmployeeRequestModel employeeRequestModel) {
        Employee newEmployee = this.employeeRequestMapper.requestModelToEntity(employeeRequestModel, new EmployeeIdentifier());

        return employeeResponseMapper.entityToResponseModel(this.employeeRepository.save(newEmployee));
    }

    public EmployeeResponseModel updateEmployee(String employeeid, EmployeeRequestModel employeeRequestModel) {
        Employee employee = this.employeeRepository.findEmployeeByEmployeeIdentifier_Employeeid(employeeid);

        if (employee == null) {
            throw new InvalidInputException("Unknown employeeid: " + employeeid);
        }

        this.employeeRequestMapper.updateEntityFromRequest(employeeRequestModel, employee);

        Employee updatedEmployee = this.employeeRepository.save(employee);

        logger.info("Updated employee with employeeid: " + employeeid);

        return this.employeeResponseMapper.entityToResponseModel(updatedEmployee);
    }

    public void deleteEmployee(String employeeid) {
        Employee employee = this.employeeRepository.findEmployeeByEmployeeIdentifier_Employeeid(employeeid);

        if (employee == null) {
            throw new InvalidInputException("Unknown employeeid: " + employeeid);
        }

        try {
            this.employeeRepository.delete(employee);
        } catch(DataIntegrityViolationException exception) {
            throw new InUseException("Employee is in use by another entity, currently cannot delete.");
        }
    }
}
