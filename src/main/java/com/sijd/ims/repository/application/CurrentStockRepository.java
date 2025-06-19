package com.sijd.ims.repository.application;

import com.sijd.ims.entity.application.ItemCurrent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentStockRepository extends JpaRepository<ItemCurrent,Long> {
    boolean existsByItemName(String itemName);
    ItemCurrent findByItemName(String itemName);
}
