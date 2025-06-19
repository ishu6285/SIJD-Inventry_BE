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

@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService {
    private final CurrentStockRepository currentStockRepository;
    private final StockOutRepository stockOutRepository;
    private final StockInRepository stockInRepository;

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