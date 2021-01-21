package com.lambdaschool.crudyrestaurants.services;

import com.lambdaschool.crudyrestaurants.models.Restaurant;

public interface RestaurantServices {
//    Define all the methods to communicate with the DB;
//    Then in RestaurantServicesImpl it will return the save
    Restaurant save(Restaurant restaurant);
}
