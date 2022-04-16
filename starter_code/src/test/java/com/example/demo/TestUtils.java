package com.example.demo;

import java.lang.reflect.Field;

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
}
