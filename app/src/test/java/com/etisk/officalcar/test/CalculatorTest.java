package com.etisk.officalcar.test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * <p>Title:CalculatorTest</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright(c)2016</p>
 * User: 刘开峰
 * Date: 2016-12-26
 * Time: 13:45
 */
public class CalculatorTest {

    private Calculator mCalculator;

    @Before
    public void setUp() throws Exception {
        mCalculator = new Calculator();
    }

    @Test
    public void sum() throws Exception {
        //expected: 6, sum of 1 and 5
        assertEquals(6d, mCalculator.sum(1d, 5d), 0);
    }

    @Test
    public void substract() throws Exception {
        assertEquals(1d, mCalculator.substract(5d, 4d), 0);
    }

    @Test
    public void divide() throws Exception {
        assertEquals(4d, mCalculator.divide(20d, 5d), 0);
    }

    @Test
    public void multiply() throws Exception {
        assertEquals(10d, mCalculator.multiply(2d, 5d), 0);
    }

}