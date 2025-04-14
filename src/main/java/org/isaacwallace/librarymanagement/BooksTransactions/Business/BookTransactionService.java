package org.isaacwallace.librarymanagement.BooksTransactions.Business;

import org.isaacwallace.librarymanagement.BooksTransactions.Presentation.Models.BookTransactionResponseModel;

public interface BookTransactionService {
    public BookTransactionResponseModel getAllTransactionsByBookID(String bookid);
}
