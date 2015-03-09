import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Random;

public class Process extends UnicastRemoteObject implements Actor, Remote {
	private int i;
	private int b;
	private int n;
	private int d;
	private Actor[] actors;
	private boolean receive;
	private int[] receivedVotes;
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
		this.receive = true;
		this.receivedVotes = new int[n];
		Arrays.fill(receivedVotes, -1);
		this.random = new Random();
	}

	public void setActor(int i, Actor actor) {
		actors[i] = actor;
	}

	public int coordinate() throws RemoteException {
		receive = true;

		// Step 3: Broadcast vote
		for (int j = 0; j < n; j++) {
			if (j == i) {
				continue;
			}

			actors[j].receive(i, b);
		}

		receivedVotes[i] = b;

		// Step 4: Receive votes from all other processors
		while (Arrays.stream(receivedVotes).anyMatch(v -> v == -1)) {
			// Deliberately empty; loop until you've received all votes
		}
		receive = false;

		// Step 5: Compute majority value among votes received
		int maj;
		if (Arrays.stream(receivedVotes).sum() < n/2) {
			maj = 0;
		} else {
			maj = 1;
		}

		// Step 6: Compute number of occurences of maj among votes received
		long tally = Arrays.stream(receivedVotes).filter(v -> v == maj).count();

		// Step 7: Compute threshold
		int threshold;
		if (random.nextInt(2) == 1) {
			threshold = 5*n/8+1;
		} else {
			threshold = 3*n/4+1;
		}

		// Step 8: Compute vote
		int vote;
		if (tally >= threshold) {
			vote = maj;
		} else {
			vote = 0;
		}

		// Step 9: Set d permanently
		if (tally >= 7*n/8) {
			d = maj;

			log("Permanently chosen for " + d);

			return 0;
		}

		return -1;
	}

	@Override
	public void receive(int sender, int vote) throws RemoteException {
		while (!receive) {
			// Block until we are allowed to receive
		}

		receivedVotes[sender] = vote;

		log("Recorded vote " + vote + " from " + sender);
	}

	private void log(String m) {
		System.out.println(i + ": " + m);
	}
}
