package org.isaacwallace.librarymanagement.Transaction.Mapper;

import org.isaacwallace.librarymanagement.Member.DataAccess.Member;
import org.isaacwallace.librarymanagement.Transaction.DataAccess.Transaction;
import org.isaacwallace.librarymanagement.Transaction.DataAccess.TransactionIdentifier;
import org.isaacwallace.librarymanagement.Transaction.Presentation.Models.TransactionRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TransactionRequestMapper {
    @Mapping(target = "id", ignore = true)
    Transaction requestModelToEntity(TransactionRequestModel transactionRequestModel, TransactionIdentifier transactionIdentifier);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(TransactionRequestModel transactionRequestModel, @MappingTarget Transaction transaction);
}