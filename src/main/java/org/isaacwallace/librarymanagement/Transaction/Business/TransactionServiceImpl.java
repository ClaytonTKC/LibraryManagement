package org.isaacwallace.librarymanagement.Transaction.Business;

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

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    public TransactionServiceImpl(TransactionRepository tRepository, TransactionResponseMapper tResponseMapper, TransactionRequestMapper tRequestMapper) {
        this.transactionRepository = tRepository;
        this.transactionResponseMapper = tResponseMapper;
        this.transactionRequestMapper = tRequestMapper;
    }


    public List<TransactionResponseModel> getAllTransactions() {
        return this.transactionResponseMapper.entityToResponseModelList(transactionRepository.findAll());
    }

    public TransactionResponseModel getTransactionById(String transactionid) {
        Transaction transaction = this.transactionRepository.findTransactionByTransactionIdentifier_Transactionid(transactionid);

        if (transaction == null) {
            throw new NotFoundException("Unknown transaction id " + transactionid);
        }

        return this.transactionResponseMapper.entityToResponseModel(transaction);
    }

    public TransactionResponseModel addTransaction(TransactionRequestModel transactionRequestModel) {
        Transaction transaction = this.transactionRequestMapper.requestModelToEntity(transactionRequestModel, new TransactionIdentifier());

        return this.transactionResponseMapper.entityToResponseModel(this.transactionRepository.save(transaction));
    }

    public TransactionResponseModel updateTransaction(String transactionid, TransactionRequestModel transactionRequestModel) {
        Transaction transaction = this.transactionRepository.findTransactionByTransactionIdentifier_Transactionid(transactionid);

        if (transaction == null) {
            throw new NotFoundException("Unknown transaction id " + transactionid);
        }

        this.transactionRequestMapper.updateEntityFromRequest(transactionRequestModel, transaction);

        Transaction updatedTransaction = this.transactionRepository.save(transaction);

        logger.info("Updated transaction with id " + transactionid);

        return this.transactionResponseMapper.entityToResponseModel(updatedTransaction);
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
