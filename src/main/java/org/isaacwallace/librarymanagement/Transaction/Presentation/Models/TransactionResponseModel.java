package org.isaacwallace.librarymanagement.Transaction.Presentation.Models;

import lombok.Data;
import org.isaacwallace.librarymanagement.Book.DataAccess.BookIdentifier;
import org.isaacwallace.librarymanagement.Member.DataAccess.MemberIdentifier;
import org.isaacwallace.librarymanagement.Transaction.DataAccess.Payment;
import org.isaacwallace.librarymanagement.Transaction.DataAccess.TransactionStatus;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
public class TransactionResponseModel extends RepresentationModel<TransactionResponseModel> {
    private String transactionid;
    private String memberid;
    private String bookid;

    private LocalDateTime transactionDate;

    private TransactionStatus status;

    private Payment payment;
}
