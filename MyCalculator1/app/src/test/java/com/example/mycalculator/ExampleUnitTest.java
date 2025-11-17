package com.example.mycalculator;

import org.junit.Test;
import static org.junit.Assert.*;

public class ExampleUnitTest {

    // PASSING TEST: Addition
    @Test
    public void testAdditionPass() {
        assertEquals("5", MathEval.eval("2+3"));
    }

    // FAILING TEST: Addition (intentional)
    @Test
    public void testAdditionFail() {
        assertEquals("10", MathEval.eval("2+3"));
    }

    // PASSING TEST: Division
    @Test
    public void testDivisionPass() {
        assertEquals("2", MathEval.eval("10/5"));
    }

    // FAILING TEST: Division (intentional)
    @Test
    public void testDivisionFail() {
        assertEquals("5", MathEval.eval("10/0"));
    }
}
