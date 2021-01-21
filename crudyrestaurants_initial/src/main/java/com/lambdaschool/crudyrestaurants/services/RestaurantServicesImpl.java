package com.lambdaschool.crudyrestaurants.services;

import com.lambdaschool.crudyrestaurants.models.Restaurant;
import com.lambdaschool.crudyrestaurants.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//if a method is transactional make the whole class transactional
@Transactional
//after the return is created let spring know to connect them as a service for the seedData ln 33&34
@Service(value = "restaurantServices")
public class RestaurantServicesImpl implements RestaurantServices{
//    Code for how the save works users don't see referencing the Restaurants Services Interface.
//    Generate a Override Methods from the RestaurantServices to connect them.

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Transactional
    @Override
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }
}
