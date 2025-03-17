package org.isaacwallace.librarymanagement.Transaction.Presentation;

import org.isaacwallace.librarymanagement.Transaction.Business.TransactionService;
import org.isaacwallace.librarymanagement.Transaction.Presentation.Models.TransactionRequestModel;
import org.isaacwallace.librarymanagement.Transaction.Presentation.Models.TransactionResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("")
    public ResponseEntity<List<TransactionResponseModel>> getAllTransactions() {
        return ResponseEntity.status(HttpStatus.OK).body(this.transactionService.getAllTransactions());
    }

    @GetMapping("{transactionid}")
    public ResponseEntity<TransactionResponseModel> getTransactionById(@PathVariable String transactionid) {
        return ResponseEntity.status(HttpStatus.OK).body(this.transactionService.getTransactionById(transactionid));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponseModel> addMember(@RequestBody TransactionRequestModel transactionRequestModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.transactionService.addTransaction(transactionRequestModel));
    }

    @PutMapping(value = "{transactionid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponseModel> EditCustomer(@PathVariable String transactionid, @RequestBody TransactionRequestModel transactionRequestModel) {
        return ResponseEntity.status(HttpStatus.OK).body(this.transactionService.updateTransaction(transactionid, transactionRequestModel));
    }

    @DeleteMapping("{transactionid}")
    public ResponseEntity<TransactionResponseModel> DeleteCustomer(@PathVariable String transactionid) {
        this.transactionService.deleteTransaction(transactionid);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
