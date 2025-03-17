package org.isaacwallace.librarymanagement.InventoryBooks.Business;

import org.isaacwallace.librarymanagement.InventoryBooks.Presentation.Models.InventoryBooksResponseModel;

public interface InventoryBooksService {
    public InventoryBooksResponseModel getAllBooksByInventoryID(String inventoryid);
}
