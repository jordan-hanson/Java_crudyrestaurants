package com.lambdaschool.crudyrestaurants.services;

import com.lambdaschool.crudyrestaurants.models.Restaurant;

import java.util.List;

public interface RestaurantServices {
//    Define all the methods to communicate with the DB;
//    Then in RestaurantServicesImpl it will return the save
    Restaurant save(Restaurant restaurant);

    List<Restaurant> findAllRestaurants();

    Restaurant findRestaurantById(long restid);

    List<Restaurant> findByNameLike(String subname);
}
