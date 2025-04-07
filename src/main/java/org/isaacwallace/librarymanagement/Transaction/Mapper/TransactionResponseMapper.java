package org.isaacwallace.librarymanagement.Transaction.Mapper;

import org.isaacwallace.librarymanagement.Book.Presentation.BookController;
import org.isaacwallace.librarymanagement.Member.Presentation.MemberController;
import org.isaacwallace.librarymanagement.Transaction.DataAccess.Transaction;
import org.isaacwallace.librarymanagement.Transaction.Presentation.Models.TransactionResponseModel;
import org.isaacwallace.librarymanagement.Transaction.Presentation.TransactionController;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface TransactionResponseMapper {
    @Mapping(expression = "java(transaction.getTransactionIdentifier().getTransactionid())", target = "transactionid")
    TransactionResponseModel entityToResponseModel(Transaction transaction);
    List<TransactionResponseModel> entityToResponseModelList(List<Transaction> transactions);

    @AfterMapping
    default void addLinks(@MappingTarget TransactionResponseModel transactionResponseModel, Transaction transaction) {
        Link selfLink = linkTo(methodOn(TransactionController.class).getTransactionById(transaction.getTransactionIdentifier().getTransactionid())).withSelfRel();
        transactionResponseModel.add(selfLink);

        Link allLink = linkTo(methodOn(TransactionController.class).getAllTransactions()).withRel("transactions");
        transactionResponseModel.add(allLink);

        Link memberLink = linkTo(methodOn(MemberController.class).getMemberById(transaction.getMemberid())).withRel("member");
        transactionResponseModel.add(memberLink);

        Link bookLink = linkTo(methodOn(BookController.class).getBookById(transaction.getBookid())).withRel("book");
        transactionResponseModel.add(bookLink);
    }
}
