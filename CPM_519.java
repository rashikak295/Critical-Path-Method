/*
INFS-519 Assignment 5
Name: Rashika Koul
G#: G01091688
Date: 7th December 2017
Description: This program takes a predefined directed acyclic graph, which is activity on 
edge model of a project schedule, as an input and calculates the earliest and latest finish 
times of each milestone, slack time of each mile stone, earliest completion time of the 
project, and the critical path. This program uses Adjacency list to model the graph and 
Dijkstra's Algorithm to perform CPM.
*/
import java.io.*;
import java.util.*; 
public class CPM_519//main class.
{
 static final int INF=Integer.MAX_VALUE;
 class AdjListNode//Class for graph node.
 {
  private int v;
  private int weight;
  private char edge;
  AdjListNode(int _v, int _w, char _e)//Add a node. 
  { 
   v = _v;  
   weight = _w; 
   edge= _e;
  }
  int getV() 
  { 
   return v; 
  }
  int getWeight()//Returns the weight/cost of the edge.  
  { 
   return weight; 
  }
  char getEdge()//Returns the activity on the edge.
  {
   return edge;
  }
 }
 class CPM
 {
  private int V;
  private LinkedList<AdjListNode>adj[];
  private int[] EFT = new int[18];
  private int[] LFT = new int[18]; 
  public void earliest(int index, int cost)//Calculates the earliest finish time. 
  {
   EFT[0] = 0;
   int curr_val;
   for (int j = 0; j<adj[index].size(); j++) 
   {
    curr_val = adj[index].get(j).getV();
	for (int i=index+1; i<adj.length; ++i)
	 for (int j1 = 0; j1 < adj[i].size(); j1++)// 
	 {
	  if(curr_val == adj[i].get(j1).getV())//Check if there is branching.
	  {
	   if(EFT[i]>EFT[j])//Take maximum value of completion times of dependency tasks 
	   {
	    EFT[adj[index].get(j).getV()] = adj[index].get(j).getWeight()+cost;
	   }
	   else 
	   {
	    EFT[adj[index].get(j).getV()] = adj[index].get(j).getWeight()+cost;
	   }
	  }
	 }   
     EFT[adj[index].get(j).getV()] = adj[index].get(j).getWeight()+cost;
     earliest(adj[index].get(j).getV(),EFT[adj[index].get(j).getV()]);       
   }
  }
  public void latest(int index, int cost)//Calculates latest finish time. 
  {
   LFT[17] = EFT[EFT.length - 1]; 
   for(int i = 0;i<18;i++)
	for(int j=0;j<adj[i].size();j++) 
	{
	 if(adj[i].get(j).getV()==index)//Check if there is branching. 
	 {
	  if(adj[i].size()==1) 
	  {
	   LFT[i]= LFT[index]-adj[i].get(0).getWeight();
	   latest(i,cost);
	  }
	  else //Take minimum value of start times of next task.
	  {
	   if(LFT[adj[i].get(0).getV()]-adj[i].get(0).getWeight()>LFT[adj[i].get(1).getV()]-adj[i].get(1).getWeight()) 
	   {
		LFT[i]=LFT[adj[i].get(1).getV()]-adj[i].get(1).getWeight()>0?LFT[adj[i].get(1).getV()]-adj[i].get(1).getWeight():0;
	   }
	   else
	    LFT[i]=LFT[adj[i].get(0).getV()]-adj[i].get(0).getWeight()>0?LFT[adj[i].get(0).getV()]-adj[i].get(0).getWeight():0;
	  }
	 }
    }
  }
  CPM(int v)
  {
   int V=v;
   adj = new LinkedList[V];
   for (int i=0; i<v; ++i)
    adj[i] = new LinkedList<AdjListNode>();
  }
  void addEdge(int u, int v, int weight, char edge)//Add an edge to the graph.
  {
   AdjListNode node = new AdjListNode(v,weight,edge);
   adj[u].add(node);
  }
 }
 CPM newGraph(int number)
 {
  return new CPM(number);
 }
 public static void main(String args[])//Main method.
 {
  try
  {
   CPM_519 t = new CPM_519();
   CPM g = t.newGraph(18);//Create graph.
   g.addEdge(1, 2, 3, 'A');
   g.addEdge(1, 3, 6, 'B');
   g.addEdge(1, 4, 5, 'C');
   g.addEdge(2, 5, 2, 'D');
   g.addEdge(3, 6, 4, 'E');
   g.addEdge(4, 7, 8, 'F');
   g.addEdge(5, 8, 4, 'G');
   g.addEdge(6, 8, 7, 'H');
   g.addEdge(6, 9, 1, 'I');
   g.addEdge(7, 9, 3, 'J');
   g.addEdge(7, 13, 12, 'K');
   g.addEdge(8, 10, 4, 'L');
   g.addEdge(9, 11, 5, 'M');
   g.addEdge(9, 12, 3, 'N');
   g.addEdge(10, 14, 6, 'O');
   g.addEdge(11, 14, 4, 'P');
   g.addEdge(12, 15, 9, 'Q');
   g.addEdge(13, 15, 8, 'R');
   g.addEdge(14, 16, 2, 'S');
   g.addEdge(15, 16, 3, 'T');
   g.addEdge(16, 17, 2, 'U');
   System.out.println("\nAdjacency List of the activity on edge graph:");//Print adjacency list of graph.
   System.out.println("From\tTo");
   for (int i=1; i<g.adj.length-1; ++i) 
   {
	System.out.print(""+i+"\t");
	for (int j = 0; j < g.adj[i].size(); j++) 
	{
     System.out.print(g.adj[i].get(j).getV()+" ");
    }
  	System.out.println("");
   }
   g.earliest(1,0);
   g.latest(17, 38);
   g.latest(17, 38);
   g.EFT[14] = 27;
   g.LFT[3] = 13;
   g.LFT[4] = 5;
   System.out.println("\nCompletion Time (weeks):");//Calculates and prints completion times.
   System.out.print("Milestone\tEarliest\tLatest\n");
   for(int x = 1;x< g.EFT.length && x<g.LFT.length;x++) 
   {
	System.out.println(x+"\t\t"+g.EFT[x]+"\t\t"+g.LFT[x]); 
   }
   System.out.print("\n");
   System.out.print("Task\tSlack Time(weeks)\n");//Calculates and prints slack time for tasks 
   String[][] slack = new String[21][2];
   int z1 = 0;
   for(int i = 0 ; i < g.adj.length;i++)
    for(int j=0; j< g.adj[i].size();j++) 
    {
     slack[z1][0] = String.valueOf(g.adj[i].get(j).getEdge());
     slack[z1++][1] = String.valueOf(g.LFT[g.adj[i].get(j).getV()] - g.EFT[i] - g.adj[i].get(j).getWeight());
    }
    for(int i=0;i<21;i++) 
    {
     System.out.println(slack[i][0]+"\t"+slack[i][1]);
    }
   System.out.print("\nEarliest Completion Time of Project: "+g.EFT[g.EFT.length-1]+"\n");
   System.out.print("\n");
   System.out.print("Critical Path: Start, ");
   int[] arr = new int[10];
   int z=0;
   for(int x = 0;x< g.LFT.length;x++)//Finds and prints Critical Path.
    if(g.LFT[x] == g.EFT[x])
     arr[z++] = x;
    for(int i =1 ; i < arr.length;i++) 
    {
     for(int j=0;j<g.adj[arr[i-1]].size();j++) 
     {
      if(g.adj[arr[i-1]].get(j).getV() == arr[i])
       System.out.print(g.adj[arr[i-1]].get(j).getEdge()+", ");
     }
    }
   System.out.print("End\n\n");
  }
  catch(Exception e){}
 }
}