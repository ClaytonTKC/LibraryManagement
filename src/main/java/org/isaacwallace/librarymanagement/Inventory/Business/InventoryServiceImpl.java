package org.isaacwallace.librarymanagement.Inventory.Business;

import org.isaacwallace.librarymanagement.Inventory.DataAccess.Inventory;
import org.isaacwallace.librarymanagement.Inventory.DataAccess.InventoryIdentifier;
import org.isaacwallace.librarymanagement.Inventory.DataAccess.InventoryRepository;
import org.isaacwallace.librarymanagement.Inventory.Mapper.InventoryRequestMapper;
import org.isaacwallace.librarymanagement.Inventory.Mapper.InventoryResponseMapper;
import org.isaacwallace.librarymanagement.Inventory.Presentation.Models.InventoryRequestModel;
import org.isaacwallace.librarymanagement.Inventory.Presentation.Models.InventoryResponseModel;
import org.isaacwallace.librarymanagement.Utils.Exceptions.InUseException;
import org.isaacwallace.librarymanagement.Utils.Exceptions.InvalidInputException;
import org.isaacwallace.librarymanagement.Utils.Exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryResponseMapper inventoryResponseMapper;
    private final InventoryRequestMapper inventoryRequestMapper;

    private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

    public InventoryServiceImpl(InventoryRepository inventoryRepository, InventoryResponseMapper inventoryResponseMapper, InventoryRequestMapper inventoryRequestMapper) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryResponseMapper = inventoryResponseMapper;
        this.inventoryRequestMapper = inventoryRequestMapper;
    }

    public List<InventoryResponseModel> getAllInventories() {
        return this.inventoryResponseMapper.entityToResponseModelList(this.inventoryRepository.findAll());
    }

    public InventoryResponseModel getInventoryById(String inventoryid) {
        Inventory inventory = this.inventoryRepository.findInventoryByInventoryIdentifier_Inventoryid(inventoryid);

        if (inventory == null) {
            throw new NotFoundException("Unknown inventoryid: " + inventoryid);
        }

        return this.inventoryResponseMapper.entityToResponseModel(inventory);
    }

    public InventoryResponseModel addInventory(InventoryRequestModel inventoryRequestModel) {
        Inventory inventory = this.inventoryRequestMapper.requestModelToEntity(inventoryRequestModel, new InventoryIdentifier());

        return this.inventoryResponseMapper.entityToResponseModel(this.inventoryRepository.save(inventory));
    }

    public InventoryResponseModel updateInventory(String inventoryid, InventoryRequestModel inventoryRequestModel) {
        Inventory inventory = this.inventoryRepository.findInventoryByInventoryIdentifier_Inventoryid(inventoryid);

        if (inventory == null) {
            throw new NotFoundException("Unknown inventoryid: " + inventoryid);
        }

        this.inventoryRequestMapper.updateEntityFromRequest(inventoryRequestModel, inventory);

        if (inventory.getId() < 0) { // Invariant HA
            throw new InvalidInputException("Quantity must always be above 0. Invalid quantity: " + inventory.getQuantity());
        }

        Inventory updatedInventory = this.inventoryRepository.save(inventory);

        logger.info("Updated member with inventoryid: " + inventoryid);

        return this.inventoryResponseMapper.entityToResponseModel(updatedInventory);
    }

    public void deleteInventory(String inventoryid) {
        Inventory inventory = this.inventoryRepository.findInventoryByInventoryIdentifier_Inventoryid(inventoryid);

        if (inventory == null) {
            throw new NotFoundException("Unknown inventoryid: " + inventoryid);
        }

        try {
            this.inventoryRepository.delete(inventory);
            logger.info("Inventory with id: " + inventoryid + " has been deleted");
        } catch (DataIntegrityViolationException exception) {
            logger.error("Failed to delete inventory with id: " + inventoryid, exception.getMessage());
            throw new InUseException("Inventory with id: " + inventoryid + " is already in use by another entity, currently cannot delete.");
        }
    }
}
