package org.isaacwallace.librarymanagement.Transaction.Presentation.Models;

import lombok.*;
import org.isaacwallace.librarymanagement.Book.DataAccess.BookIdentifier;
import org.isaacwallace.librarymanagement.Employee.DataAccess.EmployeeIdentifier;
import org.isaacwallace.librarymanagement.Member.DataAccess.MemberIdentifier;
import org.isaacwallace.librarymanagement.Transaction.DataAccess.Payment;
import org.isaacwallace.librarymanagement.Transaction.DataAccess.TransactionStatus;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Value
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionRequestModel extends RepresentationModel<TransactionRequestModel> {
    String memberid;
    String bookid;
    String employeeid;

    LocalDateTime transactionDate;

    TransactionStatus status;

    Payment payment;
}
