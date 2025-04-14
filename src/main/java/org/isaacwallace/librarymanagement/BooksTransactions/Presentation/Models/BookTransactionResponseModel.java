package org.isaacwallace.librarymanagement.BooksTransactions.Presentation.Models;

import lombok.Data;
import org.isaacwallace.librarymanagement.Book.DataAccess.BookStatus;
import org.isaacwallace.librarymanagement.Transaction.Presentation.Models.TransactionResponseModel;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookTransactionResponseModel extends RepresentationModel<BookTransactionResponseModel> {
    private String bookid;
    private String authorid;
    private String inventoryid;
    private String memberid;

    private String title;
    private String genre;
    private String publisher;

    private LocalDateTime released;

    private BookStatus availability;

    private List<TransactionResponseModel> transactions;
}
