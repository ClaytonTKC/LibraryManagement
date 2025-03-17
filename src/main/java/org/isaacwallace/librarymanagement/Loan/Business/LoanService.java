package org.isaacwallace.librarymanagement.Loan.Business;

import org.isaacwallace.librarymanagement.Loan.Presentation.Models.LoanResponseModel;

import java.util.List;

public interface LoanService {
    public List<LoanResponseModel> getAllLoans();
}
