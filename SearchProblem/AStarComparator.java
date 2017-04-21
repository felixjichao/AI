import java.util.Comparator;
import java.util.HashMap;


public class AStarComparator implements Comparator<NodeState> {
    private SearchSpace search;
    private HashMap<String, Integer> sundayLines;

    public AStarComparator(SearchSpace search, HashMap<String, Integer> sundayLines) {
        this.search = search;
        this.sundayLines = sundayLines;
    }

    public int compare(NodeState a, NodeState b) {
        if (a.getNode().equals(b.getNode())) {
            return 0;
        }

        int estimateA = a.getPathCost() + sundayLines.get(a.getNode());
        int estimateB = b.getPathCost() + sundayLines.get(b.getNode());

        if (estimateA == estimateB) {
            if (!a.getParent().equals(b.getParent())) {
                return a.getArrivalTime() - b.getArrivalTime();
            }
            else {
                NodeState parent = search.getState(a.getParent().getNode());
                int indexA = parent.getChildIndex(a);
                int indexB = parent.getChildIndex(b);
                return indexA - indexB;
            }
        }



        return estimateA - estimateB;
    }
}