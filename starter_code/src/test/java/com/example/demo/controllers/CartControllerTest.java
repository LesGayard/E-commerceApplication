package com.example.demo.controllers;


import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static com.example.demo.TestUtils.createItem;
import static com.example.demo.TestUtils.createUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CartControllerTest {

    private CartController cartController;

    private CartRepository cartRepository = mock(CartRepository.class);

    private UserRepository userRepository = mock(UserRepository.class);

    private ItemRepository itemRepository = mock(ItemRepository.class);

    private final Logger logger = LoggerFactory.getLogger(CartControllerTest.class);

    @Before
    public void setup(){
        this.cartController = new CartController();
        TestUtils.injectObject(this.cartController,"cartRepository", this.cartRepository);
        TestUtils.injectObject(this.cartController,"userRepository",this.userRepository);
        TestUtils.injectObject(this.cartController,"itemRepository", this.itemRepository);

    }

    @Test
    public void addToCartSuccess(){

        when(userRepository.findByUsername("leslie")).thenReturn(createUser());
        when(itemRepository.findById(any())).thenReturn(Optional.of(createItem(1)));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("leslie");
        request.setItemId(1L);
        request.setQuantity(5);

        logger.info("request name : " + request.getUsername() + " request item ID : " + request.getItemId() + " request quantity : " + request.getQuantity());

        final ResponseEntity<Cart> response = this.cartController.addTocart(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCodeValue());

    }

    @Test
    public void addTocartUserNotFound(){
        when(userRepository.findByUsername("leslie")).thenReturn(createUser());
        when(itemRepository.findById(any())).thenReturn(Optional.of(createItem(1)));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("Athena");
        request.setItemId(3L);
        request.setQuantity(5);

        logger.info("user : " + request.getUsername() + " Item ID: " + request.getItemId() + " Quantity : " + request.getQuantity());
        final ResponseEntity<Cart> response = this.cartController.addTocart(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(404, response.getStatusCodeValue());
    }


    @Test
    public void addToCartItemNotPresent(){
        when(userRepository.findByUsername("leslie")).thenReturn(createUser());

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("leslie");
        request.setQuantity(0);

        logger.info("user : " + request.getUsername() + " Item ID : " + request.getItemId() + " Quantity : " + request.getQuantity());
        final ResponseEntity<Cart> response = this.cartController.addTocart(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void removeFromCartSuccess(){
        when(userRepository.findByUsername("leslie")).thenReturn(createUser());
        when(itemRepository.findById(any())).thenReturn(Optional.of(createItem(1)));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("leslie");
        request.setItemId(1l);
        request.setQuantity(5);

        logger.info("user : " + request.getUsername() + " Item ID : " + request.getItemId() + " Quantity : " + request.getQuantity());

        final ResponseEntity<Cart> response = this.cartController.removeFromcart(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCodeValue());

    }

    @Test
    public void removeFromCartUserNotFound(){
        logger.info("The User must not exist , user must be null ! ");
        when(itemRepository.findById(any())).thenReturn(Optional.of(createItem(1)));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(1L);
        request.setQuantity(5);

        logger.info("User : " + request.getUsername() + " Item ID : " + request.getItemId() + " Quantity : " + request.getQuantity());

        final ResponseEntity<Cart> response = this.cartController.removeFromcart(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void removeFromCartItemNotPresent(){
        when(userRepository.findByUsername("athena")).thenReturn(createUser());
        logger.info("The cart contains no Item ; Item must be null");

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("athena");
        request.setQuantity(5);

        logger.info("User : " + request.getUsername() + " Item ID : " + request.getItemId() + " Quantity : " + request.getQuantity() );

        final ResponseEntity<Cart> response = this.cartController.removeFromcart(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(404, response.getStatusCodeValue());
    }

}
