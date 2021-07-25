package com.getir.onlinebooks.store.statistics.repository;

import com.getir.onlinebooks.store.statistics.entity.MonthlyOrderStatistic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends CrudRepository<MonthlyOrderStatistic, Long> {
    Iterable<MonthlyOrderStatistic> findAll();
}
