package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setup(){
        this.userController = new UserController();
        TestUtils.injectObject(this.userController,"userRepository",this.userRepository);
        TestUtils.injectObject(this.userController,"cartRepository", this.cartRepository);
        TestUtils.injectObject(this.userController, "bCryptPasswordEncoder", this.bCryptPasswordEncoder);
    }

    @Test
    public void createUser(){

        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("UsernameTest");
        request.setPassword("passwordTest");
        request.setConfirmPassword("passwordTest");

        when(bCryptPasswordEncoder.encode("passwordTest")).thenReturn("HashedPassword");
        final ResponseEntity<User> response = this.userController.createUser(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(200,response.getStatusCodeValue());

        User user = response.getBody();
        Assert.assertNotNull(user);
        Assert.assertEquals(0, user.getId());
        Assert.assertEquals("UsernameTest",user.getUsername());


        Assert.assertEquals("HashedPassword",user.getPassword());
    }
}
