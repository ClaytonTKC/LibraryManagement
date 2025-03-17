package org.isaacwallace.librarymanagement.Inventory.Business;

import org.isaacwallace.librarymanagement.Inventory.Presentation.Models.InventoryRequestModel;
import org.isaacwallace.librarymanagement.Inventory.Presentation.Models.InventoryResponseModel;

import java.util.List;

public interface InventoryService {
    public List<InventoryResponseModel> getAllInventories();
    public InventoryResponseModel getInventoryById(String inventoryid);
    public InventoryResponseModel addInventory(InventoryRequestModel inventoryRequestModel);
    public InventoryResponseModel updateInventory(String inventoryid, InventoryRequestModel inventoryRequestModel);
    public void deleteInventory(String inventoryid);
}
