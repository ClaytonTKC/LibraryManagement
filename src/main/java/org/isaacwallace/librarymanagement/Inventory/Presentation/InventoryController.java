package org.isaacwallace.librarymanagement.Inventory.Presentation;

import org.isaacwallace.librarymanagement.Inventory.Business.InventoryService;
import org.isaacwallace.librarymanagement.Inventory.Presentation.Models.InventoryRequestModel;
import org.isaacwallace.librarymanagement.Inventory.Presentation.Models.InventoryResponseModel;
import org.isaacwallace.librarymanagement.Member.Presentation.Models.MemberResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/inventory")
public class InventoryController {
    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("")
    public ResponseEntity<List<InventoryResponseModel>> getAllInventories() {
        return ResponseEntity.status(HttpStatus.OK).body(this.inventoryService.getAllInventories());
    }

    @GetMapping("{inventoryid}")
    public ResponseEntity<InventoryResponseModel> getInventoryById(@PathVariable String inventoryid) {
        return ResponseEntity.status(HttpStatus.OK).body(this.inventoryService.getInventoryById(inventoryid));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryResponseModel> addInventory(@RequestBody InventoryRequestModel inventoryRequestModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.inventoryService.addInventory(inventoryRequestModel));
    }

    @PutMapping(value = "{inventoryid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryResponseModel> EditInventory(@PathVariable String inventoryid, @RequestBody InventoryRequestModel inventoryRequestModel) {
        return ResponseEntity.status(HttpStatus.OK).body(this.inventoryService.updateInventory(inventoryid, inventoryRequestModel));
    }

    @DeleteMapping("{inventoryid}")
    public ResponseEntity<InventoryResponseModel> DeleteInventory(@PathVariable String inventoryid) {
        this.inventoryService.deleteInventory(inventoryid);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
