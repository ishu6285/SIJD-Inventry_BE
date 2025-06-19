package com.sijd.ims.repository.application;

import com.sijd.ims.entity.application.ItemCurrent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrentStockRepository extends JpaRepository<ItemCurrent,Long> {
    boolean existsByItemName(String itemName);
    ItemCurrent findByItemName(String itemName);

    @Query("SELECT ic.itemName FROM ItemCurrent ic WHERE ic.itemName LIKE %:searchQuery%")
    List<String> findItemNamesBySearchQuery(@Param("searchQuery") String searchQuery);
}
