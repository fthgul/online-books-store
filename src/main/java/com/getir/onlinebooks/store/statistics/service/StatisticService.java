package com.getir.onlinebooks.store.statistics.service;

import com.getir.onlinebooks.store.statistics.model.MonthlyOrderStatisticDTO;

import java.util.List;

public interface StatisticService {
    List<MonthlyOrderStatisticDTO> getMonthlyStatistics();
}
