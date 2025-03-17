package org.isaacwallace.librarymanagement.Loan.Mapper;

import org.isaacwallace.librarymanagement.Loan.DataAccess.Loan;
import org.isaacwallace.librarymanagement.Loan.Presentation.Models.LoanResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanResponseMapper {
    @Mapping()
    LoanResponseModel entityToResponseModel(Loan loan);
}
