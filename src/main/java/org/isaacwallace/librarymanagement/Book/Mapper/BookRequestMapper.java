package org.isaacwallace.librarymanagement.Book.Mapper;

import org.isaacwallace.librarymanagement.Book.DataAccess.Book;
import org.isaacwallace.librarymanagement.Book.DataAccess.BookIdentifier;
import org.isaacwallace.librarymanagement.Book.Presentation.Models.BookRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookRequestMapper {

    @Mapping(target = "id", ignore = true)
    Book requestModelToEntity(BookRequestModel bookRequestModel, BookIdentifier bookIdentifier);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(BookRequestModel bookRequestModel, @MappingTarget Book book);
}
