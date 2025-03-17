package org.isaacwallace.librarymanagement.InventoryBooks.Presentation.Models;

import lombok.Data;
import org.isaacwallace.librarymanagement.Book.Presentation.Models.BookResponseModel;
import org.isaacwallace.librarymanagement.Inventory.DataAccess.InventoryStatus;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
public class InventoryBooksResponseModel extends RepresentationModel<InventoryBooksResponseModel> {
    private String inventoryid;

    private String bookid;
    private String quantity;

    private InventoryStatus status;

    private List<BookResponseModel> books;
}
