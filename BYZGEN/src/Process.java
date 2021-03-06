import java.util.ArrayList;
import java.util.Arrays;

public class Process implements Actor {
	protected int i;
	protected int b;
	protected int n;
	protected int round;
	protected int d;
	protected Actor[] processes;
	protected ArrayList<int[]> receivedVotes;


	/**
	 * Construct process
	 *
	 * @param i The process ID
	 * @param n The total number of processes
	 * @param b A process' local value
	 */
	public Process(int i, int n, int b) {
		this.i = i;
		this.b = b;
		this.n = n;
		this.d = -1;
		this.processes = new Process[n];
		this.processes[i] = this;
		this.receivedVotes = new ArrayList<>();
		this.round = 0;

		// Add array of -1 votes for 0th round
		int[] a = new int[this.n];
		Arrays.fill(a, -1);
		this.receivedVotes.add(a);
	}

	public void setProcess(int i, Actor process) {
		processes[i] = process;
	}

	private boolean roundCompleted(int[] round) {
		for (int vote : round) {
			if (vote == -1) {
				return false;
			}
		}
		return true;
	}

	public void broadcast() {
		for (int j = 0; j < n; j++) {
			processes[j].receive(i, round, b);
		}
	}

	public boolean hasDecided() {
		return d != -1;
	}

	public int getDecision() {
		return d;
	}

	public void coordinate(int coin) {
		if (!hasDecided()) {
			// Step 5: Compute majority value among votes received
			int maj = majority(receivedVotes.get(round));

			// Step 6: Compute number of occurences of maj among votes received
			long tally = Arrays.stream(receivedVotes.get(round)).filter(v -> v == maj).count();

			// Step 7: Compute threshold
			int threshold = coin == 1 ? getL() : getH();

			// Step 8: Compute vote
			b = tally >= threshold ? maj : 0;

			// Step 9: Set d permanently
			if (tally >= getG()) {
				d = maj;
				b = maj;

				log("Permanently chosen for " + d + " in round " + round);
			}
		}

		// Add array of -1 votes for next round
		int[] a = new int[this.n];
		Arrays.fill(a, -1);
		this.receivedVotes.add(a);

		round++;
	}

	protected int getL() {
		return 5 * n / 8 + 1;
	}

	protected int getH() {
		return 3 * n / 4 + 1;
	}

	protected int getG() {
		return 7 * n / 8;
	}

	/**
	 * Compute majority of given votes. If n is even there may not be a majority. In that case, 0 is chosen as
	 * majority.
	 *
	 * @param votes Array of votes
	 * @return The majority vote, which is either 0 or 1
	 */
	public int majority(int[] votes) {
		return Arrays.stream(votes).sum() <= n / 2 ? 0 : 1;
	}

	public void receive(int sender, int round, int vote) {
		if (receivedVotes.get(round) == null) {
			throw new RuntimeException("Kan dit? Sender: " + sender + ", round: " + round);
		}

		receivedVotes.get(round)[sender] = vote;

		log("Recorded vote " + vote + " from " + sender + " in round " + round);
	}

	/**
	 * A process is never halted, according to the ByzGen algorithm in Motwani
	 *
	 * @return boolean Flag indicating if the process is halted
	 */
	@Override
	public boolean isHalted() {
		return false;
	}

	protected void log(String m) {
		System.err.println(i + ": " + m);
	}
}
