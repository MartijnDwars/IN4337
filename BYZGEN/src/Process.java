import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Process extends UnicastRemoteObject implements Actor, Remote {
	private int i;
	private int b;
	private int n;
	private int round;
	private int d;
	private Actor[] actors;
	private ArrayList<HashMap<Integer, Integer>> receivedVotes;
	private Random random;


	/**
	 * Construct process
	 *
	 * @param i The process ID
	 * @param n The total number of processes
	 * @param b A process' local value
	 */
	public Process(int i, int n, int b) throws RemoteException {
		super();

		this.i = i;
		this.b = b;
		this.n = n;
		this.d = -1;
		this.actors = new Actor[n];
		this.actors[i] = this;
		this.receivedVotes = new ArrayList<HashMap<Integer, Integer>>();
		this.random = new Random();
		this.round = 0;
	}

	public void setActor(int i, Actor actor) {
		actors[i] = actor;
	}

	private HashMap<Integer, Integer> getVotesForRound(int round) {
		while (receivedVotes.size() <= round) {
			this.receivedVotes.add(new HashMap<>(this.n));
		}

		return this.receivedVotes.get(round);
	}

	public int coordinate() throws RemoteException, InterruptedException {
		// Step 3: Broadcast vote
		broadcast();

		// Step 4: Receive votes from all other processors
		while (getVotesForRound(round).size() < n) {
			Thread.sleep(10);
			// Deliberately empty; loop until you've received all votes
		}

		log("Got all votes");

		// Step 5: Compute majority value among votes received
		int maj;
		if (getVotesForRound(round).values().stream().mapToInt(Integer::intValue).sum() < n/2) {
			maj = 0;
		} else {
			maj = 1;
		}

		// Step 6: Compute number of occurences of maj among votes received
		long tally = getVotesForRound(round).values().stream().filter(v -> v == maj).count();

		// Step 7: Compute threshold
		int threshold;
		if (random.nextInt(2) == 1) {
			threshold = 5*n/8+1;
		} else {
			threshold = 3*n/4+1;
		}

		// Step 8: Compute vote
		if (tally >= threshold) {
			b = maj;
		} else {
			b = 0;
		}

		// Step 9: Set d permanently
		if (tally >= 7*n/8) {
			d = maj;

			log("Round " + round + ": Permanently chosen for " + d);

			return 0;
		}

		round++;
		return -1;
	}

	private void broadcast() throws RemoteException {
		for (int j = 0; j < n; j++) {
			actors[j].receive(i, round, b);
		}
	}

	@Override
	public void receive(int sender, int round, int vote) throws RemoteException {
		getVotesForRound(round).put(sender, vote);

		log("Recorded vote " + vote + " from " + sender + " in round " + round);
	}

	private void log(String m) {
		System.out.println(i + ": " + m);
	}
}
