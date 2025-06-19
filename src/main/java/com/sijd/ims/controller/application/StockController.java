package com.sijd.ims.controller.application;

import com.sijd.ims.dto.application.StockRequest;
import com.sijd.ims.service.application.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping(value = "/api/${version}/admin/")
@RequiredArgsConstructor
@RestController
@CrossOrigin
public class StockController {
    private final StockService stockService;

    @PostMapping("stock/in")
    ResponseEntity<String> saveStockIn(@RequestBody @Valid StockRequest stockRequest){

       return ResponseEntity.ok(stockService.saveStockIn(stockRequest));

    }
    @PostMapping("stock/out")
    ResponseEntity<String> saveStockOut(@RequestBody @Valid StockRequest stockRequest){
        return ResponseEntity.ok(stockService.saveStockOut(stockRequest));
    }
}
