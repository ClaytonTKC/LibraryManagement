package org.isaacwallace.librarymanagement.Loan.Presentation.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoanResponseModel {
    private LocalDateTime loanDate;
    private LocalDateTime dueDate;
    private String status;

}
