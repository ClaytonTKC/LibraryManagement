package org.isaacwallace.librarymanagement.Author.Mapper;

import org.isaacwallace.librarymanagement.Author.DataAccess.Author;
import org.isaacwallace.librarymanagement.Author.DataAccess.AuthorIdentifier;
import org.isaacwallace.librarymanagement.Author.Presentation.Models.AuthorRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorRequestMapper {
    @Mapping(target = "id", ignore = true)
    Author requestModelToEntity(AuthorRequestModel authorRequestModel, AuthorIdentifier authorIdentifier);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(AuthorRequestModel authorRequestModel, @MappingTarget Author author);
}
