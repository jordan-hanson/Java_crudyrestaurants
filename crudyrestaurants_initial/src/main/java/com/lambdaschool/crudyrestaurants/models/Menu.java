package com.lambdaschool.crudyrestaurants.models;


import javax.persistence.*;

@Entity
@Table(name = "menus")
public class Menu {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) private long id;
    private long menuid;

    private String dish;

    private double price;

    @ManyToOne
    @JoinColumn(name = 'restaurantid', nullable = false)
    private Restaurant restaurant;

    public Menu() {

    }

    public Menu(String dish, double price) {
        this.dish = dish;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMenuid() {
        return menuid;
    }

    public void setMenuid(long menuid) {
        this.menuid = menuid;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", menuid=" + menuid +
                ", dish='" + dish + '\'' +
                ", price=" + price +
                '}';
    }
}
