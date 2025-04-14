package org.isaacwallace.librarymanagement.Inventory.Presentation.Models;

import lombok.Data;
import org.isaacwallace.librarymanagement.Inventory.DataAccess.InventoryStatus;
import org.springframework.hateoas.RepresentationModel;

@Data
public class InventoryResponseModel extends RepresentationModel<InventoryResponseModel> {
    private String inventoryid;

    private String bookid;
    private Integer quantity;

    private InventoryStatus status;
}