package com.example.demo.controllers;


import com.example.demo.TestUtils;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class CartControllerTest {

    private CartController cartController;

    private UserRepository userRepository = mock(UserRepository.class);

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setup(){
        this.cartController = new CartController();
        TestUtils.injectObject(this.cartController,"userRepository",this.userRepository);
        TestUtils.injectObject(this.cartController,"itemRepository", this.itemRepository);
    }

    @Test
    public void addToCartTest(){

    }
}
