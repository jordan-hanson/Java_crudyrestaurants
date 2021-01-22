package com.lambdaschool.crudyrestaurants.controllers;

import com.lambdaschool.crudyrestaurants.models.Restaurant;
import com.lambdaschool.crudyrestaurants.services.RestaurantServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController
{

    @Autowired
    private RestaurantServices restaurantServices;
    // list of all restaurants
    // http://localhost:2019/restaurants/restaurant
    @GetMapping(value = "/restaurant", produces = "application/json")
    public ResponseEntity<?> listAllRestaurants() {

        List<Restaurant> listRestaurants = restaurantServices.findAllRestaurants();
        return new ResponseEntity<>(listRestaurants, HttpStatus.OK);
    }

    //find restaurant id number
    //http://localhost:2019/restaurants/restaurant/3

    @GetMapping(value = "/restaurant/{restid}", produces = "application/json")
    public ResponseEntity<?> findRestaurantById(@PathVariable long restid) {
        Restaurant restaurant = restaurantServices.findRestaurantById(restid);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
    //find restaurants whose name contains a substring
    //http://localhost:2019/restaurants/restaurant/likename/eat

    @GetMapping(value = "/restaurant/likename/{subname}", produces = "application/json")
    public ResponseEntity<?> findRestaurantByNameLike(@PathVariable String subname){
        List<Restaurant> rtnList = restaurantServices.findByNameLike(subname);
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    //custom query (sql)
    //http://localhost:2019/restaurants/menucounts

    //find restaurant based off of menu item
    //http://localhost:2019/restaurants/restaurant/likedish/cake

}
