package org.isaacwallace.librarymanagement.InventoryBook.Presentation;

import org.isaacwallace.librarymanagement.InventoryBook.Business.InventoryBooksService;
import org.isaacwallace.librarymanagement.InventoryBook.Presentation.Models.InventoryBooksResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/inventory/{inventoryid}/books")
public class InventoryBooksController {
    private final InventoryBooksService inventoryBooksService;

    public InventoryBooksController(InventoryBooksService inventoryBooksService) {
        this.inventoryBooksService = inventoryBooksService;
    }

    @GetMapping("")
    public ResponseEntity<InventoryBooksResponseModel> getAllInventories(@PathVariable String inventoryid) {
        return ResponseEntity.status(HttpStatus.OK).body(this.inventoryBooksService.getAllBooksByInventoryID(inventoryid));
    }
}
