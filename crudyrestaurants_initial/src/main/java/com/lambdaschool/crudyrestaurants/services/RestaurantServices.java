package com.lambdaschool.crudyrestaurants.services;

import com.lambdaschool.crudyrestaurants.models.Restaurant;
import com.lambdaschool.crudyrestaurants.views.MenuCounts;

import java.util.List;

public interface RestaurantServices {
//    Define all the methods to communicate with the DB;
//    Then in RestaurantServicesImpl it will return the save

    List<Restaurant> findAllRestaurants(); //GET

    Restaurant findRestaurantById(long restid);//GET

    List<Restaurant> findByNameLike(String subname);//GET

    List<MenuCounts> findMenuCounts();//GET

    List<Restaurant> findRestaurantByDish(String subdish);//GET

    Restaurant save(Restaurant restaurant);//POST

    void delete(long id);//DELETE

    Restaurant update(Restaurant restaurant, long id); //PATCH vs  REPLACE WITH PUT (only updating certain fields of a data object)

    void deleteAll(); //DELETE ALL (only for Seed Data not a User to use)
}
