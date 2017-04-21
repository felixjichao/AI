public class SearchSpace {
    private String startState;
    private String goalState;
    private NodeState[] vertex;
    private int numNode;

    private int numEdge;

    public SearchSpace(String startState, String goalState, int numEdge) {
        this.startState = startState;
        this.goalState = goalState;
        numNode = 0;
        vertex = new NodeState[2 * numEdge];

    }

    public void addEdge(String parent, String child, int weight) {
        int index1 = this.checkState(parent);
        int index2 = this.checkState(child);
        if (index1 == -1) {
            vertex[numNode] = new NodeState(parent);
            if (index2 == -1) {
                vertex[numNode + 1] = new NodeState(child);
                vertex[numNode].addChild(new NodeState(child), weight);
                numNode+= 2;
            }
            else {

                vertex[numNode].addChild(new NodeState(child), weight);
                numNode++;
            }
        }
        else {
            if (index2 == -1) {
                vertex[numNode] = new NodeState(child);
                vertex[index1].addChild(new NodeState(child), weight);
                numNode++;
            }
            else {
                vertex[index1].addChild(new NodeState(child), weight);
            }
        }

    }


    public int checkState(String target) {
        for (int i = 0; i < numNode; i++) {
            if (vertex[i].getNode().equals(target)) {
                return i;
            }
        }
        return -1;
    }

    public NodeState getState(String target) {
        int index = this.checkState(target);
        return vertex[index];
    }
}

