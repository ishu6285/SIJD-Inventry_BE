package com.sijd.ims.entity.application;

import com.sijd.ims.entity.AuditModifyUser;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "item_current")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemCurrent extends AuditModifyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_auantity", nullable = false)
    private BigDecimal itemQuantity;
}
