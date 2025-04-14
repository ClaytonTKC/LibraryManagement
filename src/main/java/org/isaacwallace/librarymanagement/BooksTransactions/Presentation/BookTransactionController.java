package org.isaacwallace.librarymanagement.BooksTransactions.Presentation;

import org.isaacwallace.librarymanagement.BooksTransactions.Business.BookTransactionService;
import org.isaacwallace.librarymanagement.BooksTransactions.Presentation.Models.BookTransactionResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/books/{bookid}/transactions")
public class BookTransactionController {
    private final BookTransactionService bookTransactionService;

    public BookTransactionController(BookTransactionService bookTransactionService) {
        this.bookTransactionService = bookTransactionService;
    }

    @GetMapping("")
    public ResponseEntity<BookTransactionResponseModel> getAllTransactions(@PathVariable String bookid) {
        return ResponseEntity.status(HttpStatus.OK).body(this.bookTransactionService.getAllTransactionsByBookID(bookid));
    }
}