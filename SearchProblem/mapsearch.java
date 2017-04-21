

import java.io.*;
import java.util.*;

public class mapsearch {


    public static void main(String[] args) {
        String algType;
        String startState;
        String goalState;
        SearchSpace search;
        HashMap<String, Integer> sundayLines;
        SearchCondition searchCondition;

        try {
            searchCondition = readInput();

            algType = searchCondition.algType;
            startState = searchCondition.startState;
            goalState = searchCondition.goalState;
            search = searchCondition.search;
            sundayLines = searchCondition.sundayLines;

            if (algType.equals("BFS")) {
                searchByBFS(startState, goalState, search);
            }
            else if (algType.equals("DFS")) {
                searchByDFS(startState, goalState, search);
            }
            else {
                Comparator<NodeState> comparator = null;
                if (algType.equals("UCS")) {
                    comparator = new PathCostComparator(search);
                }
                else if (algType.equals("A*")) {
                    comparator = new AStarComparator(search, sundayLines);
                }
                searchByCost(startState, goalState, search, comparator);
            }

            printToFile(algType, startState, goalState, search);

        }
        catch (FileNotFoundException exc) {
            System.out.println("File Not Found");
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private static SearchCondition readInput() throws IOException {
        File inputFile = new File("input.txt");
        Scanner in = new Scanner(inputFile);

        String algType = in.nextLine();
        String startState = in.nextLine();
        String goalState = in.nextLine();
        int numOfLines = in.nextInt();

        SearchSpace search = new SearchSpace(startState, goalState, numOfLines);


        for (int i = 0; i < numOfLines; i++) {
            String parent = in.next();
            String child = in.next();
            int weight = in.nextInt();
            search.addEdge(parent, child, weight);
        }

        HashMap<String, Integer> sundayLines = new HashMap<>();
        if (algType.equals("A*")) {
            int numOfSundayLines = in.nextInt();
            for (int i = 0; i < numOfSundayLines; i++) {
                String location = in.next();
                int accumulated = in.nextInt();
                sundayLines.put(location, accumulated);
            }
        }

        in.close();

        return new SearchCondition(algType, startState, goalState, search, sundayLines);

    }

    private static void searchByBFS(String startState,
                                    String goalState,
                                    SearchSpace search) {
        Queue<NodeState> frontier = new LinkedList<>();
        Set<NodeState> exploredSet = new HashSet<>();

        frontier.add(search.getState(startState));
        boolean found = false;
        while(!found) {
            if (frontier.isEmpty()) {
                found = false;
                break;
            }
            NodeState current = frontier.poll();
            if (current.getNode().equals(goalState)) {
                found = true;
                break;
            }
            exploredSet.add(current);
            LinkedList<NodeState> expandSet = current.getChildren();
            for (NodeState elem : expandSet) {
                NodeState child = search.getState(elem.getNode());
                if (!frontier.contains(child) && !exploredSet.contains(child)) {
                    child.setParent(current);
                    child.setPathCost(current.getPathCost() + elem.getStepCost());
                    child.setDepth(current.getDepth() + 1);
                    frontier.add(child);
                }
            }
        }
    }

    private static void searchByDFS(String startState,
                                    String goalState,
                                    SearchSpace search) {
        Stack<NodeState> frontier = new Stack<>();
        Set<NodeState> exploredSet = new HashSet<>();

        frontier.push(search.getState(startState));
        boolean found = false;
        while(!found) {
            if (frontier.isEmpty()) {
                found = false;
                break;
            }
            NodeState current = frontier.pop();
            if (current.getNode().equals(goalState)) {
                found = true;
                break;
            }
            exploredSet.add(current);
            LinkedList<NodeState> expandSet = current.getChildren();
            Collections.reverse(expandSet);
            for (NodeState elem : expandSet) {
                NodeState child = search.getState(elem.getNode());
                if (frontier.search(child) == -1 && !exploredSet.contains(child)) {
                    child.setParent(current);
                    child.setPathCost(current.getPathCost() + elem.getStepCost());
                    child.setDepth(current.getDepth() + 1);
                    frontier.push(child);
                }
            }
        }
    }

    private static void searchByCost(String startState,
                                     String goalState,
                                     SearchSpace search,
                                     Comparator<NodeState> comparator) {
        PriorityQueue<NodeState> frontier = new PriorityQueue<>(10, comparator);
        Set<NodeState> exploredSet = new HashSet<>();

        frontier.add(search.getState(startState));
        boolean found = false;
        int arrivalTime = 0;
        while(!found) {
            if (frontier.isEmpty()) {
                found = false;
                break;
            }
            NodeState current = frontier.poll();
            if (current.getNode().equals(goalState)) {
                found = true;
                break;
            }
            exploredSet.add(current);
            LinkedList<NodeState> expandSet = current.getChildren();
            for (NodeState elem : expandSet) {
                NodeState child = search.getState(elem.getNode());
                int childPathCost = current.getPathCost() + elem.getStepCost();
                if (!frontier.contains(child) && !exploredSet.contains(child)) {
                    child.setParent(current);
                    child.setPathCost(childPathCost);
                    child.setDepth(current.getDepth() + 1);
                    arrivalTime++;
                    child.setArrivalTime(arrivalTime);
                    frontier.add(child);
                }
                else if (frontier.contains(child)) {
                    Iterator<NodeState> iter = frontier.iterator();
                    while (iter.hasNext()) {
                        NodeState curr = iter.next();
                        if (curr.getNode().equals(child.getNode())
                                && curr.getPathCost() > childPathCost) {
                            child.setParent(current);
                            child.setPathCost(childPathCost);
                            child.setDepth(current.getDepth() + 1);
                            curr.setArrivalTime(0);
                            frontier.remove(curr);
                            arrivalTime++;
                            child.setArrivalTime(arrivalTime);
                            frontier.add(child);
                            break;
                        }
                    }
                }
            }
        }
    }

    private static void printToFile(String algorithm, String startState, String goalState,
                                    SearchSpace search) throws IOException {
        FileWriter outputFile = new FileWriter("output.txt");

        NodeState backtrack = search.getState(goalState);
        Stack<NodeState> result = new Stack<>();
        while (!backtrack.getNode().equals(startState)) {
            //System.out.println(backtrack.getNode() + " " + backtrack.getPathCost());
            result.push(backtrack);
            backtrack = backtrack.getParent();
        }
        outputFile.write(startState + " 0\n");

        while (!result.empty()) {
            NodeState print = result.pop();
            if (algorithm.equals("BFS") || algorithm.equals("DFS")) {
                outputFile.write(print.getNode() + " " + print.getDepth() + "\n");
            }
            else {
                outputFile.write(print.getNode() + " " + print.getPathCost() + "\n");
            }

        }
        outputFile.close();
    }

    private static class SearchCondition {
        public String algType = "";
        public String startState = "";
        public String goalState = "";
        public SearchSpace search = null;
        public HashMap<String, Integer> sundayLines = null;

        public SearchCondition(String algType, String startState,
                               String goalState, SearchSpace search,
                               HashMap<String, Integer> sundayLines) {
            this.algType = algType;
            this.startState = startState;
            this.goalState = goalState;
            this.search = search;
            this.sundayLines = sundayLines;
        }
    }

}