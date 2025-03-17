package org.isaacwallace.librarymanagement.Author.Mapper;

import org.isaacwallace.librarymanagement.Author.DataAccess.Author;
import org.isaacwallace.librarymanagement.Author.Presentation.AuthorController;
import org.isaacwallace.librarymanagement.Author.Presentation.Models.AuthorRequestModel;
import org.isaacwallace.librarymanagement.Author.Presentation.Models.AuthorResponseModel;
import org.isaacwallace.librarymanagement.Member.DataAccess.Member;
import org.isaacwallace.librarymanagement.Member.Presentation.MemberController;
import org.isaacwallace.librarymanagement.Member.Presentation.Models.MemberResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface AuthorResponseMapper {
    @Mapping(expression = "java(author.getAuthorIdentifier().getAuthorid())", target = "authorId")
    AuthorResponseModel entityToResponseModel(Author author);
    List<AuthorResponseModel> entityToResponseModelList(List<Author> authors);

    @AfterMapping
    default void addLinks(@MappingTarget AuthorResponseModel authorResponseModel, Author author) {
        Link selfLink = linkTo(methodOn(AuthorController.class).getAuthorById(author.getAuthorIdentifier().getAuthorid())).withSelfRel();
        authorResponseModel.add(selfLink);
    }

}
