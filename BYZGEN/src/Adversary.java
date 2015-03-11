import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Adversary extends UnicastRemoteObject implements Actor, Remote {
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
	public Adversary(int i, int n, int b) throws RemoteException {
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

		int size = getVotesForRound(round).size();
		// Step 4: Receive votes from all other processors
		while (size < n) {
			Thread.sleep(10);
			size = getVotesForRound(round).size();
			// Deliberately empty; loop until you've received all votes
		}

		log("Got all votes");

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
		getVotesForRound(round).put(sender, vote);

		log("Recorded vote " + vote + " from " + sender + " in round " + round);
	}

	private void log(String m) {
		System.err.println("a" + i + ": " + m);
	}
}
