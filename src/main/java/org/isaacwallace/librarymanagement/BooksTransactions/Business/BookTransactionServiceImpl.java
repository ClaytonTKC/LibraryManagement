package org.isaacwallace.librarymanagement.BooksTransactions.Business;

import org.isaacwallace.librarymanagement.Book.DataAccess.Book;
import org.isaacwallace.librarymanagement.Book.DataAccess.BookRepository;
import org.isaacwallace.librarymanagement.Book.Mapper.BookResponseMapper;
import org.isaacwallace.librarymanagement.BooksTransactions.Presentation.Models.BookTransactionResponseModel;
import org.isaacwallace.librarymanagement.Transaction.DataAccess.TransactionRepository;
import org.isaacwallace.librarymanagement.Transaction.Mapper.TransactionResponseMapper;
import org.isaacwallace.librarymanagement.Transaction.Presentation.Models.TransactionResponseModel;
import org.isaacwallace.librarymanagement.Utils.Exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookTransactionServiceImpl implements BookTransactionService {
    private final BookRepository bookRepository;
    private final TransactionRepository transactionRepository;
    private final BookResponseMapper bookResponseMapper;
    private final TransactionResponseMapper transactionResponseMapper;

    public BookTransactionServiceImpl(BookRepository bookRepository, TransactionRepository transactionRepository, BookResponseMapper bookResponseMapper, TransactionResponseMapper transactionResponseMapper) {
        this.bookRepository = bookRepository;
        this.transactionRepository = transactionRepository;
        this.bookResponseMapper = bookResponseMapper;
        this.transactionResponseMapper = transactionResponseMapper;
    }

    public BookTransactionResponseModel getAllTransactionsByBookID(String bookid) {
        Book book = this.bookRepository.findBookByBookIdentifier_Bookid(bookid);

        if (book == null) {
            throw new NotFoundException("Unknown bookid: " + bookid);
        }

        List<TransactionResponseModel> transactions = this.transactionResponseMapper.entitiesToResponseModelList(this.transactionRepository.findTransactionsByBookid(bookid));
        BookTransactionResponseModel bookModel = this.bookResponseMapper.transactionToAggregateResponseModel(book);
        bookModel.setTransactions(transactions);
        bookModel.setBookid(bookid);

        return bookModel;
    }
}
