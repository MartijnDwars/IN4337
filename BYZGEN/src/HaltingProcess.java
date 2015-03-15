/**
 * A HaltingProcess is a Process that halts one round after it made a decision.
 */
public class HaltingProcess extends Process {
	/**
	 * Indicate if this process has used its final round
	 */
	protected boolean finalRound;

	/**
	 * Construct process
	 *
	 * @param i The process ID
	 * @param n The total number of processes
	 * @param b A process' local value
	 */
	public HaltingProcess(int i, int n, int b) {
		super(i, n, b);

		finalRound = false;
	}

	@Override
	public void broadcast() {
		// If we've had our final round, don't do anything. We're halted.
		if (finalRound) {
			return;
		}

		// If we've decided but not had our final round, then this is our final round.
		if (hasDecided() && !finalRound) {
			finalRound = true;
		}

		super.broadcast();

		// Show that we're halted now.
		if (finalRound) {
			log("Halted");
		}
	}

	@Override
	public boolean isHalted() {
		return finalRound;
	}
}
