import static org.junit.Assert.*;

import org.junit.Test;

public class ProcessTest {

	@Test
	public void testMajorityEvenAllZero() throws Exception {
		// Arrange
		Process p = new Process(0, 4, 2);

		// Act
		int maj = p.majority(new int[] {0, 0, 0, 0});

		// Assert
		assertEquals(0, maj);
	}

	@Test
	public void testMajorityEvenOne() throws Exception {
		// Arrange
		Process p = new Process(0, 4, 2);

		// Act
		int maj = p.majority(new int[] {0, 0, 0, 1});

		// Assert
		assertEquals(0, maj);
	}

	@Test
	public void testMajorityEvenBiasedToZero() throws Exception {
		// Arrange
		Process p = new Process(0, 4, 2);

		// Act
		int maj = p.majority(new int[] {0, 0, 1, 1});

		// Assert
		assertEquals(0, maj);
	}

	@Test
	public void testMajorityOddZero() throws Exception {
		// Arrange
		Process p = new Process(0, 5, 2);

		// Act
		int maj = p.majority(new int[] {0, 0, 0, 1, 1});

		// Assert
		assertEquals(0, maj);
	}

	@Test
	public void testMajorityOddOne() throws Exception {
		// Arrange
		Process p = new Process(0, 5, 2);

		// Act
		int maj = p.majority(new int[] {0, 0, 1, 1, 1});

		// Assert
		assertEquals(1, maj);
	}

}
