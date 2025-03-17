package org.isaacwallace.librarymanagement.Inventory.Mapper;

import org.isaacwallace.librarymanagement.Inventory.DataAccess.Inventory;
import org.isaacwallace.librarymanagement.Inventory.Presentation.InventoryController;
import org.isaacwallace.librarymanagement.Inventory.Presentation.Models.InventoryResponseModel;
import org.isaacwallace.librarymanagement.Member.Presentation.MemberController;
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

    @AfterMapping
    default void addLinks(@MappingTarget InventoryResponseModel inventoryResponseModel, Inventory inventory) {
        Link selfLink = linkTo(methodOn(InventoryController.class).getInventoryById(inventory.getInventoryIdentifier().getInventoryid())).withSelfRel();
        inventoryResponseModel.add(selfLink);
    }
}