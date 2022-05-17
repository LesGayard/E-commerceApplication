package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.demo.TestUtils.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderController orderController;

    private UserRepository userRepository = mock(UserRepository.class);

    private OrderRepository orderRepository = mock(OrderRepository.class);

    Logger logger = LoggerFactory.getLogger(OrderControllerTest.class);

    @Before
    public void setUp(){
        this.orderController = new OrderController();
        TestUtils.injectObject(this.orderController,"userRepository", this.userRepository);
        TestUtils.injectObject(this.orderController,"orderRepository", this.orderRepository);

    }

    @Test
    public void submitSuccess(){
        when(this.userRepository.findByUsername("leslie")).thenReturn(createUser());
        logger.info("User must be found ! ");

        Cart cartToCreate = createCart(createUser());
        logger.info("User from cart created : " + cartToCreate.getUser().toString());

        UserOrder order = UserOrder.createFromCart(cartToCreate);


        when(this.orderRepository.save(order)).thenReturn(createOrder());

        final ResponseEntity<UserOrder> response = this.orderController.submit("leslie");
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void submitUserNotFound(){

        logger.error("This User must not exist ! ");

        Cart cartToCreate = createCart(createUser());
        UserOrder order = UserOrder.createFromCart(cartToCreate);
        when(this.orderRepository.save(order)).thenReturn(createOrder());

        final ResponseEntity<UserOrder> response = this.orderController.submit("athena");
        Assert.assertNotNull(response);
        Assert.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void getOrdersForUsersSuccess(){
        when(this.userRepository.findByUsername("leslie")).thenReturn(createUser());

      //  List<UserOrder> orders = createOrders();

        final ResponseEntity<List<UserOrder>>response = this.orderController.getOrdersForUser("leslie");
        Assert.assertNotNull(response);
        Assert.assertEquals(200,response.getStatusCodeValue());

    }
}
