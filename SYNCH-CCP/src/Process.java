import java.util.Random;

public class Process {
	/**
	 * Process identifier
	 */
	private final int i;

	/**
	 * Array of registers
	 */
	private Register[] registers;

	/**
	 * Current register
	 */
	private int r;

	/**
	 * Local variable B_i
	 */
	private int B;

	/**
	 * Initialize process
	 *
	 * @param i Process index
	 * @param registers Array of registers
	 */
	public Process(int i, Register[] registers) {
		this.i = i;
		this.r = i;
		this.B = 0;
		this.registers = registers;
	}

	/**
	 * @return If the halting condition is satisfied 0, otherwise 1
	 */
	public int coordinate() {
		Register register = registers[r];

		// Step 1
		int R = register.getValue();

		System.err.println("P" + i + ": B = " + B + ", R = " + R);

		// Step 2
		if (R == Integer.MAX_VALUE) {
			return 0;
		} else if (R == 0 && B == 1) {
			register.setValue(Integer.MAX_VALUE);

			return 0;
		} else {
			B = new Random().nextInt(2);

			register.setValue(B);
		}

		// Step 3
		r = 1-r;

		return -1;
	}
}
