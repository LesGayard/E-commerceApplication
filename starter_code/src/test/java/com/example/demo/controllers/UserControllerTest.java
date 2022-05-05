package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    private Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    @Before
    public void setup(){
        this.userController = new UserController();
        TestUtils.injectObject(this.userController,"userRepository",this.userRepository);
        TestUtils.injectObject(this.userController,"cartRepository", this.cartRepository);
        TestUtils.injectObject(this.userController, "bCryptPasswordEncoder", this.bCryptPasswordEncoder);
    }

    @Test
    public void createUserTest(){

        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("UsernameTest");
        request.setPassword("passwordTest");
        request.setConfirmPassword("passwordTest");

        when(this.bCryptPasswordEncoder.encode("passwordTest")).thenReturn("HashedPassword");
        final ResponseEntity<User> response = this.userController.createUser(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(200,response.getStatusCodeValue());

        User user = response.getBody();
        Assert.assertNotNull(user);
        Assert.assertEquals(0, user.getId());
        Assert.assertEquals("UsernameTest",user.getUsername());


        Assert.assertEquals("HashedPassword",user.getPassword());
    }

    @Test
    public void getUserByUsernameTest(){
        /* Create the user */
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("usernameToSearch");
        request.setPassword("passwordTest");
        request.setConfirmPassword("passwordTest");
        final ResponseEntity<User> response = this.userController.createUser(request);

        User user = response.getBody();
        /* Find it */
        logger.info("username from User : " + user.getUsername());
       /* User user = userRepository.findByUsername(username); */
        when(this.userRepository.findByUsername("usernameToSearch")).thenReturn(user);
        Assert.assertEquals("usernameToSearch",user.getUsername());
    }

    @Test
    public void findByIdTest(){
        /* Create the user */
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("usernameToSearch");
        request.setPassword("passwordTest");
        request.setConfirmPassword("passwordTest");
        final ResponseEntity<User> response = this.userController.createUser(request);

        User user = response.getBody();

        /* Find it */
        when(this.userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Assert.assertEquals(0,user.getId());
    }
}
