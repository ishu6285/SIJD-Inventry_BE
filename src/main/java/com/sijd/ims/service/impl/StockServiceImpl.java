package com.sijd.ims.service.impl;

import com.sijd.ims.dto.application.StockCommonItemPaginateDto;
import com.sijd.ims.dto.application.StockRequest;
import com.sijd.ims.entity.AuditModifyUser;
import com.sijd.ims.entity.application.ItemCurrent;
import com.sijd.ims.entity.application.ItemStockIn;
import com.sijd.ims.entity.application.ItemStockOut;
import com.sijd.ims.repository.application.CurrentStockRepository;
import com.sijd.ims.repository.application.StockInRepository;
import com.sijd.ims.repository.application.StockOutRepository;
import com.sijd.ims.service.application.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService {
    private final CurrentStockRepository currentStockRepository;
    private final StockOutRepository stockOutRepository;
    private final StockInRepository stockInRepository;
    private static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @Override
    @Transactional
    public String saveStockIn(StockRequest stockRequest) {
        if(checkStockNameInCurrentStock(stockRequest.getItemName())){
            ItemCurrent current = currentStockRepository.findByItemName(stockRequest.getItemName());
            current.setItemQuantity(current.getItemQuantity().add(stockRequest.getQuantity()));

            currentStockRepository.save(current);
            stockInRepository.save(buildStockIn(stockRequest,current));

            return String.format("Stock in success for item '%s'. Added quantity: %s. New total: %s",
                    stockRequest.getItemName(),
                    stockRequest.getQuantity(),
                    current.getItemQuantity());
        }
        assert buildItemCurrent(stockRequest) != null;
        ItemCurrent current= currentStockRepository.save(buildItemCurrent(stockRequest));
        stockInRepository.save(buildStockIn(stockRequest,current));
        return String.format("New item '%s' added to stock with quantity: %s",
                stockRequest.getItemName(),
                stockRequest.getQuantity());
    }

    @Override
    @Transactional
    public String saveStockOut(StockRequest stockRequest) {
        if (!checkStockNameInCurrentStock(stockRequest.getItemName())) {
            return String.format("Item '%s' not found in current stock", stockRequest.getItemName());
        }

        ItemCurrent current = currentStockRepository.findByItemName(stockRequest.getItemName());

        if (current.getItemQuantity().compareTo(stockRequest.getQuantity()) < 0) {
            return String.format("Insufficient stock for item '%s'. Available: %s, Requested: %s",
                    stockRequest.getItemName(),
                    current.getItemQuantity(),
                    stockRequest.getQuantity());
        }

        current.setItemQuantity(current.getItemQuantity().subtract(stockRequest.getQuantity()));
        currentStockRepository.save(current);
        stockOutRepository.save(buildStockOut(stockRequest, current));

        return String.format("Stock out success for item '%s'. Quantity removed: %s. Remaining stock: %s",
                stockRequest.getItemName(),
                stockRequest.getQuantity(),
                current.getItemQuantity());
    }

    @Override
    @Transactional
    public String updateStockIn(StockRequest stockRequest) {
        logger.info("Attempting to update Stock In record with ID: {}", stockRequest.getStockInId());

        if (stockRequest.getStockInId() == null) {
            logger.error("Stock In ID is null for update request.");
            return "Stock In ID is required for update.";
        }

        Optional<ItemStockIn> existingStockInOptional = stockInRepository.findById(stockRequest.getStockInId());

        if (existingStockInOptional.isEmpty()) {
            logger.warn("Stock In record with ID {} not found.", stockRequest.getStockInId());
            return String.format("Stock In record with ID %d not found.", stockRequest.getStockInId());
        }

        ItemStockIn existingStockIn = existingStockInOptional.get();
        BigDecimal originalQuantity = existingStockIn.getStockInQuantity();
        ItemCurrent currentItem = existingStockIn.getStockInItem();

        logger.info("Found Stock In record for Item: '{}' (ID: {}). Original Quantity: {}. New Quantity requested: {}",
                currentItem.getItemName(), stockRequest.getStockInId(), originalQuantity, stockRequest.getQuantity());


        if (!currentItem.getItemName().equalsIgnoreCase(stockRequest.getItemName())) {
            logger.warn("Item name change detected during stock-in edit. Original: '{}', Requested: '{}'. Denying update.",
                    currentItem.getItemName(), stockRequest.getItemName());
            return String.format("Cannot change item name '%s' to '%s' during a stock-in edit. Please use separate stock-out/stock-in operations.",
                    currentItem.getItemName(), stockRequest.getItemName());
        }

        // --- Core Logic for Current Stock Adjustment ---
        BigDecimal currentItemQuantityBeforeUpdate = currentItem.getItemQuantity();
        currentItem.setItemQuantity(currentItem.getItemQuantity().subtract(originalQuantity).add(stockRequest.getQuantity()));
        logger.info("ItemCurrent for '{}' quantity adjusted. Before: {}, After: {}",
                currentItem.getItemName(), currentItemQuantityBeforeUpdate, currentItem.getItemQuantity());
        // --- End Core Logic ---

        currentStockRepository.save(currentItem);
        existingStockIn.setStockInQuantity(stockRequest.getQuantity());
        stockInRepository.save(existingStockIn);

        logger.info("Stock In record ID {} and Current Stock for '{}' updated successfully.",
                stockRequest.getStockInId(), currentItem.getItemName());

        return String.format("Stock In record ID %d updated successfully. Original quantity: %s, New quantity: %s. Current stock for '%s': %s",
                stockRequest.getStockInId(),
                originalQuantity,
                stockRequest.getQuantity(),
                currentItem.getItemName(),
                currentItem.getItemQuantity());
    }

    @Override
    @Transactional
    public String updateStockOut(StockRequest stockRequest) {
        logger.info("Attempting to update Stock Out record with ID: {}", stockRequest.getStockOutId());

        if (stockRequest.getStockOutId() == null) {
            logger.error("Stock Out ID is null for update request.");
            return "Stock Out ID is required for update.";
        }

        Optional<ItemStockOut> existingStockOutOptional = stockOutRepository.findById(stockRequest.getStockOutId());

        if (existingStockOutOptional.isEmpty()) {
            logger.warn("Stock Out record with ID {} not found.", stockRequest.getStockOutId());
            return String.format("Stock Out record with ID %d not found.", stockRequest.getStockOutId());
        }

        ItemStockOut existingStockOut = existingStockOutOptional.get();
        BigDecimal originalQuantity = existingStockOut.getStockOutQuantity();
        ItemCurrent currentItem = existingStockOut.getStockOutItem();

        logger.info("Found Stock Out record for Item: '{}' (ID: {}). Original Quantity: {}. New Quantity requested: {}",
                currentItem.getItemName(), stockRequest.getStockOutId(), originalQuantity, stockRequest.getQuantity());


        if (!currentItem.getItemName().equalsIgnoreCase(stockRequest.getItemName())) {
            logger.warn("Item name change detected during stock-out edit. Original: '{}', Requested: '{}'. Denying update.",
                    currentItem.getItemName(), stockRequest.getItemName());
            return String.format("Cannot change item name '%s' to '%s' during a stock-out edit. Please use separate stock-in/stock-out operations.",
                    currentItem.getItemName(), stockRequest.getItemName());
        }


        BigDecimal quantityDifference = stockRequest.getQuantity().subtract(originalQuantity); // new_qty - old_qty
        BigDecimal currentItemQuantityBeforeUpdate = currentItem.getItemQuantity();

        if (quantityDifference.compareTo(BigDecimal.ZERO) > 0) {

            BigDecimal netChangeToCurrentStock = originalQuantity.subtract(stockRequest.getQuantity());

            BigDecimal projectedCurrentStock = currentItem.getItemQuantity().add(netChangeToCurrentStock);

            if (projectedCurrentStock.compareTo(BigDecimal.ZERO) < 0) {
                logger.warn("Insufficient stock for item '{}' after updating stock-out. Projected current stock: {}",
                        currentItem.getItemName(), projectedCurrentStock);
                return String.format("Insufficient stock for item '%s'. Update would result in negative stock. Available: %s, Proposed Stock-out: %s",
                        currentItem.getItemName(), currentItem.getItemQuantity(), stockRequest.getQuantity());
            }
        }


        currentItem.setItemQuantity(currentItem.getItemQuantity().add(originalQuantity).subtract(stockRequest.getQuantity()));
        logger.info("ItemCurrent for '{}' quantity adjusted. Before: {}, After: {}",
                currentItem.getItemName(), currentItemQuantityBeforeUpdate, currentItem.getItemQuantity());


        currentStockRepository.save(currentItem);
        existingStockOut.setStockOutQuantity(stockRequest.getQuantity());
        stockOutRepository.save(existingStockOut);

        logger.info("Stock Out record ID {} and Current Stock for '{}' updated successfully.",
                stockRequest.getStockOutId(), currentItem.getItemName());

        return String.format("Stock Out record ID %d updated successfully. Original quantity: %s, New quantity: %s. Current stock for '%s': %s",
                stockRequest.getStockOutId(),
                originalQuantity,
                stockRequest.getQuantity(),
                currentItem.getItemName(),
                currentItem.getItemQuantity());
    }

    @Override
    @Transactional
    public String deleteStockIn(Long stockInId) {
        logger.info("Attempting to delete Stock In record with ID: {}", stockInId);

        if (stockInId == null) {
            logger.error("Stock In ID is null for delete request.");
            return "Stock In ID is required for delete.";
        }

        Optional<ItemStockIn> existingStockInOptional = stockInRepository.findById(stockInId);

        if (existingStockInOptional.isEmpty()) {
            logger.warn("Stock In record with ID {} not found for deletion.", stockInId);
            return String.format("Stock In record with ID %d not found.", stockInId);
        }

        ItemStockIn existingStockIn = existingStockInOptional.get();
        BigDecimal quantityToDelete = existingStockIn.getStockInQuantity();
        ItemCurrent currentItem = existingStockIn.getStockInItem();

        logger.info("Found Stock In record for Item: '{}' (ID: {}). Quantity to be removed from current stock: {}",
                currentItem.getItemName(), stockInId, quantityToDelete);


        BigDecimal currentItemQuantityBeforeUpdate = currentItem.getItemQuantity();
        currentItem.setItemQuantity(currentItem.getItemQuantity().subtract(quantityToDelete));

        if (currentItem.getItemQuantity().compareTo(BigDecimal.ZERO) < 0) {
            logger.warn("Deleting Stock In record ID {} would result in negative current stock for '{}'. Projected: {}. Denying delete.",
                    stockInId, currentItem.getItemName(), currentItem.getItemQuantity());
            throw new IllegalStateException(String.format("Deleting stock-in record would make current stock for '%s' negative. Operation cancelled.", currentItem.getItemName()));
        }

        logger.info("ItemCurrent for '{}' quantity adjusted (after deletion). Before: {}, After: {}",
                currentItem.getItemName(), currentItemQuantityBeforeUpdate, currentItem.getItemQuantity());

        currentStockRepository.save(currentItem);
        stockInRepository.delete(existingStockIn);

        logger.info("Stock In record ID {} deleted and Current Stock for '{}' adjusted successfully.",
                stockInId, currentItem.getItemName());

        return String.format("Stock In record ID %d deleted successfully. Quantity %s removed from current stock for '%s'. Remaining stock: %s",
                stockInId,
                quantityToDelete,
                currentItem.getItemName(),
                currentItem.getItemQuantity());
    }

    @Override
    @Transactional
    public String deleteStockOut(Long stockOutId) {
        logger.info("Attempting to delete Stock Out record with ID: {}", stockOutId);

        if (stockOutId == null) {
            logger.error("Stock Out ID is null for delete request.");
            return "Stock Out ID is required for delete.";
        }

        Optional<ItemStockOut> existingStockOutOptional = stockOutRepository.findById(stockOutId);

        if (existingStockOutOptional.isEmpty()) {
            logger.warn("Stock Out record with ID {} not found for deletion.", stockOutId);
            return String.format("Stock Out record with ID %d not found.", stockOutId);
        }

        ItemStockOut existingStockOut = existingStockOutOptional.get();
        BigDecimal quantityToDelete = existingStockOut.getStockOutQuantity();
        ItemCurrent currentItem = existingStockOut.getStockOutItem();

        logger.info("Found Stock Out record for Item: '{}' (ID: {}). Quantity to be added back to current stock: {}",
                currentItem.getItemName(), stockOutId, quantityToDelete);

        BigDecimal currentItemQuantityBeforeUpdate = currentItem.getItemQuantity();

        currentItem.setItemQuantity(currentItem.getItemQuantity().add(quantityToDelete));

        logger.info("ItemCurrent for '{}' quantity adjusted (after deletion). Before: {}, After: {}",
                currentItem.getItemName(), currentItemQuantityBeforeUpdate, currentItem.getItemQuantity());


        currentStockRepository.save(currentItem);
        stockOutRepository.delete(existingStockOut);

        logger.info("Stock Out record ID {} deleted and Current Stock for '{}' adjusted successfully.",
                stockOutId, currentItem.getItemName());

        return String.format("Stock Out record ID %d deleted successfully. Quantity %s added back to current stock for '%s'. Remaining stock: %s",
                stockOutId,
                quantityToDelete,
                currentItem.getItemName(),
                currentItem.getItemQuantity());
    }

    @Override
    public Page<ItemCurrent> getAllCurrentItem(Pageable pageable) {
        return currentStockRepository.findAll(pageable);
    }

    @Override
    public Page<StockCommonItemPaginateDto> getAllStockInItem(Pageable pageable) {
        return stockInRepository.findAll(pageable).map(stockIn ->
                StockCommonItemPaginateDto.builder()
                        .version(stockIn.getVersion())
                        .createdUser(stockIn.getCreatedUser())
                        .createdDateTime(stockIn.getCreatedDateTime())
                        .modifiedUser(stockIn.getModifiedUser())
                        .modifiedDateTime(stockIn.getModifiedDateTime())
                        .status(String.valueOf(stockIn.getStatus()))
                        .itemId(stockIn.getItemId())
                        .itemName(stockIn.getStockInItem().getItemName())
                        .itemQuantity(stockIn.getStockInQuantity())
                        .build()
        );
    }


    @Override
    public Page<StockCommonItemPaginateDto> getAllStockOutItem(Pageable pageable) {
        return stockOutRepository.findAll(pageable).map(stockOut ->
                StockCommonItemPaginateDto.builder()
                        .version(stockOut.getVersion())
                        .createdUser(stockOut.getCreatedUser())
                        .createdDateTime(stockOut.getCreatedDateTime())
                        .modifiedUser(stockOut.getModifiedUser())
                        .modifiedDateTime(stockOut.getModifiedDateTime())
                        .status(String.valueOf(stockOut.getStatus()))
                        .itemId(stockOut.getItemId())
                        .itemName(stockOut.getStockOutItem().getItemName())
                        .itemQuantity(stockOut.getStockOutQuantity())
                        .build()
        );
    }



    @Override
    public List<String> itemSearch(String searchQuery) {
        List<String> searchList =currentStockRepository.findItemNamesBySearchQuery(searchQuery);
        if(!searchList.isEmpty()){
            return searchList;
        }
        return null;
    }



    private ItemCurrent buildItemCurrent(StockRequest stockRequest) {
        return ItemCurrent.builder()
                .itemName(stockRequest.getItemName())
                .itemQuantity(stockRequest.getQuantity())
                .build();
    }

    private ItemStockIn buildStockIn(StockRequest stockRequest, ItemCurrent current) {
        return ItemStockIn.builder()
                .stockInItem(current)
                .stockInQuantity(stockRequest.getQuantity())
                .build();
    }

    private ItemStockOut buildStockOut(StockRequest stockRequest, ItemCurrent current) {
        return ItemStockOut.builder()
                .stockOutItem(current)
                .stockOutQuantity(stockRequest.getQuantity())
                .build();
    }

    private boolean checkStockNameInCurrentStock(String itemName){
        return currentStockRepository.existsByItemName(itemName);
    }
}