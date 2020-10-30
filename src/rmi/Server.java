package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Server extends UnicastRemoteObject implements IRemoteMethod{

	ArrayList<ArrayList<Integer>> graph;
	Utiles u=new Utiles();
	private ReadWriteLock rwlock;
	ArrayList<ArrayList<Object>> performance;
	
	
	protected Server() throws RemoteException {
		graph= u.parseGraph("graph");
		rwlock = new ReentrantReadWriteLock();
		performance=new ArrayList<ArrayList<Object>>();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public  ArrayList<String> executeBatch(ArrayList<String> batch) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<String> outputs=new ArrayList<String>();
		long startTime = System.currentTimeMillis();
		Addtoperformance(Thread.currentThread().getId(),true,startTime);
		
		for(int i=0;i<batch.size();i++) {
			String []str=batch.get(i).split(" ");
			int src=Integer.parseInt(str[1])-1;
			int dest=Integer.parseInt(str[2])-1;
			synchronized(this) {
			if(str[0].equals("Q")) {
			
				outputs.add(query(src,dest));
//				rwlock.readLock().lock();
//			       try {
//						outputs.add(query(src,dest));
//			       } finally {
//			          rwlock.readLock().unlock();
//			       }
			}else if(str[0].equals("A")){
				addEdge(src,dest);

//				rwlock.writeLock().lock();
//			       try {
//						addEdge(src,dest);
//			       } finally {
//			          rwlock.writeLock().unlock();
//			       }
			}else if(str[0].equals("D")){
				deleteEdge(src,dest);
				//				rwlock.writeLock().lock();
//			       try {
//						deleteEdge(src,dest);
//			       } finally {
//			          rwlock.writeLock().unlock();
//			       }
			}
			//new Random().nextInt(10000)
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		long stopTime = System.currentTimeMillis();
		Addtoperformance(Thread.currentThread().getId(),false,stopTime);
		return outputs;
	}

	private void Addtoperformance(long id, boolean b, long Time) {
		// TODO Auto-generated method stub
		if(b) {
			performance.add(new ArrayList<Object>());
			performance.get(performance.size()-1).add(id);
			performance.get(performance.size()-1).add(Time);
		}else {
			for(int i=0;i<performance.size();i++) {
				if(performance.get(i).get(0).equals(id)) {
					performance.get(i).add(Time);
					System.out.println("the node number:"+(i+1));
					System.out.println("the Thread ID="+performance.get(i).get(0));
					System.out.println("the execution Time:"+(Time-(long)performance.get(i).get(1))+" ms");
					return;
				}
			}
		}
	}

	private void deleteEdge(int src, int dest) {
		// TODO Auto-generated method stub
		if(src<graph.size()) {
			for(int i=0;i<graph.get(src).size();i++) {
				if(graph.get(src).get(i)==dest) {
					graph.get(src).remove(i);
					return;
				}
			}
		}
		
	}

	private void addEdge(int src, int dest) {
		// TODO Auto-generated method stub
		if(src<graph.size()) {
			if(!graph.get(src).contains(dest)) {
				graph.get(src).add(dest);
			}	
		}else {
			graph.add(new ArrayList<Integer>());
			graph.get(src).add(dest);
		}
		if(dest==graph.size()) {
			graph.add(new ArrayList<Integer>());
		}	
		
	}

	private String query(int src, int dest) {
		// TODO Auto-generated method stub
		int numberOfNodes = -1;
		boolean visited[] = new boolean[graph.size()];
		ArrayList<Node> queue = new ArrayList<Node>();
		visited[src] = true;
		queue.add(new Node(src, 0));
		while (queue.size() != 0) {

			Node node = queue.remove(0);
			if(node.getnodeNum()>=graph.size()) {
				continue;
			}
			ArrayList<Integer> adjacent = graph.get(node.getnodeNum());
			for (int i = 0; i < adjacent.size(); i++) {
				int adjNode = adjacent.get(i);
				if (adjNode == dest) {
					numberOfNodes = node.getcost() + 1;
					return Integer.toString(numberOfNodes);
				}

				if (!visited[adjNode]) {
					visited[adjNode] = true;
					queue.add(new Node(adjNode, node.getcost() + 1));
				}
			}

		}

		return Integer.toString(numberOfNodes);
	}


	@Override
	public String serverReady() throws RemoteException {
		// TODO Auto-generated method stub
		if(graph!=null) {
			return "R";
		}
		return null;
	}

}
