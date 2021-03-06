import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

public class Process extends UnicastRemoteObject implements Remote {
	/**
	 * Array containing a reference to each register
	 */
	private RegisterInterface[] registers;

	/**
	 * Index of the current register
	 */
	private int r;

	/**
	 * Process name
	 */
	private int i;

	private int T;

	private int B;

	/**
	 * @param i Process index
	 * @param n Total number of processes
	 */
	public Process(int i, int n) throws RemoteException {
		super();

		this.registers = new RegisterInterface[n];
		this.r = new Random().nextInt(n);
		this.i = i;
		this.T = 0;
		this.B = 0;
	}

	public void setRegister(int index, RegisterInterface register) {
		registers[index] = register;
	}

	public int coordinate() throws RemoteException {
		// Step 1
		while (!registers[r].lock()) {
			// Deliberately empty. Block until you've acquired the lock.
		}

		int R = registers[r].getValue();
		int t = registers[r].getTimestamp();

		// Step 2
		if (R == Integer.MAX_VALUE) {
			return 0;
		} else if (T < t) {
			T = t;
			B = R;
		} else if (T > t) {
			registers[r].setValue(Integer.MAX_VALUE);

			return 0;
		} else if (T == t && R == 0 && B == 1) {
			registers[r].setValue(Integer.MAX_VALUE);

			return 0;
		} else {
			T++;
			t++;
			B = new Random().nextInt(2);

			registers[r].setTimestamp(t);
			registers[r].setValue(B);
		}

		// Step 3
		registers[r].unlock();
		r = 1-r;

		return -1;
	}
}
