package com.ubs.maven;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StringAccumulatorTest {

	private StringAccumulator fixture;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setUp() throws Exception {
		fixture = new StringAccumulator();
	}
	
	@Test
	public void testAddWithEmptyString() throws Exception {
		assertEquals(0, fixture.add(""));
	}
	
	@Test
	public void testAddWithoutDelimiter() throws Exception {
		assertEquals(5, fixture.add("5"));
	}

	@Test
	public void testAddWithEmptyLineDelimiter() throws Exception {
		assertEquals(10, fixture.add("1\n4,5"));
	}
	
	@Test
	public void testAddWithCommaDelimiter() throws Exception {
		assertEquals(6, fixture.add("1,2,3"));
	}
	
	@Test
	public void testAddWithChangedDelimiter() throws Exception {
		assertEquals(3, fixture.add("//;\n1;2"));
	}
	
	@Test
	public void testAddWithMultiLengthDelimiter() throws Exception {
		assertEquals(6, fixture.add("//***\n1***2***3"));
	}
	
	@Test
	public void testAddWithEmptyLineMultiLengthDelimiter() throws Exception {
		assertEquals(15, fixture.add("//***\n1***2***3\n4\n5"));
	}
	
	@Test
	public void testAddWithSameMultiLengthDelimiter() throws Exception {
		assertEquals(15, fixture.add("//*|**|***|****\n1**2*3***4****5"));
	}
	
	@Test
	public void testAddWithMultipleDelimiter() throws Exception {
		assertEquals(6, fixture.add("//*|%\n1*2%3"));
	}
	
	@Test
	public void testAddWithMultipleDelimiterMultipleLength() throws Exception {
		assertEquals(15, fixture.add("//*|%|&&|$$$\n1%2*3&&4$$$5"));
	}
	
	@Test
	public void testAddWithMultipleDelimiterMultipleLengthWithEmptyLine() throws Exception {
		assertEquals(21, fixture.add("//*|%|&&|$$$\n1%2*3&&4\n5$$$6"));
	}
	
	@Test
	public void testAddWithMultipleDelimiterMultipleLengthWith1000() throws Exception {
		assertEquals(1015, fixture.add("//*|%|&&|$$$\n1%2*3&&4*5$$$1000"));
	}
	
	@Test
	public void testAddWithMultipleDelimiterMultipleLengthGreaterThan1000() throws Exception {
		assertEquals(15, fixture.add("//*|%|&&|$$$\n1%2*3&&4\n5$$$1001"));
	}
	
	@Test
	public void testCreateDelimitersArray() {
		final Object [] result = fixture.createDelimitersArray("\r?\n", ",");
		assertEquals("\r?\n", result[0]);
		assertEquals(",", result[1]);
	}
	
	@Test
	public void testCreateDelimitersArrayMultipleDelimiter() {
		final Object [] result = fixture.createDelimitersArray("\r?\n", "*|%|$");
		assertEquals("\r?\n", result[0]);
		assertEquals("*", result[1]);
		assertEquals("%", result[2]);
		assertEquals("$", result[3]);
	}
	
	@Test
	public void testAddWithMultipleDelimiterWithNegativeValue() throws Exception {
		exception.expect(Exception.class);
		exception.expectMessage("negatives not allowed -2");
		fixture.add("//*|%\n1*-2%3");
	}
	
	@Test
	public void testAddWithMultipleDelimiterWithMultipleNegativeValue() throws Exception {
		exception.expect(Exception.class);
		exception.expectMessage("negatives not allowed -2,-3");
		fixture.add("//*|%\n1*-2%-3*4");
	}
}
