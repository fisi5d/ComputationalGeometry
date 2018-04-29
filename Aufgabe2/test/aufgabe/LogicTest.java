package aufgabe;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import aufgabe.LogicA2;
import aufgabe.Point;


public class LogicTest {

	@Test
	public void calcOneAcreageTest() {
		LogicA2 logic = new LogicA2();
		Point one = new Point(1, 1);
		Point two = new Point(2, 0.5);
		
		System.out.println(logic.calcOneAcreage(one, two));
	}
	
}
