package com.getir.onlinebooks.store.statistics.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class MonthlyOrderStatisticDTO {
    private BigDecimal totalOrderCount;
    private Long totalBookCount;
    private BigDecimal totalPurchasedAmount;
    private String monthName;
}
