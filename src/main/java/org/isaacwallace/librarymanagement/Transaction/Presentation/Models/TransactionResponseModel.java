package org.isaacwallace.librarymanagement.Transaction.Presentation.Models;

import lombok.Data;
import org.isaacwallace.librarymanagement.Book.Presentation.Models.BookResponseModel;
import org.isaacwallace.librarymanagement.Employee.Presentation.Models.EmployeeResponseModel;
import org.isaacwallace.librarymanagement.Member.Presentation.Models.MemberResponseModel;
import org.isaacwallace.librarymanagement.Transaction.DataAccess.Payment;
import org.isaacwallace.librarymanagement.Transaction.DataAccess.TransactionStatus;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
public class TransactionResponseModel extends RepresentationModel<TransactionResponseModel> {
    private String transactionid;

    private String memberid;
    private String bookid;
    private String employeeid;

    private LocalDateTime transactionDate;

    private TransactionStatus status;

    private Payment payment;

    private BookResponseModel book;
    private MemberResponseModel member;
    private EmployeeResponseModel employee;
}
