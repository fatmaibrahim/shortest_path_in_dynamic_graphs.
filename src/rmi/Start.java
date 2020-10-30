package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class Start {
	static String ServerAdd;
	static int ServerPort;
	static int numberOfNodes;
	static String[] nodesAdresses;
	static int registryPort;
	public static void main(String[] args) throws RemoteException, MalformedURLException {
		// TODO Auto-generated method stub
		Utiles u=new Utiles();
		ArrayList<String> config=u.readFromFile("system.properties");
		getConfig(config);
		
		LocateRegistry.createRegistry(registryPort);
		String URL="rmi://"+ServerAdd+":"+registryPort+"/shortestPath";
    	Naming.rebind(URL, new Server());
		System.out.println("Server is Running");
	}
	private static void getConfig(ArrayList<String> config) {
		// TODO Auto-generated method stub
		ServerAdd=config.get(0).substring(11);
		System.out.println(ServerAdd);
		ServerPort=Integer.parseInt(config.get(1).substring(16));
		System.out.println(ServerPort);
		numberOfNodes=Integer.parseInt(config.get(2).substring(18));
		System.out.println(numberOfNodes);
		nodesAdresses=new String[numberOfNodes];
		for(int i=0;i<numberOfNodes;i++) {
			nodesAdresses[i]=config.get(3+i).substring(10);
			System.out.println(nodesAdresses[i]);
		}
		registryPort=Integer.parseInt(config.get(3+numberOfNodes).substring(21));
		System.out.println(registryPort);
	}

}
