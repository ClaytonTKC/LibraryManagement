package org.isaacwallace.librarymanagement.Transaction.Mapper;

import org.isaacwallace.librarymanagement.Book.DataAccess.Book;
import org.isaacwallace.librarymanagement.Book.Presentation.BookController;
import org.isaacwallace.librarymanagement.Book.Presentation.Models.BookResponseModel;
import org.isaacwallace.librarymanagement.DomainClient.BookServiceClient;
import org.isaacwallace.librarymanagement.DomainClient.EmployeeServiceClient;
import org.isaacwallace.librarymanagement.DomainClient.MemberServiceClient;
import org.isaacwallace.librarymanagement.Employee.Presentation.EmployeeController;
import org.isaacwallace.librarymanagement.Member.Presentation.MemberController;
import org.isaacwallace.librarymanagement.Transaction.DataAccess.Transaction;
import org.isaacwallace.librarymanagement.Transaction.Presentation.Models.TransactionResponseModel;
import org.isaacwallace.librarymanagement.Transaction.Presentation.TransactionController;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface TransactionResponseMapper {
    @Mapping(expression = "java(transaction.getTransactionIdentifier().getTransactionid())", target = "transactionid")
    @Mapping(expression = "java(bookService.getBookByBookId(transaction.getBookid()))", target = "book")
    @Mapping(expression = "java(memberService.getMemberByMemberId(transaction.getMemberid()))", target = "member")
    @Mapping(expression = "java(employeeService.getEmployeeByEmployeeid(transaction.getEmployeeid()))", target = "employee")
    TransactionResponseModel entityToResponseModel(Transaction transaction, BookServiceClient bookService, MemberServiceClient memberService, EmployeeServiceClient employeeService);

    default List<TransactionResponseModel> entityToResponseModelList(Transaction transaction, BookServiceClient bookService, MemberServiceClient memberService, EmployeeServiceClient employeeService) {
        List<TransactionResponseModel> t = new ArrayList<>();
        t.add(entityToResponseModel(transaction, bookService, memberService, employeeService));
        return t;
    }

    default List<TransactionResponseModel> entitiesToResponseModelList(List<Transaction> transactions, BookServiceClient bookService, MemberServiceClient memberService, EmployeeServiceClient employeeService) {
        return transactions.stream()
                .map(transaction -> entityToResponseModel(transaction, bookService, memberService, employeeService))
                .toList();
    }

    @AfterMapping
    default void mapResponseFields(@MappingTarget TransactionResponseModel transactionResponseModel, Transaction transaction) {
        transactionResponseModel.setStatus(transaction.getStatus());
    }

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

        Link employeeLink = linkTo(methodOn(EmployeeController.class).getEmployee(transaction.getEmployeeid())).withRel("employee");
        transactionResponseModel.add(employeeLink);
    }
}
