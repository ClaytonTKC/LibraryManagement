package org.isaacwallace.librarymanagement.BooksTransactions.Business;

import org.isaacwallace.librarymanagement.BooksTransactions.Presentation.Models.BookTransactionResponseModel;
import org.isaacwallace.librarymanagement.InventoryBooks.Presentation.Models.InventoryBooksResponseModel;

public interface BookTransactionService {
    public BookTransactionResponseModel getAllTransactionsByBookID(String bookid);
}
