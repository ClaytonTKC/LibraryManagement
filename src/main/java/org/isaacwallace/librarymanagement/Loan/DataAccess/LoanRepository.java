package org.isaacwallace.librarymanagement.Loan.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    Loan findLoanByLoanIdentifier_LoanId(String loanId);
}
