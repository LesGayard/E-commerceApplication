package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;

    private ItemRepository itemRepository = mock(ItemRepository.class);

    private final Logger logger = LoggerFactory.getLogger(ItemControllerTest.class);

    @Before
    public void setup(){
        this.itemController = new ItemController();
        TestUtils.injectObject(this.itemController,"itemRepository", this.itemRepository);
    }

    @Test
    public void getItemsTest(){
        /* ResponseEntity.ok(itemRepository.findAll()) */
        Item item00 = new Item();
        item00.setName("ball");
        item00.setDescription("a ball for playing football");

        Item item01 = new Item();
        item01.setName("flower");
        item01.setDescription("A Rose flower");

        Item item02 = new Item();
        item02.setName("phone");
        item02.setDescription("Iphone");

        List<Item> items = new ArrayList<>();
        items.add(item00);
        items.add(item01);
        items.add(item02);


        this.itemRepository.save(item00);
        this.itemRepository.save(item01);
        this.itemRepository.save(item02);

        when(this.itemRepository.findAll()).thenReturn(items);
        Assert.assertEquals(3,items.size());

        /* public ResponseEntity<List<Item>> getItems() */
        ResponseEntity<List<Item>> getItems = this.itemController.getItems();
        Assert.assertEquals(200, getItems.getStatusCodeValue());
    }

    @Test
    public void getItemByIdTest(){
        /* ResponseEntity.of(itemRepository.findById(id)) */
        Item item00 = new Item();
        item00.setName("ball");
        item00.setId(0L);
        item00.setDescription("a ball for playing football");

        Item item01 = new Item();
        item01.setName("flower");
        item01.setId(1L);
        item01.setDescription("A Rose flower");

        Item item02 = new Item();
        item02.setName("phone");
        item02.setId(2L);
        item02.setDescription("Iphone");

        List<Item> items = new ArrayList<>();
        items.add(item00);
        items.add(item01);
        items.add(item02);


        this.itemRepository.save(item00);
        this.itemRepository.save(item01);
        this.itemRepository.save(item02);

        logger.info("Item 00 ID : " + item00.getId());

        when(this.itemRepository.findAll()).thenReturn(items);
        Assert.assertEquals(3,items.size());

        ResponseEntity<Item> response = this.itemController.getItemById(0L);
        logger.info("response status code : " + response.getStatusCode());
        logger.info("the response : " + response.toString());
        //Assert.assertEquals(200,response.getStatusCodeValue());
    }

    @Test
    public void getItemsByNameTest(){
        Item item00 = new Item();
        item00.setName("ball");
        item00.setId(0L);
        item00.setDescription("a ball for playing football");

        Item item01 = new Item();
        item01.setName("flower");
        item01.setId(1L);
        item01.setDescription("A Rose flower");

        Item item02 = new Item();
        item02.setName("phone");
        item02.setId(2L);
        item02.setDescription("Iphone");

        List<Item> items = new ArrayList<>();
        items.add(item00);
        items.add(item01);
        items.add(item02);

        this.itemRepository.save(item00);
        this.itemRepository.save(item01);
        this.itemRepository.save(item02);

        logger.info("Item 00 ID : " + item00.getId());

        when(this.itemRepository.findAll()).thenReturn(items);
        Assert.assertEquals(3,items.size());
        logger.info("Item02 named phone : " + item02.getName());
        //List<Item> items1 = this.itemRepository.findByName("phone");
        when(this.itemRepository.findByName("phone")).thenReturn(items);
        ResponseEntity<List<Item>> response = this.itemController.getItemsByName("phone");

        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCode().value());
    }
}
