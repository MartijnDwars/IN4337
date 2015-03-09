import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Actor extends Remote {
	void receive(int sender, int vote) throws RemoteException;
}
