public class FoilProcess extends Process {
	/**
	 * Construct process
	 *
	 * @param i The process ID
	 * @param n The total number of processes
	 * @param b A process' local value
	 */
	public FoilProcess(int i, int n, int b) {
		super(i, n, b);
	}

	@Override
	protected int getL() {
		return 3;
	}

	@Override
	protected int getH() {
		return 5;
	}

	@Override
	protected int getG() {
		return 7;
	}
}
