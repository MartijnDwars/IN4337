import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RegisterInterface extends Remote {
	public boolean lock() throws RemoteException;
	public void unlock() throws RemoteException;

	public int getValue() throws RemoteException;
	public void setValue(int value) throws RemoteException;

	public int getTimestamp() throws RemoteException;
	public void setTimestamp(int timestamp) throws RemoteException;
}
