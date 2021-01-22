package com.lambdaschool.crudyrestaurants.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController
{
    // list of all restaurants
    // http://localhost:2019/restaurants/restaurant
    @GetMapping(value = "/restaurant", produces = "application/json")
    public ResponseEntity<?>

    //find restaurant id number
    //http://localhost:2019/restaurants/restaurants/3

    //find restaurants whose name contains a substring
    //http://localhost:2019/restaurants/restaurant/likename/eat

    //custom query (sql)
    //http://localhost:2019/restaurants/menucounts

    //find restaurant based off of menu item
    //http://localhost:2019/restaurants/restaurants/likedish/cake

}
