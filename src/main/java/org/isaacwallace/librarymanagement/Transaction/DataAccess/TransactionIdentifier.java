package org.isaacwallace.librarymanagement.Transaction.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class TransactionIdentifier {
    @Column(name = "transactionid")
    private String transactionid;

    public TransactionIdentifier() {
        this.transactionid = UUID.randomUUID().toString();
    }

    public TransactionIdentifier(String transactionid) {
        this.transactionid = transactionid;
    }
}
