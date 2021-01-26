package com.lambdaschool.crudyrestaurants.services;

import com.lambdaschool.crudyrestaurants.models.Menu;
import com.lambdaschool.crudyrestaurants.models.Payment;
import com.lambdaschool.crudyrestaurants.models.Restaurant;
import com.lambdaschool.crudyrestaurants.repositories.PaymentRepository;
import com.lambdaschool.crudyrestaurants.repositories.RestaurantRepository;
import com.lambdaschool.crudyrestaurants.views.MenuCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

//if a method is transactional make the whole class transactional
@Transactional
//after the return is created let spring know to connect them as a service for the seedData ln 33&34
@Service(value = "restaurantServices")
public class RestaurantServicesImpl implements RestaurantServices{
//    Code for how the save works users don't see referencing the Restaurants Services Interface.
//    Generate a Override Methods from the RestaurantServices to connect them.

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private PaymentRepository paymentRepository;


    @Override
    public List<Restaurant> findAllRestaurants() {
        List<Restaurant> list = new ArrayList<>();
        restaurantRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Restaurant findRestaurantById(long restid) {
        return restaurantRepository.findById(restid)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant" + restid + "not Found."));
    }

    @Override
    public List<Restaurant> findByNameLike(String subname) {
        return restaurantRepository.findByNameContainingIgnoringCase(subname);
    }

    @Override
    public List<MenuCounts> findMenuCounts() {
        return restaurantRepository.findMenuCounts();
    }

    @Override
    public List<Restaurant> findRestaurantByDish(String subdish) {
        return restaurantRepository.findByMenus_dishContainingIgnoringCase(subdish);
    }

    @Transactional
    @Override
    public Restaurant save(Restaurant restaurant) {
//        Start with making a new restaurant from the object you passed.
        Restaurant newRestaurant = new Restaurant();

//        Put and Post both use Save so validate if we are saving new content on an existing object or creating a new one.


//        For the developers to validate information being inputed is correct and works.
//        If it isn't a 0 then it is a put. If it IS a 0 we are doing a post.
        if (restaurant.getRestaurantid() !=0){
//            Find the id to save the already created restaurant on to PUT.
            restaurantRepository.findById(restaurant.getRestaurantid())
//                    If it does equal 0 the info can't be CREATED ON AN EXISTING ID and the id isn't found POST.
                    .orElseThrow(() -> new EntityNotFoundException("Restaurant " + restaurant.getRestaurantid() + "Not Found!"));
//            if there is an id attached to update it set the id for it
            newRestaurant.setRestaurantid(restaurant.getRestaurantid());
        }

//        CREATE: Then Validate the info for all the fields needed in the object data point
//        Set by getting the fields
        newRestaurant.setName(restaurant.getName());
        newRestaurant.setAddress(restaurant.getAddress());
        newRestaurant.setCity(restaurant.getCity());
        newRestaurant.setState(restaurant.getState());
        newRestaurant.setTelephone(restaurant.getTelephone());
        newRestaurant.setSeatcapacity(restaurant.getSeatcapacity());

//        With the many to many hook to a existing repository.
//        @Autowired
//        private PaymentRepository paymentRepository;
//        Then clear it to make 100% sure the payments are clear
        newRestaurant.getPayments().clear();
//        For all the payments passed through the input fields in restaurant we are passing down
//        go through them and assign them to a newPayment variableObject

        for (Payment p: restaurant.getPayments()){
            Payment newPayment = paymentRepository.findById(p.getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found."));
            newRestaurant.getPayments().add(newPayment);
        }

        newRestaurant.getMenus().clear();
//        Because menus don't exist without a restaurant it is different tehn the payments that are
//        already in a made table.
        for (Menu m : restaurant.getMenus()){
            Menu newMenu = new Menu();
                newMenu.setDish(m.getDish());
                newMenu.setPrice(m.getPrice());
                newMenu.setRestaurant(newRestaurant);

                newRestaurant.getMenus().add(newMenu);

        }
        return restaurantRepository.save(newRestaurant);
    }

    @Transactional
    @Override
    public void delete(long id) {
        restaurantRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Restaurant update(Restaurant restaurant, long id) {
//        Start with making a new restaurant from the object you passed.
                Restaurant updateRestaurant = restaurantRepository.findById(id)
//                    If it does equal 0 the info can't be CREATED ON AN EXISTING ID and the id isn't found POST.
                    .orElseThrow(() -> new EntityNotFoundException("Restaurant " + id + "Not Found!"));

//        CREATE: Then Validate the info for all the fields needed in the object data point
//        Set by getting the fields
        if (restaurant.getName() !=null) updateRestaurant.setName(restaurant.getName());
        if (restaurant.getAddress() !=null) updateRestaurant.setAddress(restaurant.getAddress());
        if (restaurant.getCity() !=null) updateRestaurant.setCity(restaurant.getCity());
        if (restaurant.getState() !=null) updateRestaurant.setState(restaurant.getState());
        if (restaurant.getTelephone() !=null) updateRestaurant.setTelephone(restaurant.getTelephone());
        if (restaurant.hasValueForSeatCapacity) updateRestaurant.setSeatcapacity(restaurant.getSeatcapacity());

//        With the many to many hook to a existing repository.
//        @Autowired
//        private PaymentRepository paymentRepository;
//        Then clear the Relationships @many to many or @one to many to make 100% sure the payments are clear & check to rebuild the payments.
        if(restaurant.getPayments().size() >0 ) {
            updateRestaurant.getPayments().clear();
//        For all the payments passed through the input fields in restaurant we are passing down
//        go through them and assign them to a newPayment variableObject

            for (Payment p : restaurant.getPayments()) {
                Payment newPayment = paymentRepository.findById(p.getPaymentid())
                        .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found."));
                updateRestaurant.getPayments().add(newPayment);
            }
        }

        if(restaurant.getMenus().size() >0 ) {
            updateRestaurant.getMenus().clear();
//        Because menus don't exist without a restaurant it is different tehn the payments that are
//        already in a made table.
            for (Menu m : restaurant.getMenus()){
                Menu newMenu = new Menu();
                newMenu.setDish(m.getDish());
                newMenu.setPrice(m.getPrice());
                newMenu.setRestaurant(updateRestaurant);

                updateRestaurant.getMenus().add(newMenu);

            }
            }
        return restaurantRepository.save(updateRestaurant);
    }

    @Transactional
    @Override
    public void deleteAll() {
     restaurantRepository.deleteAll();
    }

}
