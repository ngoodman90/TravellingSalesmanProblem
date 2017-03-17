import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Noam on 3/15/2017.
 */

public class DistanceGraph {

    private int N;
    private ArrayList<DistanceNode> nodes;
    private double routeLength;
    private double[][] distanceMatrix;

    public DistanceGraph(int N)
    {
        this.N = N;
        nodes = createNodes();
        routeLength = Double.MAX_VALUE;
        distanceMatrix = buildDistanceMatrix();
    }

    public double getRouteLength(){return routeLength;}

    private ArrayList<DistanceNode> createNodes()
    {
        ArrayList<DistanceNode> nodes = new ArrayList<>(N);

        for (int i = 0; i < N; i++)
            nodes.add(new DistanceNode(i));

        return nodes;
    }

    private double[][] buildDistanceMatrix()
    {
        double[][] distanceMatrix = new double[N][N];
        Random random = new Random();

        for (int i = 0; i < N; i++)
            for (int j = i + 1; j < N; j++)
            {
                distanceMatrix[i][j] = random.nextDouble();
                distanceMatrix[j][i] = distanceMatrix[i][j];
            }

        return distanceMatrix;
    }


    public void buildRandomRoute()//switch this to shuffle
    {
        /*Random random = ThreadLocalRandom.current();
        int rand;
        boolean[] used = new boolean[N];
        int nodesLeft = N;
        int index;
        for (int i = 0; i < N; i++)
        {
            rand = random.nextInt(nodesLeft);
            index = 0;
            while (rand > 0)
            {
                if (!used[index])
                    rand--;
                index++;
            }
            nodes.add(index);
        }*/

        Collections.shuffle(nodes);
        DistanceNode node1, node2;
        node1 = nodes.get(0);
        for (int i = 0; i < N; i++)
        {
            if (i != N - 1) // not last node
            {
                node2 = nodes.get(i + 1);
                node1.setNext(node2);
                node1 = node2;
            }
            else
            {
                node2 = nodes.get(i);
                node2.setNext(nodes.get(0));
            }
        }
    }

    /*public void buildGreedyRoute()
    {
        DistanceNode minNode1;
        DistanceNode minNode2;
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
                        if (distanceMatrix[i][j] > 0.0 && distanceMatrix[i][j] < min)
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

    public boolean isCircuit(DistanceNode n)
    {
        int numOfNodesInRoute = 0;
        DistanceNode tempNode = n;
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
        ArrayList<DistanceNode> rearrangedRoute = new ArrayList<>(N);
        DistanceNode firstNode = nodes.get(0);
        DistanceNode currNode = firstNode.getNext();
        rearrangedRoute.add(firstNode);
        while (!(currNode.getId() == firstNode.getId()))
        {
            rearrangedRoute.add(currNode);
            currNode = currNode.getNext();
        }
        nodes = rearrangedRoute;
    }*/

    public void buildGreedyRoute()
    {
        DistanceNode minNode1;
        DistanceNode minNode2;
        IntPoint minEdge;

        boolean[] hasNext = new boolean[N];
        boolean[] hasPrev = new boolean[N];

        double[][] tempDistanceMatrix = matrixDeepCopy();

        minEdge = findMinEdge(tempDistanceMatrix, hasNext, hasPrev);
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
            tempDistanceMatrix[minEdge.getX()][minEdge.getY()] = Double.MAX_VALUE;
            tempDistanceMatrix[minEdge.getY()][minEdge.getX()] = Double.MAX_VALUE;
            minEdge = findMinEdge(tempDistanceMatrix, hasNext, hasPrev);
        }

        rearrangeNodes();
    }

    public IntPoint findMinEdge(double[][] tempDistanceMatrix, boolean[] hasNext, boolean[] hasPrev)
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
                        if (tempDistanceMatrix[i][j] > 0 && tempDistanceMatrix[i][j] < min)
                        {
                            min = tempDistanceMatrix[i][j];
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

    public boolean isCircuit(DistanceNode n)
    {
        int numOfNodesInRoute = 0;
        DistanceNode tempNode = n;
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
        ArrayList<DistanceNode> rearrangedRoute = new ArrayList<>(N);
        DistanceNode firstNode = nodes.get(0);
        DistanceNode currNode = firstNode.getNext();
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
        ArrayList<DistanceNode> newRoute;
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
                        continue Outer;
                    }
                }
            }
        }
    }


    public boolean shouldTrySwap(int nodeIndex1, int nodeIndex2)
    {
        int n1, n2, n3, n4;
        double n1_n2, n3_n4, n1_n3, n2_n4;

        n1 = nodes.get(nodeIndex1).getId();
        n2 = nodes.get(nodeIndex1).getNext().getId();
        n3 = nodes.get(nodeIndex2).getId();
        n4 = nodes.get(nodeIndex2).getNext().getId();
        n1_n2 = distanceMatrix[n1][n2];
        n3_n4 = distanceMatrix[n3][n4];
        n1_n3 = distanceMatrix[n1][n3];
        n2_n4 = distanceMatrix[n2][n4];

        return (n1_n2 + n3_n4 > n1_n3 + n2_n4);
    }

    public ArrayList<DistanceNode> swap(DistanceNode n1, DistanceNode n2)
    {
        ArrayList<DistanceNode> newRoute = new ArrayList<>(N);
        ArrayList<DistanceNode> firstPart = new ArrayList<>();
        ArrayList<DistanceNode> secondPart = new ArrayList<>();
        ArrayList<DistanceNode> thirdPart = new ArrayList<>();
        DistanceNode curr = nodes.get(0);

        curr = addNodesToRoute(firstPart, curr, n1.getNext());
        curr = addNodesToRoute(secondPart, curr, n2.getNext());
        addNodesToRoute(thirdPart, curr, nodes.get(0));

        reverseRoute(secondPart);

        appendRoute(newRoute, firstPart);
        appendRoute(newRoute, secondPart);
        appendRoute(newRoute, thirdPart);

        return newRoute;
    }

    private void appendRoute(ArrayList<DistanceNode> route1, ArrayList<DistanceNode> route2)
    {
        if (route1.size() == 0)
            route1.addAll(route2);

        else if (!(route2.size() == 0))
        {
            DistanceNode firstNodeRoute1, lastNodeRoute1, firstNodeRoute2;
            lastNodeRoute1 = route1.get(route1.size() - 1);
            firstNodeRoute2 = route2.get(0);
            lastNodeRoute1.setNext(firstNodeRoute2);

            route1.addAll(route2);

            firstNodeRoute1 = route1.get(0);
            lastNodeRoute1 = route1.get(route1.size() - 1);
            lastNodeRoute1.setNext(firstNodeRoute1);
        }
    }

    private DistanceNode addNodesToRoute(ArrayList<DistanceNode> route, DistanceNode curr, DistanceNode n)
    {
        while (!curr.equals(n))
        {
            route.add(curr);
            curr = curr.getNext();
        }
        return curr;
    }

    public void reverseRoute(ArrayList<DistanceNode> route)
    {
        if (route.size() > 1)
        {
            DistanceNode currNode, nextNode;
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

    public double calculateRouteLength(ArrayList<DistanceNode> nodes)
    {
        double routeLength = 0.0;
        for (DistanceNode node : nodes)
            routeLength += getDistance(node);
        return routeLength;
    }

    public void setRouteLength()
    {
        double routeLength = 0.0;
        for (DistanceNode node : nodes)
            routeLength += getDistance(node);
        this.routeLength =  routeLength;
    }

    public int nodesSize(){return nodes.size();}

    public void setRouteLength(double routeLength)
    {
        this.routeLength = routeLength;
    }

    public double getDistance(DistanceNode node)
    {
        return distanceMatrix[node.getId()][node.getNext().getId()];
    }

    public void resetGraph()
    {
        nodes = createNodes();
        routeLength = Double.MAX_VALUE;
    }

    private double[][] matrixDeepCopy()
    {
        double[][] matrixCopy = new double[N][N];
        for (int i = 0; i < N; i++)
            matrixCopy[i] = distanceMatrix[i].clone();
        return matrixCopy;
    }


}
