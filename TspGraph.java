import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;


/**
 * Created by Noam on 12/11/2016.
 */
public class TspGraph{

    private ArrayList<TspNode> nodes;
	private double routeLength;
	private Graph displayGraph;

    public TspGraph(String s)
    {
    	this.nodes = createNodes(s);
		routeLength = Double.MAX_VALUE;
    }

    public TspGraph(ArrayList<TspNode> nodes)
    {
    	this.nodes = nodes;
		routeLength = Double.MAX_VALUE;;
    }

    public double getRouteLength(){ return routeLength;}

	public void setRouteLength(double routeLength) { this.routeLength = routeLength;}

	public void setRouteLength() {
		double routeLength = 0.0;
		for (TspNode node : nodes)
			routeLength += node.getDistanceToNext();
		this.routeLength =  routeLength;
	}

	public ArrayList<TspNode> createNodes(String s)
	{
		Scanner scanner = new Scanner(s);
		int numberOfNodes = scanner.nextInt(); // the total number of nodes
		scanner.nextLine();
		ArrayList<TspNode> nodes = new ArrayList<>(numberOfNodes);
		for (int i = 0; i < numberOfNodes; i++)
		{
			String name = scanner.nextLine();
			int x = scanner.nextInt();
			int y = scanner.nextInt();
			scanner.nextLine();//read newline char
			nodes.add(new TspNode(name, i, new Point(x, y)));
		}
		return nodes;
	}

	public ArrayList<TspNode> getNodes() {return nodes;}

	/*public void buildSequentialRoute()
	{
		int size = nodes.size();
		for (int i = 0; i < size; i++)
			nodes.get(i % size).setNext(nodes.get((i + 1) % size));
	}*/

	public void buildRandomRoute()
	{
		ArrayList<TspNode> newRoute = new ArrayList<>();
		TspNode lastNode, addedNode;
		int randInt;

		newRoute.add(nodes.get(0));
		nodes.remove(0);
		while (nodes.size() > 0)
		{
			randInt = ThreadLocalRandom.current().nextInt(0, nodes.size());
			lastNode = newRoute.get(newRoute.size() - 1);
			addedNode = nodes.get(randInt);
			lastNode.setNext(addedNode);
			newRoute.add(addedNode);
			nodes.remove(randInt);
		}
		newRoute.get(newRoute.size() - 1).setNext(newRoute.get(0));
		nodes = newRoute;
	}

	public void buildNearestNeighbourGraph()
    {
    	Stack<TspNode> stack = new Stack();
    	int numOfNodes = this.nodes.size();
		int nodeIndex = 0, minIndex;
		double min;
		boolean[] visited = new boolean[numOfNodes];
		boolean foundMin = false;
		double[][] distanceMatrix = new double[numOfNodes][numOfNodes];
		TspNode currNode, nextNode;
		for (int i = 0; i < numOfNodes; i++)
		{
			TspNode n1 = nodes.get(i);
			for (int j = 0; j < numOfNodes; j++)
				distanceMatrix[i][j] = n1.distanceToNode(nodes.get(j));
		}
		System.out.println(Arrays.deepToString(distanceMatrix));

		visited[nodeIndex] = true;
		stack.push(nodes.get(nodeIndex));
		while (!stack.isEmpty())
		{
			currNode = stack.pop();
			min = Double.MAX_VALUE;
			minIndex = -1;
			for (int i = 0; i < numOfNodes; i++)
			{
				if (distanceMatrix[nodeIndex][i] < min && !visited[i]) {
					min = distanceMatrix[nodeIndex][i];
					minIndex = i;
					foundMin = true;
				}
			}
			if (foundMin)
			{
				foundMin = false;
				visited[minIndex] = true;
				nextNode = nodes.get(minIndex);
				currNode.setNext(nextNode);
				stack.push(nextNode);
			}
			else
				//set last nodes next to origin
				currNode.setNext(nodes.get(0));
		}
    }

    public void twoOptSwap() throws InterruptedException 
    {
    	boolean foundShorterPath = false;
		TspGraph newRoute;
		outer:
		for (int i = 1; i < nodes.size() - 1; i++)
		{
			for (int j = i + 1; j < nodes.size(); j++)
			{
				newRoute = new TspGraph(swap(nodes.get(i), nodes.get(j)));
				newRoute.setRouteLength();
				if (newRoute.getRouteLength() < routeLength)
				{
					nodes = newRoute.getNodes();
					setRouteLength(newRoute.getRouteLength());
					foundShorterPath = true;
					break;
				}
			}
			if (foundShorterPath)
				break;
		}
		if (foundShorterPath)
			twoOptSwap();
	}

	public ArrayList<TspNode> swap(TspNode n1, TspNode n2) 
	{
		ArrayList<TspNode> newRoute = new ArrayList<>(nodes.size());
		ArrayList<TspNode> firstPart = new ArrayList<>();
		ArrayList<TspNode> secondPart = new ArrayList<>();
		ArrayList<TspNode> thirdPart = new ArrayList<>();
		TspNode curr = nodes.get(0);

		curr = addNodesToRoute(firstPart, curr, n1);
		curr = addNodesToRoute(secondPart, curr, n2.getNext());
		addNodesToRoute(thirdPart, curr, nodes.get(0));

		reverseRoute(secondPart);

		appendRoute(newRoute, firstPart);
		appendRoute(newRoute, secondPart);
		appendRoute(newRoute, thirdPart);

		return newRoute;
	}

	private void appendRoute(ArrayList<TspNode> route1, ArrayList<TspNode> route2)
	{
		if (route1.size() == 0)
			route1.addAll(route2);

		else if (!(route2.size() == 0))
		{
			TspNode firstNodeRoute1, lastNodeRoute1, firstNodeRoute2;
			lastNodeRoute1 = route1.get(route1.size() - 1);
			firstNodeRoute2 = route2.get(0);
			lastNodeRoute1.setNext(firstNodeRoute2);

			route1.addAll(route2);

			firstNodeRoute1 = route1.get(0);
			lastNodeRoute1 = route1.get(route1.size() - 1);
			lastNodeRoute1.setNext(firstNodeRoute1);
		}
	}

	private TspNode addNodesToRoute(ArrayList<TspNode> route, TspNode curr, TspNode n)
	{
		while (!curr.equals(n))
		{
			route.add(curr);
			curr = curr.getNext();
		}
		return curr;
	}

	public void reverseRoute(ArrayList<TspNode> route)
	{
		if (route.size() > 1)
		{
			TspNode currNode, nextNode;
			int size = route.size();
			Collections.reverse(route);
			for (int i = 0; i < route.size() - 1; i++)
			{
				currNode = route.get(i % size);
				nextNode = route.get((i + 1) % size);
				currNode.setNext(nextNode);
			}
		}

	}

	public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		for (TspNode node : nodes)
			stringBuilder.append(node.toString());
		return stringBuilder.toString();
	}

	public void printGraph() throws InterruptedException {
		displayGraph = new SingleGraph("DisplayGraph");
		for (TspNode node : nodes)
			displayGraph.addNode(node.getName()).addAttribute("xy", node.getLocation().getX(), node.getLocation().getY());
		for (TspNode node : nodes)
		{
			TspNode next = node.getNext();
			displayGraph.addEdge(node.getName() + next.getName(), node.getName(), next.getName()).addAttribute("length", node.getDistanceToNext());
		}
		for (Node n : displayGraph)
			n.addAttribute("label", n.getId());
		for (Edge e : displayGraph.getEachEdge())
			e.addAttribute("label", "" + (int) e.getNumber("length"));
		displayGraph.display(false);
		Thread.sleep(5000);

	}
}
