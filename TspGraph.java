import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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
	private boolean graphics;
	private SingleGraph displayGraph;
	private Viewer viewer;
	private ViewerPipe pipe;

    public TspGraph(String s, boolean graphics)
    {
    	this.nodes = createNodes(s);
    	this.N = nodes.size();
		routeLength = Double.MAX_VALUE;
		distanceMatrix = buildDistanceMatrix();
		this.graphics = graphics;
		if (this.graphics)
		{
			displayGraph = initDisplayGraph();
			viewer = initViewer();
			pipe = initPipe();
		}
    }

	public TspGraph(int n, boolean graphics)
	{
		this.nodes = createRandomNodes(n);
		this.N = nodes.size();
		routeLength = Double.MAX_VALUE;
		distanceMatrix = buildDistanceMatrix();
		this.graphics = graphics;
		if (this.graphics)
		{
			displayGraph = initDisplayGraph();
			viewer = initViewer();
			pipe = initPipe();
		}
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

	public ArrayList<TspNode> createRandomNodes(int num)
	{
		Random random = new Random();
		ArrayList<TspNode> randomNodes = new ArrayList<>(num);
		double randX, randY;
		for (int i = 0; i < num; i++)
		{
			randX = random.nextDouble();
			randY = random.nextDouble();
			randomNodes.add(new TspNode(Integer.toString(i).toString(), i, new DoublePoint(randX, randY)));
		}
		return randomNodes;
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
			nodes.add(new TspNode(name, i, new DoublePoint(x, y)));
		}
		return nodes;
	}

	public SingleGraph initDisplayGraph()
	{
		if (graphics)
		{
			SingleGraph g = new SingleGraph("DisplayGraph");
			for (TspNode node : nodes)
				g.addNode(node.getName()).addAttribute("xy", node.getLocation().getX(), node.getLocation().getY());

			for (Node n : g)
				n.addAttribute("label", n.getId());
			return g;
		}
		return null;
	}

	public Viewer initViewer()
	{
		if (graphics)
		{
			Viewer viewer;
			viewer = displayGraph.display();
			viewer.disableAutoLayout();
			viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
			return viewer;
		}
		return null;
	}

	private ViewerPipe initPipe()
	{
		if (graphics)
		{
			ViewerPipe pipe;
			pipe = viewer.newViewerPipe();
			pipe.addAttributeSink(displayGraph);
			return pipe;
		}
		return null;
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

    public void buildGreedyRoute()
    {
    	TspNode minNode1;
		TspNode minNode2;
		IntPoint minEdge;
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
				minNode1.resetNode();
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

    public IntPoint findMinEdge(boolean[] hasNext, boolean[] hasPrev)
    {
    	double min = Double.MAX_VALUE;
		IntPoint ans = new IntPoint(-1, -1);

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
							ans = new IntPoint(i, j);
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

	public void buildTwoOptRoute() throws InterruptedException
	{
		boolean foundShorterPath = false;
		double newRouteLength;
		ArrayList<TspNode> newRoute;
		int i;
		Outer:
		for (i = 0; i < N - 1; i++)
		{
			if (foundShorterPath)
			{
				i = 0;
				foundShorterPath = false;
			}
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
						if (graphics)
							printGraph();
						continue Outer;
					}
				}
			}
		}
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
		if (graphics)
		{
			TspNode next;
			while (displayGraph.getEdgeCount() > 0)
			{
				pipe.pump();
				displayGraph.removeEdge(0);
			}
			for (TspNode node : nodes)
			{
				if ((next = node.getNext()) != null)
				{
					pipe.pump();
					try {
						displayGraph.addEdge(node.getName() + next.getName(), node.getName(), next.getName()).addAttribute("length", node.getDistanceToNext());
					} catch (org.graphstream.graph.IdAlreadyInUseException e) {}
				}
				else break;
			}
			Thread.sleep(5);
		}
	}
}
