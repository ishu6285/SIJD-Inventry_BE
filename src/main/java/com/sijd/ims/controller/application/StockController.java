package com.sijd.ims.controller.application;

import com.sijd.ims.constant.AppConstant;
import com.sijd.ims.dto.application.StockCommonItemPaginateDto;
import com.sijd.ims.dto.application.StockRequest;
import com.sijd.ims.entity.application.ItemCurrent;
import com.sijd.ims.service.application.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("get/all/current-stock")
    ResponseEntity<Page<ItemCurrent>> getAllCurrentItem(

            @RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_NO) final Integer page,
            @RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_SIZE) final Integer size,
            @RequestParam(defaultValue = "createdDateTime") final String sortField,
            @RequestParam(defaultValue = AppConstant.DEF_SORT_DIR_DESC) final String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc") ?
                Sort.by(sortField).descending() :
                Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(stockService.getAllCurrentItem(pageable));
    }

    @GetMapping("get/all/stock-in")
    ResponseEntity<Page<StockCommonItemPaginateDto>> getAllStockInItem(

            @RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_NO) final Integer page,
            @RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_SIZE) final Integer size,
            @RequestParam(defaultValue = "createdDateTime") final String sortField,
            @RequestParam(defaultValue = AppConstant.DEF_SORT_DIR_DESC) final String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc") ?
                Sort.by(sortField).descending() :
                Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(stockService.getAllStockInItem(pageable));

    }

}
