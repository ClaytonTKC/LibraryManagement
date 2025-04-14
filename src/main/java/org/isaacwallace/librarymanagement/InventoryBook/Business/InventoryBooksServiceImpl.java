package org.isaacwallace.librarymanagement.InventoryBook.Business;

import org.isaacwallace.librarymanagement.Book.DataAccess.BookRepository;
import org.isaacwallace.librarymanagement.Book.Mapper.BookResponseMapper;
import org.isaacwallace.librarymanagement.Book.Presentation.Models.BookResponseModel;
import org.isaacwallace.librarymanagement.Inventory.DataAccess.Inventory;
import org.isaacwallace.librarymanagement.Inventory.DataAccess.InventoryRepository;
import org.isaacwallace.librarymanagement.Inventory.Mapper.InventoryResponseMapper;
import org.isaacwallace.librarymanagement.InventoryBook.Presentation.Models.InventoryBooksResponseModel;
import org.isaacwallace.librarymanagement.Utils.Exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryBooksServiceImpl implements InventoryBooksService {
    private final InventoryRepository inventoryRepository;
    private final BookRepository bookRepository;
    private final InventoryResponseMapper inventoryResponseMapper;
    private final BookResponseMapper bookResponseMapper;

    public InventoryBooksServiceImpl(InventoryRepository inventoryRepository, BookRepository bookRepository, InventoryResponseMapper inventoryResponseMapper, BookResponseMapper bookResponseMapper) {
        this.inventoryRepository = inventoryRepository;
        this.bookRepository = bookRepository;
        this.inventoryResponseMapper = inventoryResponseMapper;
        this.bookResponseMapper = bookResponseMapper;
    }

    public InventoryBooksResponseModel getAllBooksByInventoryID(String inventoryid) {
        Inventory inventory = this.inventoryRepository.findInventoryByInventoryIdentifier_Inventoryid(inventoryid);

        if (inventory == null) {
            throw new NotFoundException("Unknown inventoryid: " + inventoryid);
        }

        List<BookResponseModel> books = this.bookResponseMapper.entitiesToResponseModelList(this.bookRepository.findBooksByInventoryid(inventoryid));
        InventoryBooksResponseModel inventoryModel = this.inventoryResponseMapper.bookToAggregateResponseModel(inventory);
        inventoryModel.setBooks(books);
        inventoryModel.setInventoryid(inventoryid);

        return inventoryModel;
    }
}
