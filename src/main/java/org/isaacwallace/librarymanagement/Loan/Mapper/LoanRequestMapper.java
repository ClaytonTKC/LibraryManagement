package org.isaacwallace.librarymanagement.Loan.Mapper;

import org.isaacwallace.librarymanagement.Loan.DataAccess.Loan;
import org.isaacwallace.librarymanagement.Loan.Presentation.Models.LoanRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanRequestMapper {
    @Mapping(target = "id", ignore = true)
    Loan requestModelToEntity(LoanRequestModel loanRequestModel);
}
