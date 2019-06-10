package engsoft.dellinhostore.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("CalculatorExample")
public class jUnit5Test {

	@Test
	void correctExample() {
		assertEquals(10, 10);
	}
	
	@Test
	void wrongExample() {
		assertNotEquals(11, 10);
	}
}
