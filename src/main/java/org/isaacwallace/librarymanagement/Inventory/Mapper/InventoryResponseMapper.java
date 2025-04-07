package org.isaacwallace.librarymanagement.Inventory.Mapper;

import org.isaacwallace.librarymanagement.Book.Presentation.BookController;
import org.isaacwallace.librarymanagement.Inventory.DataAccess.Inventory;
import org.isaacwallace.librarymanagement.Inventory.Presentation.InventoryController;
import org.isaacwallace.librarymanagement.Inventory.Presentation.Models.InventoryResponseModel;
import org.isaacwallace.librarymanagement.InventoryBooks.Presentation.InventoryBooksController;
import org.isaacwallace.librarymanagement.InventoryBooks.Presentation.Models.InventoryBooksResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface InventoryResponseMapper {
    @Mapping(expression = "java(inventory.getInventoryIdentifier().getInventoryid())", target = "inventoryid")
    InventoryResponseModel entityToResponseModel(Inventory inventory);
    List<InventoryResponseModel> entityToResponseModelList(List<Inventory> inventories);
    InventoryBooksResponseModel bookToAggregateResponseModel(Inventory inventory);

    @AfterMapping
    default void addLinks(@MappingTarget InventoryResponseModel inventoryResponseModel, Inventory inventory) {
        Link selfLink = linkTo(methodOn(InventoryController.class).getInventoryById(inventory.getInventoryIdentifier().getInventoryid())).withSelfRel();
        inventoryResponseModel.add(selfLink);

        Link allLink = linkTo(methodOn(InventoryController.class).getAllInventories()).withRel("inventories");
        inventoryResponseModel.add(allLink);

        Link bookLink = linkTo(methodOn(BookController.class).getBookById(inventory.getBookid())).withRel("book");
        inventoryResponseModel.add(bookLink);

        Link booksLink = linkTo(methodOn(InventoryBooksController.class).getAllInventories(inventory.getInventoryIdentifier().getInventoryid())).withRel("books");
        inventoryResponseModel.add(booksLink);
    }
}