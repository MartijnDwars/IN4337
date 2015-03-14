import java.util.Random;

public class Adversary extends Process {
	/**
	 * Construct process
	 *
	 * @param i The process ID
	 * @param n The total number of processes
	 */
	public Adversary(int i, int n) {
		super(i, n, 0);
	}

	/**
	 * An adversary has already made a decision
	 *
	 * @return boolean Flag indicating if the adversy has made a decision
	 */
	@Override
	public boolean hasDecided() {
		return true;
	}

	/**
	 * An adversary broadcasts a random vote to all other processes
	 */
	@Override
	public void broadcast() {
		for (int j = 0; j < n; j++) {
			processes[j].receive(i, round, new Random().nextInt(2));
		}
	}
}
