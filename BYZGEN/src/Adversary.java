import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Adversary extends UnicastRemoteObject implements Actor, Remote {
	private int i;
	private int b;
	private int n;
	private int round;
	private int d;
	private Actor[] actors;
	private ArrayList<int[]> receivedVotes;
	private Random random;


	/**
	 * Construct process
	 *
	 * @param i The process ID
	 * @param n The total number of processes
	 * @param b A process' local value
	 */
	public Adversary(int i, int n, int b) throws RemoteException {
		super();

		this.i = i;
		this.b = b;
		this.n = n;
		this.d = -1;
		this.actors = new Actor[n];
		this.actors[i] = this;
		this.receivedVotes = new ArrayList<>();
		this.random = new Random();
		this.round = 0;

		// Loop een voor met vote storage
		int[] a = new int[this.n];
		Arrays.fill(a, -1);
		this.receivedVotes.add(a);

		int[] a2 = new int[this.n];
		Arrays.fill(a2, -1);
		this.receivedVotes.add(a2);
	}

	public void setActor(int i, Actor actor) {
		actors[i] = actor;
	}

	private int[] getVotesForRound(int round) {
		while (receivedVotes.size() <= round) {
			int[] a = new int[this.n];
			Arrays.fill(a, -1);

			this.receivedVotes.add(a);
		}

		return this.receivedVotes.get(round);
	}

	private boolean roundCompleted(int[] round) {
		for (int vote : round) {
			if (vote == -1) {
				return false;
			}
		}
		return true;
	}

	public int coordinate() throws RemoteException, InterruptedException {
		// Step 3: Broadcast vote
		broadcast();

		// Step 4: Receive votes from all other processors
		while (!roundCompleted(receivedVotes.get(round))) {
			Thread.sleep(10);
			// Deliberately empty; loop until you've received all votes
		}

		log("Got all votes");

		// Loop een voor met vote storage
		int[] a = new int[this.n];
		Arrays.fill(a, -1);
		this.receivedVotes.add(a);

		round++;
		return -1;
	}

	private void broadcast() throws RemoteException {
		for (int j = 0; j < n; j++) {
			actors[j].receive(i, round, random.nextInt(2));
		}
	}

	@Override
	public void receive(int sender, int round, int vote) throws RemoteException {
		if (receivedVotes.get(round) == null) {
			throw new RuntimeException("Kan dit? Sender: " + sender + ", round: " + round);
		}

		receivedVotes.get(round)[sender] = vote;

		log("Recorded vote " + vote + " from " + sender + " in round " + round);
	}

	private void log(String m) {
		System.err.println(i + ": " + m);
	}
}
