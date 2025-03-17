package org.isaacwallace.librarymanagement.Transaction.Business;

import org.isaacwallace.librarymanagement.Transaction.Presentation.Models.TransactionRequestModel;
import org.isaacwallace.librarymanagement.Transaction.Presentation.Models.TransactionResponseModel;

import java.util.List;

public interface TransactionService {
    public List<TransactionResponseModel> getAllTransactions();
    public TransactionResponseModel getTransactionById(String transactionid);
    public TransactionResponseModel addTransaction(TransactionRequestModel transactionRequestModel);
    public TransactionResponseModel updateTransaction(String transactionid, TransactionRequestModel transactionRequestModel);
    public void deleteTransaction(String transactionid);
}
