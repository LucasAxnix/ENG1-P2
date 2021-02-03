package com.hardgforgif.dragonboatracing.UI;

import org.junit.Test;
import static org.junit.Assert.*;

public class MenuUITest {

    @Test
    public void aPassedTest() throws Exception{
        System.out.println("Hello, test has run!");
        assertEquals(0,0);
    }
    @Test
    public void aFailedTest() throws Exception{
        System.out.println("Hello, test has run!");
        assertEquals(0,1);
    }
    @Test
    public void anotherPassedTest() throws Exception{
        System.out.println("Hello, test has run!");
        assertEquals(6,add(2,4));
    }

    public int add(int i, int j){
        return i + j;
    }
}
