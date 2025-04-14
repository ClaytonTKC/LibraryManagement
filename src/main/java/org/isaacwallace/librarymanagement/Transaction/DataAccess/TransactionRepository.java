package org.isaacwallace.librarymanagement.Transaction.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    Transaction findTransactionByTransactionIdentifier_Transactionid(String transactionid);
    List<Transaction> findTransactionsByBookid(String bookid);

}
