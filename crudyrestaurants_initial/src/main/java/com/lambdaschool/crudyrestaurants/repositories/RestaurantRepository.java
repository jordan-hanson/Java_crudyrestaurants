package com.lambdaschool.crudyrestaurants.repositories;

import com.lambdaschool.crudyrestaurants.models.Restaurant;
import com.lambdaschool.crudyrestaurants.views.MenuCounts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    List<Restaurant> findByNameContainingIgnoringCase(String subname);
    List<Restaurant> findByMenus_dishContainingIgnoringCase(String subdish);

    @Query(value = "SELECT r.name as name, count(m.menuid) as countmenus " +
    "FROM restaurants r LEFT JOIN menus m " +
    "ON r.restaurantid = m.restaurantid " +
    "GROUP BY r.name " +
    "ORDER BY countmenus DESC ", nativeQuery = true)
    List<MenuCounts> findMenuCounts();
}
