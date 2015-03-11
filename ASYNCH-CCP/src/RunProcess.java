import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RunProcess {
	public static void main(String[] args) throws RemoteException, AlreadyBoundException, InterruptedException, NotBoundException, MalformedURLException {
		if (args.length != 2) {
			System.out.println("Usage: RunProcess name total");
			System.exit(-1);
		}

		int i = Integer.parseInt(args[0]);
		int n = Integer.parseInt(args[1]);

		Process process = new Process(i, n);

		// Wait 2 sec. so all registers can register themselves with the rmiregistry
		Registry registry = LocateRegistry.getRegistry();
		while (registry.list().length < 2)
			Thread.sleep(10);

		// Link registers on the process
		for (int j = 0; j < n; j++) {
			process.setRegister(j, (RegisterInterface) Naming.lookup("rmi://localhost:1099/r" + j));
		}

		log(i, "Start coordinating");

		int iterations = 0;

		// Run the coordinate function in a loop until it returns 0
		while (process.coordinate() != 0) {
			// Deliberately empty. Coordinate until you're done..
			log(i, "Next iteration");
			iterations++;
		}

		log(i, "Done");
		System.out.println(" " + iterations + " ");
		System.exit(0);
	}

	private static void log(int i, String m) {
		System.err.println(i + ": " + m);
	}
}
