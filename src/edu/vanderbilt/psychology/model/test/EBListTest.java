package edu.vanderbilt.psychology.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.vanderbilt.psychology.model.EBList;


/**
 * 
 * @author hamiltont
 * 
 */
public class EBListTest {

	@Test
	public void testListNaming() {
		EBList<String> list = new EBList<String>("Some name");

		assertEquals("Some name", list.getName());

		list.setName("New");

		assertEquals("New", list.getName());
	}

	@Test
	public void testLocationPointer() {
		EBList<String> list = new EBList<String>("name");

		assertEquals(list.getLocation(), -1);

		try {
			list.setLocation(10);
			fail("We should not have been able to do this");
		} catch (Exception e) {	
			// We can't set location 10, we only have 1 element
		}
		
		try {
			list.setLocation(-1);
			fail("We should not have been able to do this");
		} catch (Exception e) {	
			// We can't set location -1, that's not valid
		}
		
		// etc etc
	}
}
