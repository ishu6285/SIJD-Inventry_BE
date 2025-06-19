package com.sijd.ims.service.application;

import com.sijd.ims.dto.application.StockCommonItemPaginateDto;
import com.sijd.ims.dto.application.StockRequest;
import com.sijd.ims.entity.application.ItemCurrent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StockService {
    String saveStockIn(StockRequest stockRequest);

    String saveStockOut(StockRequest stockRequest);


    Page<ItemCurrent> getAllCurrentItem(Pageable pageable);

    Page<StockCommonItemPaginateDto> getAllStockInItem(Pageable pageable);
}
