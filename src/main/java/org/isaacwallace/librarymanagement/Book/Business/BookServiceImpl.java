package org.isaacwallace.librarymanagement.Book.Business;

import org.isaacwallace.librarymanagement.Book.DataAccess.Book;
import org.isaacwallace.librarymanagement.Book.DataAccess.BookIdentifier;
import org.isaacwallace.librarymanagement.Book.DataAccess.BookRepository;
import org.isaacwallace.librarymanagement.Book.Mapper.BookRequestMapper;
import org.isaacwallace.librarymanagement.Book.Mapper.BookResponseMapper;
import org.isaacwallace.librarymanagement.Book.Presentation.Models.BookRequestModel;
import org.isaacwallace.librarymanagement.Book.Presentation.Models.BookResponseModel;
import org.isaacwallace.librarymanagement.Utils.Exceptions.InUseException;
import org.isaacwallace.librarymanagement.Utils.Exceptions.InvalidInputException;
import org.isaacwallace.librarymanagement.Utils.Exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookResponseMapper bookResponseMapper;
    private final BookRequestMapper bookRequestMapper;

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    public BookServiceImpl(BookRepository bookRepository, BookResponseMapper bookResponseMapper, BookRequestMapper bookRequestMapper) {
        this.bookRepository = bookRepository;
        this.bookResponseMapper = bookResponseMapper;
        this.bookRequestMapper = bookRequestMapper;
    }

    private void validateBookInvariant(Book book) {
        if (book.getInventoryid() == null || book.getInventoryid().isEmpty()) {
            throw new InvalidInputException("Book must be associated with an inventory.");
        }

        if ("available".equalsIgnoreCase(book.getAvailability().toString()) && book.getMemberid() != null) {
            throw new InvalidInputException("Available books should not be assigned to a member.");
        }
    }

    public List<BookResponseModel> getAllBooks() {
        return this.bookResponseMapper.entityToResponseModelList(this.bookRepository.findAll());
    }

    public BookResponseModel getBookById(String bookid) {
        Book book = this.bookRepository.findBookByBookIdentifier_Bookid(bookid);

        if (book == null) {
            throw new NotFoundException("Unknown bookid: " + bookid);
        }

        return this.bookResponseMapper.entityToResponseModel(book);
    }

    public BookResponseModel addBook(BookRequestModel bookRequestModel) {
        Book book = this.bookRequestMapper.requestModelToEntity(bookRequestModel, new BookIdentifier());

        this.validateBookInvariant(book);

        return this.bookResponseMapper.entityToResponseModel(this.bookRepository.save(book));
    }

    public BookResponseModel updateBook(String bookid, BookRequestModel bookRequestModel) {
        Book book = this.bookRepository.findBookByBookIdentifier_Bookid(bookid);

        if (book == null) {
            throw new NotFoundException("Unknown bookid: " + bookid);
        }

        this.bookRequestMapper.updateEntityFromRequest(bookRequestModel, book);

        this.validateBookInvariant(book);

        Book updatedBook = this.bookRepository.save(book);

        logger.info("Updated book with bookid: " + bookid);

        return this.bookResponseMapper.entityToResponseModel(updatedBook);
    }

    public void deleteBook(String bookid) {
        Book book = this.bookRepository.findBookByBookIdentifier_Bookid(bookid);

        if (book == null) {
            throw new NotFoundException("Unknown bookid: " + bookid);
        }

        try {
            this.bookRepository.delete(book);
            logger.info("Book with id: " + bookid + " has been deleted");
        } catch (DataIntegrityViolationException exception) {
            logger.error("Failed to delete book with id: " + bookid, exception.getMessage());
            throw new InUseException("Book with id: " + bookid + " is already in use by another entity, currently cannot delete.");
        }
    }
}
