package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IRemoteMethod extends Remote {
	public String serverReady()throws RemoteException;
	public ArrayList<String> executeBatch(ArrayList<String> batch)throws RemoteException;

}
