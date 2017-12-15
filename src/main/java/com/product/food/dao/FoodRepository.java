package com.product.food.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food,Integer> {
    Food findBySiteAndShopAndFoodName(String site,String shop,String foodName);
    Food findFoodByShop(String shop);
    Food findById(Integer fid);
}
