package aufgabe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import aufgabe.Logic;
import aufgabe.Point;

class TestLogic {

	@Test
	void testCcw_intersection() {
		Logic logic = new Logic();

		Point one = new Point(1, 1);
		Point two = new Point(-1, -1);

		Point three = new Point(-1, 1);
		Point four = new Point(1, -1);

		assertTrue((logic.ccw(one, two, three) * logic.ccw(one, two, four)) <= 0);

	}

	@Test
	void testCcw_NoIntersection() {
		Logic logic = new Logic();

		Point one = new Point(1, 1);
		Point two = new Point(-1, -1);

		Point three = new Point(-1, 1);
		Point four = new Point(1, 10);

		assertFalse((logic.ccw(one, two, three) * logic.ccw(one, two, four)) <= 0);

	}

	@Test
	void testCcw_CoLinear() {
		Logic logic = new Logic();

		Point one = new Point(1, 1);
		Point two = new Point(-1, -1);

		Point three = new Point(1, 1);
		Point four = new Point(-1, -1);

		assertTrue((logic.ccw(one, two, three) * logic.ccw(one, two, four)) == 0);

	}

	@Test
	void testCcw_ABitCoLinear() {
		Logic logic = new Logic();

		Point one = new Point(1, 1);
		Point two = new Point(-1, -1);

		Point three = new Point(0, 0);
		Point four = new Point(-2, -2);

		assertTrue((logic.ccw(one, two, three) * logic.ccw(one, two, four)) == 0);

	}

	@Test
	void testCcw_SameStartPoint() {
		Logic logic = new Logic();

		Point one = new Point(1, 1);
		Point two = new Point(-1, -1);

		Point three = new Point(1, 1);
		Point four = new Point(2, 2);

		assertTrue((logic.ccw(one, two, three) * logic.ccw(one, two, four)) == 0);

	}

	@Test
	void testCheck() {

		Logic logic = new Logic();

		// same endpoint-startpoint
		Point one = new Point(1, 1);
		Point two = new Point(2, 2);

		Point three = new Point(2, 2);
		Point four = new Point(4, 4);

		assertTrue(logic.check(one, two, three, four));

		three = new Point(-1, -1);
		four = new Point(1, 1);

		assertTrue(logic.check(one, two, three, four));

		// parallel but does not intersect
		one = new Point(1, 1);
		two = new Point(2, 2);
		
		three = new Point(5, 5);
		four = new Point(6, 6);

		assertFalse(logic.check(one, two, three, four));

		one = new Point(3, 3);
		two = new Point(4, 4);

		three = new Point(-1, -1);
		four = new Point(1, 1);

		assertFalse(logic.check(one, two, three, four));

	}

}
