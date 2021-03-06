package com.example.demo;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/* USED FOR INJECT OBJECTS IN THE TEST CLASSES AS AUTOWIRED */
public class TestUtils {

    public static void injectObject(Object target, String fieldName, Object toInject){

        /* The flag */
        boolean wasPrivate = false;

        try {
            Field field =  target.getClass().getDeclaredField(fieldName);

            if(!field.isAccessible()){
                field.setAccessible(true);
                wasPrivate = true;
            }
            field.set(target,toInject);

            if(wasPrivate){
                field.setAccessible(false);
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


    }

    public static User createUser(){

        User user = new User();
        user.setUsername("leslie");
        user.setId(1L);
        user.setPassword("password");
        user.setCart(createCart(user));

        return user;
    }

    public static Cart createCart(User user) {

        Cart cart = new Cart();
        cart.setId(1L);
        List<Item> items = createItems();
        cart.setItems(createItems());
        cart.setTotal(items.stream().map(item -> item.getPrice()).reduce(BigDecimal::add).get());
        cart.setUser(user);

        return cart;
    }

    public static List<Item> createItems() {

        List<Item> items = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {
            items.add(createItem(i));
        }

        return items;
    }



    public static Item createItem(long id){

        Item item = new Item();
        item.setId(id);
        item.setPrice(BigDecimal.valueOf(id * 1.2));
        item.setName("Item " + item.getId());
        item.setDescription("Description ");

        return item;
    }


    public static List<UserOrder> createOrders(){
        List<UserOrder> orders = new ArrayList<>();

        IntStream.range(0,2).forEach(i -> {
            UserOrder order = new UserOrder();
            Cart cart = createCart(createUser());

            order.setItems(cart.getItems());
            order.setTotal(cart.getTotal());
            order.setUser(createUser());
            order.setId(Long.valueOf(i));

            orders.add(order);
        });
        return orders;
    }

    public static UserOrder createOrder(){
        UserOrder order = new UserOrder();
        Cart cart = createCart(createUser());

        order.setItems(cart.getItems());
        order.setUser(cart.getUser());
        order.setTotal(cart.getTotal());

        return order;
    }
}
