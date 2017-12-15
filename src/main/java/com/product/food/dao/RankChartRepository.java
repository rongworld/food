package com.product.food.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.ArrayList;

public interface RankChartRepository extends JpaRepository<RankChart,Integer> {
    ArrayList<RankChart> findAllByRankBetween(Integer start,Integer end);
}
