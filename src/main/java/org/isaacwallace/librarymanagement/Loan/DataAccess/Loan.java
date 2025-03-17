package org.isaacwallace.librarymanagement.Loan.DataAccess;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@Embeddable
public class Loan {
    private LocalDateTime loanDate;
    private LocalDateTime dueDate;
    private String status;
    private Long memberId;
    private String isbnNumber;

}
