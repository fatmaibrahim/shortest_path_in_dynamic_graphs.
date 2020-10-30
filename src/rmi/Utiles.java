package rmi;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Utiles {

	public ArrayList<String> readFromFile(String fileName) {
		ArrayList<String> records = new ArrayList<String>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = reader.readLine()) != null) {
				records.add(line);
			}
			reader.close();
			return records;
		} catch (Exception e) {
			System.err.format("Exception occurred trying to read '%s'.",
					fileName);
			e.printStackTrace();
			return null;
		}
	}
	public void clearFile(String fileName){
		PrintWriter writer;
		try {
			writer = new PrintWriter(new File(fileName));
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void writetoFile(String fileName,String line) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true));
	    writer.write(line);
	    writer.newLine();
	    writer.close();
	}
	
	public ArrayList<Object> prepareGraph(String fileName){
		ArrayList<String>data = new ArrayList<String>();
		ArrayList<Point>nodes = new ArrayList<Point>();
		ArrayList<ArrayList<Integer>>graph = new ArrayList<ArrayList<Integer>>();
		ArrayList<Object> mulRet =new ArrayList<Object>();
		data = readFromFile(fileName);
		System.out.println("*initial graph*");
		System.out.println(data);
		int max=-1;
	
		for(int i=0;i<data.size()-1;i++){
			nodes.add(new Point());
			String []s=data.get(i).split(" ");
			int index1 =Integer.parseInt(s[0]);
			int index2=Integer.parseInt(s[1]);
			nodes.get(i).x=index1;
			nodes.get(i).y= index2;
			if(index1>index2){
				if(index1>max){
					max = index1;
				}
			}else{
				if(index2>max){
					max = index2;
				}
			}
		}
		for(int j=0 ;j<max;j++){
			graph.add(new ArrayList<Integer>());
		}
		mulRet.add(nodes);
		mulRet.add(graph);
		return mulRet;
	}
	
	public ArrayList<ArrayList<Integer>> parseGraph (String fileName){
		ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
		ArrayList<Point>nodes = new ArrayList<Point>();
		ArrayList<Object> data = new ArrayList<Object>();
		data= prepareGraph(fileName);
		nodes = (ArrayList<Point>) data.get(0);
		graph = (ArrayList<ArrayList<Integer>>) data.get(1);
		for(int i=0;i<nodes.size();i++){
			graph.get(nodes.get(i).x-1).add(nodes.get(i).y-1);
		}
		return graph;
	}

}
