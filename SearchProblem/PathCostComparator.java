import java.util.Comparator;



public class PathCostComparator implements Comparator<NodeState> {
    private SearchSpace search;

    public PathCostComparator(SearchSpace search) {
        this.search = search;
    }
    public int compare(NodeState a, NodeState b) {
        if (a.getNode().equals(b.getNode())) {
            return 0;
        }

        if (a.getPathCost() == b.getPathCost()) {
            if ( a.getParent().equals(b.getParent())) {
                NodeState parent = search.getState(a.getParent().getNode());
                int indexA = parent.getChildIndex(a);
                int indexB = parent.getChildIndex(b);
                return indexA - indexB;
            }
            else {
                return a.getArrivalTime() - b.getArrivalTime();
            }
        }




        return a.getPathCost() - b.getPathCost();
    }
}