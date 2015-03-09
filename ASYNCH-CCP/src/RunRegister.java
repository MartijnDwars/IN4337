import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class RunRegister {
	public static void main(String[] args) throws RemoteException, AlreadyBoundException, InterruptedException, MalformedURLException {
		if (args.length != 2) {
			System.out.println("Usage: RunRegister name total");
			System.exit(-1);
		}

		int i = Integer.parseInt(args[0]);
		int n = Integer.parseInt(args[1]);

		Register register = new Register(i, 0);

		Naming.bind("rmi://localhost:1099/r" + i, register);

		log(i, "Register bound");
	}

	private static void log(int i, String m) {
		System.out.println(i + ": " + m);
	}
}
