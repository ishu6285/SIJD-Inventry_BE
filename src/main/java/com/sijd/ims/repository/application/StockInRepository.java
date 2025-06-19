package com.sijd.ims.repository.application;

import com.sijd.ims.entity.application.ItemStockIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockInRepository extends JpaRepository<ItemStockIn,Long> {
}
