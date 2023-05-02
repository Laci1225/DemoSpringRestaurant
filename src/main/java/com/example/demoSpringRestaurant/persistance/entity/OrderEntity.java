package com.example.demoSpringRestaurant.persistance.entity;

import jakarta.persistence.*;

@Entity
@Table
public class OrderEntity {
    @Id
    @SequenceGenerator(name = "order",sequenceName = "order_sequence",initialValue = 1)
    @GeneratedValue(generator = "order_sequence",strategy = GenerationType.SEQUENCE)
    private Long id;

    enum DrinkType {
        COLA, WATER, JUICE, LEMONADE, TEA
    }

    public enum MealType {
        RICEANDFISH, FISHANDCHIPS, CHICKENANDRICE, CHICKENANDFISH
    }

    private MealType Meal;
    private DrinkType Drink;
    @Transient
    private double price;// = getFoodPrice(Meal) + getDrinkPrice(Drink);

    private double getDrinkPrice(DrinkType drinkType) {
        if (drinkType == null) return 0;
        return switch (drinkType) {
            case COLA -> 3.1;
            case WATER -> 1.0;
            case JUICE,LEMONADE -> 2.6;
            case TEA -> 1.1;
        };
    }

    private double getFoodPrice(MealType mealType) {
        if (mealType == null) return 0;
        return switch (mealType) {
            case RICEANDFISH -> 10.1;
            case FISHANDCHIPS -> 12.1;
            case CHICKENANDFISH -> 20.1;
            case CHICKENANDRICE -> 15.1;
        };
    }

    public OrderEntity(Long id, MealType meal, DrinkType drink, double price) {
        this.id = id;
        Meal = meal;
        Drink = drink;
        this.price = price;
    }

    public OrderEntity(MealType meal, DrinkType drink, double price) {
        Meal = meal;
        Drink = drink;
        this.price = price;
    }
    public OrderEntity() {
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public MealType getMeal() {
        return Meal ;
    }

    public void setMeal(MealType meal) {
        Meal = meal;
    }

    public DrinkType getDrink() {
        return Drink;
    }

    public void setDrink(DrinkType drink) {
        Drink = drink;
    }

    public double getPrice() {
        return getFoodPrice(getMeal())+getDrinkPrice(getDrink());
    }

    /*public void setPrice(double price) {
        this.price = price;
    }*/
}
