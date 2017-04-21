import java.util.LinkedList;

public class MyTreeNode {
    public String data;
    public MyTreeNode left;
    public MyTreeNode right;
    public boolean isOperator = false;


    public MyTreeNode(String data) {
        if (data.equals("")) {
            this.data = data;
            left = null;
            right = null;
        }
        else if (data.equals("~")) {
            this.data = data;
            left = null;
            right = null;
        }
        else if (data.charAt(0) == '~' && data.charAt(1) != '(') {
            this.data = "~";
            left = new MyTreeNode("");
            right = new MyTreeNode(data.substring(1));
        }
        else {
            this.data = data;
            left = null;
            right = null;
        }

        if (data.equals("|") || data.equals("&") || data.equals("=>") || this.data.equals("~") ) {
            isOperator = true;
        }
    }

    public LinkedList<String> getAllAtoms() {
        LinkedList<String> allAtoms = new LinkedList<>();
        if (!this.isOperator) {
            allAtoms.add(this.toString());
        }
        else if (this.data.equals("~")) {
            allAtoms.add(this.toString().substring(1, this.toString().length()-1));
        }
        /*else if (!this.right.data.equals("|")
                && this.left.data.equals("|")) {
            allAtoms.add(this.right.toString());
            allAtoms.addAll(this.left.getAllAtoms());
        }
        else if (!this.left.data.equals("|")
                && this.right.data.equals("|")) {
            allAtoms.add(this.left.toString());
            allAtoms.addAll(this.right.getAllAtoms());
        }*/
        else {
            allAtoms.addAll(this.left.getAllAtoms());
            allAtoms.addAll(this.right.getAllAtoms());
        }

        return allAtoms;
    }

    public MyTreeNode getFirst() {
        if (!isOperator || this.data.equals("~")) {
            return this;
        }
        else {
            return this.right;
        }
    }

    @Override
    public String toString() {
        if (!this.isOperator) {
            return this.data;
        }
        if (!this.right.isOperator) {
            return "(" + this.left.toString() + this.data + this.right.data + ")";
        }
        else if (!this.left.isOperator) {
            return "(" + this.left.data + this.data + this.right.toString() + ")";
        }
        else {
            return "(" + this.left.toString() + this.data + this.right.toString() + ")";
        }
    }

//    @Override
//    public int hashCode() {
//
//    }
//
//    @Override
//    public boolean equals(Object object) {
//
//    }
}