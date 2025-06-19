package com.sijd.ims.repository.application;

import com.sijd.ims.entity.application.ItemStockOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockOutRepository extends JpaRepository<ItemStockOut,Long> {
}
