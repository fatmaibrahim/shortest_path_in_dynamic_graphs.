package rmi;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;


public class Client extends UnicastRemoteObject implements IRemoteMethod , Runnable {
	IRemoteMethod access;
	boolean run=false;
	protected Client(IRemoteMethod access) throws RemoteException {
		// TODO Auto-generated constructor stub
		this.access=access;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!run) {
		try {
			if(access.serverReady().equals("R")) {
				System.out.println("R");
				run=true;
			}else {
				System.out.println("Server not ready yet");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		}
        System.out.println("Successfully Connected To RMI Server");
        int i=0;
		while(run) {

        ArrayList<String> batch=generateBatch();
		
        System.out.println("batch: "+batch);
		ArrayList<String> answer = null;
		try {
			long startTime = System.currentTimeMillis();

			answer = access.executeBatch(batch);
			long stopTime = System.currentTimeMillis();
			   System.out.println("the excution Time: "+(long)(stopTime-startTime));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("outputs: "+answer);
        Scanner scanner = new Scanner(System.in);
        String fileName="log"+i;
        try {
			writetoFile(fileName,answer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("DO YOU WANT to continue or exit (C/E)");
        String response=scanner.nextLine();
			
        if (response.equals("E")) {
				run=false;
        }
        i++;
		}
	}

	private ArrayList<String> generateBatch() {
		// TODO Auto-generated method stub
		ArrayList<String> batch =new ArrayList<String>();
//		batch.add("Q 1 3");
//		batch.add("A 4 5");
//		batch.add("Q 1 5");
//		batch.add("Q 5 1");

		

		
		batch.add("Q 11 5");
		batch.add("A 1 9");
		batch.add("Q 1 7");
		batch.add("A 1 8");
		batch.add("Q 11 9");
		batch.add("D 1 7");
		batch.add("A 1 3");
		batch.add("Q 1 11");
		batch.add("A 1 3");
		batch.add("Q 1 11");		
		
//		batch.add("Q 1 3");
//		batch.add("Q 1 5");
//		batch.add("A 1 3");
//		batch.add("Q 1 3");
//		batch.add("A 1 4");
//		batch.add("Q 1 5");
//		batch.add("Q 1 3");
//		batch.add("D 1 3");
//		batch.add("Q 1 5");
//		batch.add("D 1 4");
//		batch.add("Q 1 3");
//		batch.add("A 1 4");
//		batch.add("Q 1 5");
//		batch.add("D 1 3");
//		batch.add("Q 1 4");
		
				

		return batch;
	}

	@Override
	public ArrayList<String> executeBatch(ArrayList<String> batch) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
    public static void main(String[] args) throws MalformedURLException,RemoteException,NotBoundException {
        System.out.println("\nConnecting To RMI Server...\n");
    	// lookup method to find reference of remote object
		String URL="rmi://"+args[0]+":"+Integer.parseInt(args[1])+"/shortestPath";
        
		IRemoteMethod access = (IRemoteMethod) Naming
				.lookup(URL);
        new Thread(new Client(access)).start();
		
    }

	@Override
	public String serverReady() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void writetoFile(String fileName,ArrayList<String> lines) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true));
	    for(int i=0;i<lines.size();i++) {
	    	writer.write(lines.get(i));
		    writer.newLine();	
	    }
	    writer.close();
	}
}
