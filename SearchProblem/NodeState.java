
import java.util.LinkedList;


public class NodeState {
    private String node;
    private NodeState parent;
    private int pathCost;
    private int depth;
    private int stepCost;
    private LinkedList<NodeState> children = new LinkedList<>();

    private int arrivalTime = 0;

    public NodeState(String node) {
        this.node = node;
    }

    public String getNode() {
        return node;
    }

    public NodeState getParent() {
        return parent;
    }

    public int getPathCost() {
        return pathCost;
    }

    public int getDepth() {
        return depth;
    }

    public int getStepCost() {
        return stepCost;
    }

    public LinkedList<NodeState> getChildren() {
        return children;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void addChild(NodeState child, int weight) {
        children.add(child);
        int index = getChildIndex(child);
        children.get(index).setStepCost(weight);
    }

    public void setParent(NodeState parent) {
        this.parent = parent;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setStepCost(int stepCost) {
        this.stepCost = stepCost;
    }

    public void setArrivalTime(int time) {
        arrivalTime = time;
    }

    @Override
    public boolean equals(Object o) {
        if ( o instanceof NodeState &&
                ((NodeState) o).getNode().equals(this.node)) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.node.hashCode();
    }


    public int getChildIndex(NodeState child) {
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).equals(child)) {
                return i;
            }
        }
        return -1;
    }


}
