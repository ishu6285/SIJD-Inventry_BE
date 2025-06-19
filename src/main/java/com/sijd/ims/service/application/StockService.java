package com.sijd.ims.service.application;

import com.sijd.ims.dto.application.StockRequest;

public interface StockService {
    String saveStockIn(StockRequest stockRequest);

    String saveStockOut(StockRequest stockRequest);
}
