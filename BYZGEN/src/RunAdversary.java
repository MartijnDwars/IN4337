import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RunAdversary {
	public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException,
			InterruptedException, NotBoundException {
		if (args.length != 3) {
			System.out.println("Usage: RunProcess name total initial");
			System.exit(-1);
		}

		int i = Integer.parseInt(args[0]);
		int n = Integer.parseInt(args[1]);
		int b = Integer.parseInt(args[2]);

		Adversary process = new Adversary(i, n, b);

		Naming.bind("rmi://localhost:1099/p" + i, process);

		log(i, "Adversary bound");

		Registry registry = LocateRegistry.getRegistry();
		while (registry.list().length < n)
			Thread.sleep(10);

		// Link other processes
		for (int j = 0; j < n; j++) {
			process.setActor(j, (Actor) Naming.lookup("rmi://localhost:1099/p" + j));
		}

		log(i, "Start coordinating");

		// Run the coordinate function in a loop until it returns 0
		while (process.coordinate() != 0) {
			// Deliberately empty. Coordinate until you're done..
			log(i, "Next iteration");
		}

		log(i, "Done");
	}

	private static void log(int i, String m) {
		System.err.println("a" + i + ": " + m);
	}
}
