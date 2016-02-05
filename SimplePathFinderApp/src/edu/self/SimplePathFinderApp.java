package edu.self;

import java.util.*;

public class SimplePathFinderApp {

    private static final List<Node> nodes = new ArrayList<>();
    private static final List<Edge> edges = new ArrayList<>();
    private static Set<Node> settledNodes;
    private static Set<Node> unSettledNodes;
    private static Map<Node, Node> predecessors;
    private static Map<Node, Integer> distance;
    
    public static void main(String[] args) {

        String inputFileName = args[0];
        String rootNode = args[1];

        AdjacencyList adjacencyList = new AdjacencyList(inputFileName);
        nodes.addAll(adjacencyList.getAdjacencyList().keySet());

        Map<Node, List<Edge>> list = adjacencyList.getAdjacencyList();
        for(Node node : list.keySet()){
            List<Edge> edgeList = list.get(node);
            edges.addAll(edgeList);
        }

        //print header
        System.out.println("Node \t Cost \t Next-Hop");

        //perform dijkstras for each node as the destination from the root node
        Node source = null;
        for(Node node : adjacencyList.getAdjacencyList().keySet()){
            if(node.getAddr() == Integer.valueOf(rootNode)){
                source = node;
                break;
            }
        }

        if(source != null) {
            execute(source);
        }

        for(Node node : adjacencyList.getAdjacencyList().keySet()){

            if(node.getAddr() != source.getAddr()) {
                LinkedList<Node> path = getPath(node);
                System.out.println(node.getAddr() + " \t " + distance.get(node) + " \t " + path.get(1).getAddr());
            }
        }
    }

    public static void execute(Node source) {
        settledNodes = new HashSet<Node>();
        unSettledNodes = new HashSet<Node>();
        distance = new HashMap<Node, Integer>();
        predecessors = new HashMap<Node, Node>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Node node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }


    private static void findMinimalDistances(Node node) {

        List<Node> adjacentNodes = getNeighbors(node);
        for (Node target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private static int getDistance(Node node, Node target) {
        for (Edge edge : edges) {
            if (edge.start.equals(node)
                    && edge.end.equals(target)) {
                return edge.weight;
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private static List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<Node>();
        for (Edge edge : edges) {
            if (edge.start.equals(node)
                    && !isSettled(edge.end)) {
                neighbors.add(edge.end);
            }
        }
        return neighbors;
    }

    private static Node getMinimum(Set<Node> Nodees) {
        Node minimum = null;
        for (Node Node : Nodees) {
            if (minimum == null) {
                minimum = Node;
            } else {
                if (getShortestDistance(Node) < getShortestDistance(minimum)) {
                    minimum = Node;
                }
            }
        }
        return minimum;
    }

    private static  boolean isSettled(Node Node) {
        return settledNodes.contains(Node);
    }

    private static int getShortestDistance(Node destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public static LinkedList<Node> getPath(Node target) {
        LinkedList<Node> path = new LinkedList<Node>();
        Node step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

}
