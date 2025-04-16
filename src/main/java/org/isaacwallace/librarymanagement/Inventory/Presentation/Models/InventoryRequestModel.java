package org.isaacwallace.librarymanagement.Inventory.Presentation.Models;

import lombok.*;
import org.isaacwallace.librarymanagement.Inventory.DataAccess.InventoryStatus;
import org.springframework.hateoas.RepresentationModel;

@Value
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InventoryRequestModel extends RepresentationModel<InventoryRequestModel> {
    String bookid;
    Integer quantity;
}