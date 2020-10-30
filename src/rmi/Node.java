package rmi;

public class Node {

	private int nodeNum;
	private int cost;
	public Node(int nodeNum,int cost){
	this.nodeNum=nodeNum;
	this.cost=cost;
	}
	public int getnodeNum(){
	return nodeNum;
	}
	public int getcost(){
	return cost;
	}
}
