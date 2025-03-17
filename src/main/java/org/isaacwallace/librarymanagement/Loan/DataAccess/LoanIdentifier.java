package org.isaacwallace.librarymanagement.Loan.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class LoanIdentifier {
    @Column(name = "loanId")
    public String loanId;

    public LoanIdentifier() {this.loanId = UUID.randomUUID().toString();}

    public LoanIdentifier(String loanId) {this.loanId = loanId;}
}
