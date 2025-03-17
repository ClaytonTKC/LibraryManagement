package org.isaacwallace.librarymanagement.Transaction.Presentation.Models;

import lombok.*;
import org.isaacwallace.librarymanagement.Book.DataAccess.BookIdentifier;
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
    LocalDateTime transactionDate;

    MemberIdentifier memberIdentifier;

    BookIdentifier bookIdentifier;

    TransactionStatus status;

    Payment payment;
}
