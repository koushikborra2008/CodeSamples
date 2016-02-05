package edu.self;

// Class representing a vertex
public class Node
{

	private final int addr;			// Node address
	private Node parent;			// Parent in the shortest path tree
	private int cost;				// Current cost
	private boolean visited;		// if this node has been visited;
	
	/* Create a node with default address */
	public Node() 
	{
		addr = -1;
	}
	
	/* Create a node with specific address */
	public Node (int addr)
	{
		this.addr = addr;
	}
	
	/* Accessors */
	public int getAddr()
	{
		return addr;
	}
	
	public Node getParent()
	{
		return parent;
	}
	
	public int getCost()
	{
		return cost;
	}
	
	public boolean getVisitStatus()
	{
		return visited;
	}
	
	/* Mutators */
	public void setParent(Node node)
	{
		parent = node;
	}
	
	public void setCost(int cost)
	{
		this.cost = cost;
	}
	
	public void setVisitStatus(boolean status)
	{
		visited = status;
	}
}
