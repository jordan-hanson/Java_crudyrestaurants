package com.lambdaschool.crudyrestaurants.controllers;

import com.lambdaschool.crudyrestaurants.models.Restaurant;
import com.lambdaschool.crudyrestaurants.services.RestaurantServices;
import com.lambdaschool.crudyrestaurants.views.MenuCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.net.URI;
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
    @GetMapping(value = "menucounts", produces= "application/json")
    public ResponseEntity<?> findMenuCounts(){
        List<MenuCounts> rtnList = restaurantServices.findMenuCounts();
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    //find restaurant based off of menu item
    //http://localhost:2019/restaurants/restaurant/likedish/cake
    @GetMapping(value = "/restaurant/likedish/{subdish}", produces = "applications/json")
    public ResponseEntity<?> findRestaurantByDish(@PathVariable String subdish){
        List<Restaurant> rtnList = restaurantServices.findRestaurantByDish(subdish);
                return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    @DeleteMapping(value = "/restaurant/{restid}")
    public ResponseEntity<?> deleteRestaurantById(@PathVariable long restid){
        restaurantServices.delete(restid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // It needs a request body after the value to give an object that contains the information
    // consumes = "application/json". (can be a security issue don't use personal preference)
    @PatchMapping(value = "/restaurant/{restid}", consumes= "application/json")
    //Send the @RequestBody to send an object for the update but it doesn't have to be valid
    public ResponseEntity<?> updateRestaurantById(@PathVariable long restid, @RequestBody Restaurant updateRestaurant) {
        // We use update for PATCH since we are only updating an existing "valid" object data point.
        restaurantServices.update(updateRestaurant, restid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //consumes json is needed here and produces is printing to show you it did it.
    @PutMapping(value = "/restuarant/{restid}", consumes="application/json", produces= "application/json")
    ResponseEntity<?> replaceRestaurantById(@PathVariable long restid, @Valid @RequestBody Restaurant replaceInRestaurant){
        //Look at the id to validate the save method that replaces the current for the new Restaurant
        //Use with post and put.
        replaceInRestaurant.setRestaurantid(restid);
        Restaurant r = restaurantServices.save(replaceInRestaurant);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    //FOR POST AND PUT USE THE SAVE METHOD TO SAVE THE UPDATED OR NEW DATA OBJECT
    // Using produces a json object the user can see it cannot be secure to show the user all the information on the
    //updated data object including the Id on the object it updated it with.
    @PostMapping(value = "/restaurant", consumes= "application/json", produces = "application/json")
//        @PostMapping(value = "/restaurant/", consumes= "application/json", produces = "application/json") doesn't work
//    with my post request it does a header url with restaurant{id} instead of demo /restaurant/id
    public ResponseEntity<?> newRestaurant(@Valid @RequestBody Restaurant newRestaurant){
        newRestaurant.setRestaurantid(0);
        newRestaurant = restaurantServices.save(newRestaurant);
//      When you create send back a url for the new CREATED resource with a response header that contains a URl
//        for the user. use the springframework HTTPheader
        HttpHeaders responseHeaders = new HttpHeaders();
//        Then build the URI for it
//        a URI is this /restaurant part of a URL
        URI newRestaurantURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{restid}")
                .buildAndExpand(newRestaurant.getRestaurantid())
                .toUri();
        return new ResponseEntity<>(newRestaurant, responseHeaders, HttpStatus.CREATED);
    }

}
