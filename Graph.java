import java.util.*;

/**
 * Created by Noam on 12/11/2016.
 */
public class Graph {

    ArrayList<Node> nodes;
	private int[][] distanceMatrix;

    public Graph(String s)
    {
    	this.nodes = createNodes(s);
		distanceMatrix = null;
    }

	public ArrayList<Node> createNodes(String s)
	{
		Scanner scanner = new Scanner(s);
		int numberOfNodes = scanner.nextInt(); // the total number of nodes
		scanner.nextLine();
		ArrayList<Node> nodes = new ArrayList<>(numberOfNodes);
		for (int i = 0; i < numberOfNodes; i++)
		{
			String name = scanner.nextLine();
			int x = scanner.nextInt();
			int y = scanner.nextInt();
			scanner.nextLine();//read newline char
			nodes.add(new Node(name, i, new Point(x, y)));
		}
		return nodes;
	}

	public ArrayList<Node> getNodes() {return nodes;}

	public void buildRandomRoute()
	{

		boolean[] visited = new boolean[nodes.size()];
		Stack<Node> stack = new Stack();
		Random rand = new Random();
		int randInt, numOfVisitedNodes, i;
		Node node = nodes.get(0), next;
		stack.push(node);
		visited[0] = true;
		numOfVisitedNodes = 1;
		while (!stack.isEmpty())
		{
			if (numOfVisitedNodes == nodes.size())
				break;
			node = stack.pop();
			randInt = rand.nextInt(nodes.size() - numOfVisitedNodes);
			if (randInt == 0)
				randInt++;
			for (i = 0; i < nodes.size() && randInt > 0; i++)
				if (!visited[i])
					randInt--;
			next = this.nodes.get(i);
			node.setNext(next);
			next.setPrev(node);
			node.setDistanceToNext(next);
			visited[next.getId()] = true;
			numOfVisitedNodes++;
			stack.push(next);

		}
	}

	public void buildNearestNeighbourGraph()
    {
    	Stack<Node> stack = new Stack();
    	int numOfNodes = this.nodes.size();
		int nodeIndex = 0, min, minIndex;
		boolean[] visited = new boolean[numOfNodes];
		boolean foundMin = false;
		Node currNode, nextNode;
		distanceMatrix = new int[numOfNodes][numOfNodes];
		for (int i = 0; i < numOfNodes; i++)
		{
			Node n1 = nodes.get(i);
			for (int j = 0; j < numOfNodes; j++)
				this.distanceMatrix[i][j] = n1.distanceToNode(nodes.get(j));
		}
		System.out.println(Arrays.deepToString(distanceMatrix));

		visited[nodeIndex] = true;
		stack.push(nodes.get(nodeIndex));
		while (!stack.isEmpty())
		{
			currNode = stack.pop();
			min = Integer.MAX_VALUE;
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
				nextNode.setPrev(currNode);
				currNode.setDistanceToNext(nextNode);
				stack.push(nextNode);
			}
		}
    }

    public ArrayList<Node> cloneNodes(Node node)
    {
    	//creates new ArrayList with all nodes except the given node
    	ArrayList<Node> newNodes = new ArrayList<>(this.nodes.size());
		this.nodes
				.stream()
				.filter(n -> n.getId() != node.getId())
				.forEach(n -> newNodes.add(n.clone()));
    	return newNodes;
    }

    public void removeLongestEdge()
	{
		int maxDistance = 0, maxIndex = -1;
		Node node, nextNode;
		for (int i = 0; i < nodes.size(); i++)
		{
			node = nodes.get(i);
			if (node.getDistanceToNext() > maxDistance)
			{
				maxDistance = node.getDistanceToNext();
				maxIndex = i;
			}
		}
		node  = nodes.get(maxIndex);
		nextNode = node.getNext();
		node.setNext(null);
		nextNode.setPrev(null);
		node.setDistanceToNext(null);
	}

	public void resetNodes() {nodes.forEach(node -> node.resetNode());}

	public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		for (Node node : nodes)
			stringBuilder.append(node.toString());
		return stringBuilder.toString();
	}

}
