package org.isaacwallace.librarymanagement.Employee.Mapper;

import org.isaacwallace.librarymanagement.Employee.DataAccess.Employee;
import org.isaacwallace.librarymanagement.Employee.DataAccess.EmployeeIdentifier;
import org.isaacwallace.librarymanagement.Employee.Presentation.Models.EmployeeRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeRequestMapper {
    @Mapping(target = "id", ignore = true)
    Employee requestModelToEntity(EmployeeRequestModel employeeRequestModel, EmployeeIdentifier postIdentifier);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(EmployeeRequestModel employeeRequestModel, @MappingTarget Employee employee);
}
