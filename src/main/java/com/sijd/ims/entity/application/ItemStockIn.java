package com.sijd.ims.entity.application;

import com.sijd.ims.entity.AuditModifyUser;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "item_stock_in")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemStockIn  extends AuditModifyUser
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_stock_in_id")
    private Long itemId;

    @JoinColumn(name = "item_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ItemCurrent stockInItem;

    @Column(name = "quantity",nullable = false,columnDefinition = "DECIMAL(10,2)")
    private BigDecimal stockInQuantity;
}
