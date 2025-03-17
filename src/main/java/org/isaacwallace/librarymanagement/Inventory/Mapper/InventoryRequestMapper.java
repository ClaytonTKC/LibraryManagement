package org.isaacwallace.librarymanagement.Inventory.Mapper;

import org.isaacwallace.librarymanagement.Inventory.DataAccess.Inventory;
import org.isaacwallace.librarymanagement.Inventory.DataAccess.InventoryIdentifier;
import org.isaacwallace.librarymanagement.Inventory.Presentation.Models.InventoryRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InventoryRequestMapper {
    @Mapping(target = "id", ignore = true)
    Inventory requestModelToEntity(InventoryRequestModel inventoryRequestModel, InventoryIdentifier inventoryIdentifier);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(InventoryRequestModel inventoryRequestModel, @MappingTarget Inventory inventory);
}