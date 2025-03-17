package org.isaacwallace.librarymanagement.Transaction.DataAccess;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.isaacwallace.librarymanagement.Book.DataAccess.BookIdentifier;
import org.isaacwallace.librarymanagement.Member.DataAccess.MemberIdentifier;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private TransactionIdentifier transactionIdentifier;

    private String memberid;
    private String bookid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Embedded
    private Payment payment;
}
