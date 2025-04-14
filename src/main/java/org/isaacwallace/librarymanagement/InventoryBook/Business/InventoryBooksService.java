package org.isaacwallace.librarymanagement.InventoryBook.Business;

import org.isaacwallace.librarymanagement.InventoryBook.Presentation.Models.InventoryBooksResponseModel;

public interface InventoryBooksService {
    public InventoryBooksResponseModel getAllBooksByInventoryID(String inventoryid);
}
