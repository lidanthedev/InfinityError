package me.lidan.infinityerror.Util;

import junit.framework.TestCase;

public class FunctionsTest extends TestCase {

    public void testChanceOf() {
        assertTrue(Functions.chanceOf(100));
    }

    public void testChanceOf2() {
        assertFalse(Functions.chanceOf(0));
    }

    public void testGetNumberFormat(){
        assertEquals("1,000,000", Functions.getNumberFormat(1000000));
    }
}