import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Register extends UnicastRemoteObject implements RegisterInterface, Remote {
	private int i;
	private int value;
	private int timestamp;
	private boolean locked;

	/**
	 * @param i Register index
	 * @param value Initial value
	 */
	public Register(int i, int value) throws RemoteException {
		super();

		this.i = i;
		this.value = value;
		this.timestamp = 0;
		this.locked = false;
	}

	/**
	 * Acquire a lock
	 *
	 * @return boolean True iff the lock was given to the requesting process
	 */
	public synchronized boolean lock() {
		if (!locked) {
			locked = true;
		}

		return locked;
	}

	public synchronized void unlock() {
		locked = false;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
}
