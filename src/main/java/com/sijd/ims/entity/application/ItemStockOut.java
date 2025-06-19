package com.sijd.ims.entity.application;

import com.sijd.ims.entity.AuditModifyUser;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "item_stock_out")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemStockOut extends AuditModifyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_stock_out_id")
    private Long itemId;

    @JoinColumn(name = "item_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ItemCurrent stockOutItem;

    @Column(name = "quantity",columnDefinition = "DECIMAL(10,2)",nullable = false)
    private BigDecimal stockOutQuantity;
}
