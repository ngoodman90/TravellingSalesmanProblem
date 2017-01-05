import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;


/**
 * Created by Noam on 12/11/2016.
 */
public class TspGraph{

    private ArrayList<TspNode> nodes;
    private int N;
	private double routeLength;
	private double[][] distanceMatrix;
	private SingleGraph displayGraph;
	private Viewer viewer;
	private ViewerPipe pipe;





    public TspGraph(String s)
    {
    	this.nodes = createNodes(s);
    	this.N = nodes.size();
		routeLength = Double.MAX_VALUE;
		distanceMatrix = buildDistanceMatrix();
		displayGraph = initDisplayGraph();
		viewer = initViewer();
		pipe = initPipe();
    }

	public double getRouteLength(){ return routeLength;}

	public void setRouteLength(double routeLength) { this.routeLength = routeLength;}

	public void setRouteLength() 
	{
		double routeLength = 0.0;
		for (TspNode node : nodes)
			routeLength += node.getDistanceToNext();
		this.routeLength =  routeLength;
	}

	public ArrayList<TspNode> createNodes(String s)
	{
		Scanner scanner = new Scanner(s);
		int numberOfNodes = scanner.nextInt();
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

	public SingleGraph initDisplayGraph()
	{
		SingleGraph g = new SingleGraph("DisplayGraph");
		for (TspNode node : nodes)
			g.addNode(node.getName()).addAttribute("xy", node.getLocation().getX(), node.getLocation().getY());

		for (Node n : g)
			n.addAttribute("label", n.getId());
		return g;
	}

	public Viewer initViewer()
	{
		Viewer viewer;
		viewer = displayGraph.display();
		viewer.disableAutoLayout();
		viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
		return viewer;

	}

	private ViewerPipe initPipe()
	{
		ViewerPipe pipe;
		pipe = viewer.newViewerPipe();
		pipe.addAttributeSink(displayGraph);
		return pipe;
	}

	public void buildSequentialRoute()
	{
		for (int i = 0; i < N; i++)
			nodes.get(i).setNext(nodes.get((i + 1) % N));
	}

	public double[][] buildDistanceMatrix()
	{
		double[][] distanceMatrix = new double[N][N];
		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < N; j++)
			{
				distanceMatrix[i][j] = nodes.get(i).distanceToNode(nodes.get(j));
			}
		}
		return distanceMatrix;
	}

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
    	int N = this.nodes.size();
		int nodeIndex = 0, minIndex;
		double min;
		boolean[] visited = new boolean[N];
		boolean foundMin = false, routeComplete = false;
		TspNode currNode, nextNode;
		//System.out.println(Arrays.deepToString(distanceMatrix));

		currNode = nodes.get(nodeIndex);
		visited[nodeIndex] = true;
		while (!routeComplete)
		{
			min = Double.MAX_VALUE;
			minIndex = -1;
			for (int i = 0; i < N; i++)
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
				currNode = nextNode;
			}
			else
			{
				currNode.setNext(nodes.get(0));
				routeComplete = true;
			}
		}
    }

    public void buildGreedyRoute()
    {
    	TspNode minNode1;
		TspNode minNode2;
    	Point minEdge;
    	boolean[] hasNext = new boolean[N];
		boolean[] hasPrev = new boolean[N];

    	minEdge = findMinEdge(hasNext, hasPrev);
    	while (minEdge.getX() != -1 && minEdge.getY() != -1)
    	{
    		minNode1 = nodes.get(minEdge.getX());
	    	minNode2 = nodes.get(minEdge.getY());
			minNode1.setNext(minNode2);
			if (isCircuit(minNode2))
			{
				minNode1.setNextNull(minNode2);
				hasNext[minEdge.getX()] = false;
				hasPrev[minEdge.getY()] = false;
			}
			distanceMatrix[minEdge.getX()][minEdge.getY()] = Double.MAX_VALUE;
			distanceMatrix[minEdge.getY()][minEdge.getX()] = Double.MAX_VALUE;
			minEdge = findMinEdge(hasNext, hasPrev);
    	}

		rearrangeNodes();
		distanceMatrix = buildDistanceMatrix();
    }

    public Point findMinEdge(boolean[] hasNext, boolean[] hasPrev)
    {
    	double min = Double.MAX_VALUE;
    	Point ans = new Point(-1, -1);

    	for (int i = 0; i < N; i++)
    	{
    		if (!hasNext[i])
    		{
    			for (int j = 0; j < N; j++)
	    		{
					if (!hasPrev[j])
	    			{
						if (distanceMatrix[i][j] > 0 && distanceMatrix[i][j] < min)
						{
							min = distanceMatrix[i][j];
							ans = new Point(i, j);
						}
	    			}
	    		}
    		}
    	}
    	if (ans.getX() != -1 && ans.getY() != -1)
		{
			hasNext[ans.getX()] = true;
			hasPrev[ans.getY()] = true;
		}
    	return ans;
    }

    public boolean isCircuit(TspNode n)
	{
		int numOfNodesInRoute = 0;
		TspNode tempNode = n;
		while ((tempNode = tempNode.getNext()) != null)
		{
			numOfNodesInRoute++;
			if (tempNode.equals(n))
			{
				if (numOfNodesInRoute < N)
					return true;
				else
					return false;
			}
		}
		return false;
	}

    public void buildTwoOptRoute() throws InterruptedException 
    {
    	boolean foundShorterPath = false;
		double newRouteLength;
		ArrayList<TspNode> newRoute;
		
		for (int i = 0; i < N - 1; i++)
		{
			for (int j = i + 1; j < N; j++)
			{
				if (shouldTrySwap(i, j))
				{
					newRoute = swap(nodes.get(i), nodes.get(j));
					newRouteLength = calculateRouteLength(newRoute);
					if (newRouteLength < routeLength)
					{
						nodes = newRoute;
						setRouteLength(newRouteLength);
						foundShorterPath = true;
						printGraph();
						break;
					}
				}
			}
			if (foundShorterPath)
				break;
		}
		if (foundShorterPath)
			buildTwoOptRoute();
	}

	public boolean shouldTrySwap(int nodeIndex1, int nodeIndex2)
	{
		TspNode n1, n2, n3, n4;
		double n1_n2, n3_n4, n1_n3, n2_n4;

		n1 = nodes.get(nodeIndex1);
		n2 = n1.getNext();
		n3 = nodes.get(nodeIndex2);
		n4 = n3.getNext();
		n1_n2 = n1.distanceToNode(n2);
		n3_n4 = n3.distanceToNode(n4);
		n1_n3 = n1.distanceToNode(n3);
		n2_n4 = n2.distanceToNode(n4);

		return (n1_n2 + n3_n4 > n1_n3 + n2_n4);
	}

	public ArrayList<TspNode> swap(TspNode n1, TspNode n2) 
	{
		ArrayList<TspNode> newRoute = new ArrayList<>(N);
		ArrayList<TspNode> firstPart = new ArrayList<>();
		ArrayList<TspNode> secondPart = new ArrayList<>();
		ArrayList<TspNode> thirdPart = new ArrayList<>();
		TspNode curr = nodes.get(0);

		curr = addNodesToRoute(firstPart, curr, n1.getNext());
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

	public void rearrangeNodes()
	{
		ArrayList<TspNode> rearrangedRoute = new ArrayList<>(N);
		TspNode firstNode = nodes.get(0);
		TspNode currNode = firstNode.getNext();
		rearrangedRoute.add(firstNode);
		while (!currNode.equals(firstNode))
		{
			rearrangedRoute.add(currNode);
			currNode = currNode.getNext();
		}
		nodes = rearrangedRoute;
	}

	public double calculateRouteLength(ArrayList<TspNode> nodes)
	{
		double routeLength = 0.0;
		for (TspNode node : nodes)
			routeLength += node.getDistanceToNext();
		return routeLength;
	}

	public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		for (TspNode node : nodes)
			stringBuilder.append(node.toString());
		return stringBuilder.toString();
	}

	public void printGraph() throws InterruptedException 
	{
		TspNode next;
		while (displayGraph.getEdgeCount() > 0)
		{
			pipe.pump();
			displayGraph.removeEdge(0);
		}


		for (TspNode node : nodes)
		{
			next = node.getNext();
			pipe.pump();
			displayGraph.addEdge(node.getName() + next.getName(), node.getName(), next.getName()).addAttribute("length", node.getDistanceToNext());
		}

		for (Edge e : displayGraph.getEachEdge())
		{
			pipe.pump();
			e.addAttribute("label", "" + (int) e.getNumber("length"));
		}


		//displayGraph.display(false);
		Thread.sleep(500);

	}
}
