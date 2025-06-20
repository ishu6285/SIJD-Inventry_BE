package com.sijd.ims.service.application;

import com.sijd.ims.dto.application.StockCommonItemPaginateDto;
import com.sijd.ims.dto.application.StockRequest;
import com.sijd.ims.entity.application.ItemCurrent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StockService {
    String saveStockIn(StockRequest stockRequest);

    String saveStockOut(StockRequest stockRequest);

    String updateStockIn(StockRequest stockRequest);

    String updateStockOut(StockRequest stockRequest);

    String deleteStockIn(Long stockInId);

    String deleteStockOut(Long stockInId);

    Page<ItemCurrent> getAllCurrentItem(Pageable pageable);

    Page<StockCommonItemPaginateDto> getAllStockInItem(Pageable pageable);

    List<String> itemSearch(String searchQuery);

    Page<StockCommonItemPaginateDto> getAllStockOutItem(Pageable pageable);


}
