package org.isaacwallace.librarymanagement.Transaction.Business;

import org.isaacwallace.librarymanagement.Book.Presentation.Models.BookResponseModel;
import org.isaacwallace.librarymanagement.DomainClient.BookServiceClient;
import org.isaacwallace.librarymanagement.DomainClient.EmployeeServiceClient;
import org.isaacwallace.librarymanagement.DomainClient.MemberServiceClient;
import org.isaacwallace.librarymanagement.Employee.Presentation.Models.EmployeeResponseModel;
import org.isaacwallace.librarymanagement.Member.Presentation.Models.MemberResponseModel;
import org.isaacwallace.librarymanagement.Transaction.DataAccess.Transaction;
import org.isaacwallace.librarymanagement.Transaction.DataAccess.TransactionIdentifier;
import org.isaacwallace.librarymanagement.Transaction.DataAccess.TransactionRepository;
import org.isaacwallace.librarymanagement.Transaction.Mapper.TransactionRequestMapper;
import org.isaacwallace.librarymanagement.Transaction.Mapper.TransactionResponseMapper;
import org.isaacwallace.librarymanagement.Transaction.Presentation.Models.TransactionRequestModel;
import org.isaacwallace.librarymanagement.Transaction.Presentation.Models.TransactionResponseModel;
import org.isaacwallace.librarymanagement.Utils.Exceptions.InUseException;
import org.isaacwallace.librarymanagement.Utils.Exceptions.InvalidInputException;
import org.isaacwallace.librarymanagement.Utils.Exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionResponseMapper transactionResponseMapper;
    private final TransactionRequestMapper transactionRequestMapper;

    private final BookServiceClient bookServiceClient;
    private final MemberServiceClient memberServiceClient;
    private final EmployeeServiceClient employeeServiceClient;

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionResponseMapper transactionResponseMapper, TransactionRequestMapper transactionRequestMapper, BookServiceClient bookServiceClient, MemberServiceClient memberServiceClient, EmployeeServiceClient employeeServiceClient) {
        this.transactionRepository = transactionRepository;
        this.transactionResponseMapper = transactionResponseMapper;
        this.transactionRequestMapper = transactionRequestMapper;

        this.bookServiceClient = bookServiceClient;
        this.memberServiceClient = memberServiceClient;
        this.employeeServiceClient = employeeServiceClient;
    }

    private void validateBookInvariant(Transaction transaction) {
        if (transaction.getBookid() == null) {
            throw new InvalidInputException("Transaction must be associated with a book.");
        }

        if (transaction.getMemberid() == null) {
            throw new InvalidInputException("Transaction must be associated with a member.");
        }

        if (transaction.getEmployeeid() == null) {
            throw new InvalidInputException("Transaction must be associated with an employee.");
        }
    }

    public List<TransactionResponseModel> getAllTransactions() {
        return this.transactionResponseMapper.entitiesToResponseModelList(transactionRepository.findAll(), bookServiceClient, memberServiceClient, employeeServiceClient);
    }

    public TransactionResponseModel getTransactionById(String transactionid) {
        Transaction transaction = this.transactionRepository.findTransactionByTransactionIdentifier_Transactionid(transactionid);

        if (transaction == null) {
            throw new NotFoundException("Unknown transaction id " + transactionid);
        }

        return this.transactionResponseMapper.entityToResponseModel(transaction, bookServiceClient, memberServiceClient, employeeServiceClient);
    }

    public TransactionResponseModel addTransaction(TransactionRequestModel transactionRequestModel) {
        BookResponseModel book = this.bookServiceClient.getBookByBookId(transactionRequestModel.getBookid());

        if (book == null) {
            throw new NotFoundException("Unknown bookid: " + transactionRequestModel.getBookid());
        }

        MemberResponseModel member = this.memberServiceClient.getMemberByMemberId(transactionRequestModel.getMemberid());

        if (member == null) {
            throw new NotFoundException("Unknown memberid: " + transactionRequestModel.getMemberid());
        }

        EmployeeResponseModel employee = this.employeeServiceClient.getEmployeeByEmployeeid(transactionRequestModel.getEmployeeid());

        if (employee == null) {
            throw new NotFoundException("Unknown employeeid: " + transactionRequestModel.getEmployeeid());
        }

        Transaction transaction = this.transactionRequestMapper.requestModelToEntity(transactionRequestModel, new TransactionIdentifier());

        this.validateBookInvariant(transaction);

        return this.transactionResponseMapper.entityToResponseModel(this.transactionRepository.save(transaction), bookServiceClient, memberServiceClient, employeeServiceClient);
    }

    public TransactionResponseModel updateTransaction(String transactionid, TransactionRequestModel transactionRequestModel) {
        BookResponseModel book = this.bookServiceClient.getBookByBookId(transactionRequestModel.getBookid());

        if (book == null) {
            throw new NotFoundException("Unknown bookid: " + transactionRequestModel.getBookid());
        }

        MemberResponseModel member = this.memberServiceClient.getMemberByMemberId(transactionRequestModel.getMemberid());

        if (member == null) {
            throw new NotFoundException("Unknown memberid: " + transactionRequestModel.getMemberid());
        }

        EmployeeResponseModel employee = this.employeeServiceClient.getEmployeeByEmployeeid(transactionRequestModel.getEmployeeid());

        if (employee == null) {
            throw new NotFoundException("Unknown employeeid: " + transactionRequestModel.getEmployeeid());
        }

        Transaction transaction = this.transactionRepository.findTransactionByTransactionIdentifier_Transactionid(transactionid);

        if (transaction == null) {
            throw new NotFoundException("Unknown transaction id " + transactionid);
        }

        this.transactionRequestMapper.updateEntityFromRequest(transactionRequestModel, transaction);

        Transaction updatedTransaction = this.transactionRepository.save(transaction);

        this.validateBookInvariant(transaction);

        logger.info("Updated transaction with id " + transactionid);

        return this.transactionResponseMapper.entityToResponseModel(updatedTransaction, bookServiceClient, memberServiceClient, employeeServiceClient);
    }

    public void deleteTransaction(String transactionid) {
        Transaction transaction = this.transactionRepository.findTransactionByTransactionIdentifier_Transactionid(transactionid);

        if (transaction == null) {
            throw new NotFoundException("Unknown transaction id " + transactionid);
        }

        try {
            this.transactionRepository.delete(transaction);
            logger.info("Deleted transaction with id " + transactionid);
        } catch (DataIntegrityViolationException exception) {
            logger.error("Failed to delete transaction with id " + transactionid, exception.getMessage());
            throw new InUseException("Transaction with id " + transactionid + " is already in use by another entity, currently cannot delete.");
        }
    }
}
