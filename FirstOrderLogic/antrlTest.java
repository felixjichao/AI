import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.*;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class antrlTest {
    public static void main(String[] args) throws Exception {
        //String[] res = ("A(x, y, z)".split("[~(,)]"));


        File input = new File("input.txt");
        Scanner in = new Scanner(input);
        int numOfQueries = in.nextInt();
        in.nextLine();
        LinkedList<String> queries = new LinkedList<>();
        for (int i = 0; i < numOfQueries; i++) {
            queries.add(in.nextLine().replaceAll("\\s+", ""));
        }
        int numOfSent = in.nextInt();
        in.nextLine();
        KnowledgeBase knowledgeBase = new KnowledgeBase();
        for (int i = 0; i < numOfSent; i++) {
            String sentence = in.nextLine().replaceAll("\\s+", "");
            LinkedList<MyTreeNode> clauses;
            if (!sentence.contains("|") && !sentence.contains("&") && !sentence.contains("=>")) {
//                MyTreeNode atom = new MyTreeNode(sentence);
//                knowledgeBase.tell(atom);
                if (sentence.contains("~")) {
                    MyTreeNode atom = new MyTreeNode(sentence.substring(1, sentence.length() - 1));
                    knowledgeBase.tell(atom);
                }
                else {
                    MyTreeNode atom = new MyTreeNode(sentence);
                    knowledgeBase.tell(atom);
                }
            }
            else {
                altrlTestWalker myWalker = travelString(sentence, "none");
                MyTreeNode implyTree = implyElim(myWalker.newTree.getLast());
                //String implyElim = implyTree.toString();
                MyTreeNode negaInwards = negaInwards(implyTree);
                MyTreeNode distribution = distribute(negaInwards, negaInwards.left, negaInwards.right);
                clauses = splitToClauses(distribution);
                clauses.forEach(knowledgeBase::tell);
            }

        }
        //boolean res = knowledgeBase.ask(queries.get(0));
        LinkedList<String> result = new LinkedList<>();
        for (String query : queries) {
            if (knowledgeBase.ask(query)) {
                result.add("TRUE");
            }
            else {
                result.add("FALSE");
            }
        }


//        myWalker = travelString(implyElim, "negaInward");
//        String negaInward = myWalker.result.getLast();
//        myWalker = travelString(implyElim, "distribution");
//        MyTreeNode newTree = myWalker.newTree.getLast();
//        MyTreeNode cnf = distribute(newTree, newTree.left, newTree.right);

        FileWriter output = new FileWriter("output.txt");
        for (int i = 0; i < result.size() - 1; i++) {
            output.write(result.get(i) + "\n");
        }
        output.write(result.get(result.size() - 1));

        in.close();
        output.close();


    }

    public static altrlTestWalker travelString(String sentence, String mode) {
        folLexer lexer = new folLexer(new ANTLRInputStream(sentence));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        folParser parser = new folParser(tokens);
        ParseTree tree = parser.formula();
        ParseTreeWalker walker = new ParseTreeWalker();
        altrlTestWalker myWalker = new altrlTestWalker(mode);
        walker.walk(myWalker, tree);
        return myWalker;
    }


    public static MyTreeNode implyElim(MyTreeNode parent) {
        if (!parent.isOperator ) {
            return parent;
        }
        if (!parent.right.isOperator) {
            parent.left = implyElim(parent.left);

        }
        else if (!parent.left.isOperator) {
            parent.right = implyElim(parent.right);
        }
        else {
            parent.left = implyElim(parent.left);
            parent.right = implyElim(parent.right);
        }

        MyTreeNode newParent = new MyTreeNode("|");
        MyTreeNode newLeft = new MyTreeNode("~");
        MyTreeNode newRight = new MyTreeNode("");
        if (parent.data.equals("=>")) {
            newParent.left = newLeft;
            newLeft.left = newRight;
            newLeft.right = parent.left;
            newParent.right = parent.right;
            return newParent;
        }
        return parent;
    }

    public static MyTreeNode negaInwards(MyTreeNode parent) {
        if (parent.data.equals("~") && parent.right.isOperator) {
            MyTreeNode newParent;
            MyTreeNode newLeft = new MyTreeNode("~");
            MyTreeNode newRight = new MyTreeNode("~");
            if (parent.right.data.equals("~")) {
                newParent = parent.right.right;
            }
            else  {
                if (parent.right.data.equals("&")) {
                    newParent = new MyTreeNode("|");
                }
                else {
                    newParent = new MyTreeNode("&");
                }
                newLeft.left = new MyTreeNode("");
                newLeft.right = parent.right.left;
                newRight.left = new MyTreeNode("");
                newRight.right = parent.right.right;
                newParent.left = negaInwards(newLeft);
                newParent.right = negaInwards(newRight);
            }
            return newParent;
        }
        if (!parent.isOperator) {
            return parent;
        }
        if (!parent.right.isOperator) {
            parent.left = negaInwards(parent.left);
        }
        else if (!parent.left.isOperator) {
            parent.right = negaInwards(parent.right);
        }
        else {
            parent.left = negaInwards(parent.left);
            parent.right = negaInwards(parent.right);
        }

        return parent;
    }

    public static MyTreeNode distribute(MyTreeNode parent, MyTreeNode left, MyTreeNode right) {
        if (!left.isOperator) {
            return parent;
        }
        if (!right.isOperator) {
            parent.left = distribute(left, left.left, left.right);
            //left = parent.left;

        }
        else if (!left.isOperator) {
            parent.right = distribute(right, right.left, right.right);
            //right = parent.right;
        }
        else {
            parent.left = distribute(parent.left, parent.left.left, parent.left.right);
            parent.right = distribute(parent.right, parent.right.left, parent.right.right);
            //left = parent.left;
            //right =parent.right;
        }



        MyTreeNode newParent = new MyTreeNode("&");
        MyTreeNode newLeft = new MyTreeNode("|");
        MyTreeNode newRight = new MyTreeNode("|");
        if (parent.data.equals("|")) {
            if (parent.left.data.equals("&") /*&& !left.left.isOperator && !left.right.isOperator*/
                    && (!parent.right.isOperator || parent.right.data.equals("~"))) {
                newLeft.left = parent.left.left;
                newLeft.right = parent.right;
                newRight.left = parent.left.right;
                newRight.right = parent.right;
                newParent.left = newLeft;
                newParent.right = newRight;
                return newParent;
            }
            else if (parent.right.data.equals("&") /*&& !right.left.isOperator && !right.right.isOperator*/
                    && (!parent.left.isOperator || parent.left.data.equals("~"))) {
                newLeft.left = parent.left;
                newLeft.right = parent.right.left;
                newRight.left = parent.left;
                newRight.right = parent.right.right;
                newParent.left = newLeft;
                newParent.right = newRight;
                return newParent;
            }
/*            else if (!left.isOperator && right.data.equals("&")) {
                newLeft.left = left;
                newLeft.right = distribute(right, right.left, right.right);
                newRight.left = left;
                newRight.right = distribute(right, right.left, right.right);
                newParent.left = newLeft;
                newParent.right = newRight;
                return newParent;
            }
            else if (!right.isOperator && left.data.equals("&")) {
                newLeft.left = distribute(left, left.left, left.right);
                newLeft.right = right;
                newRight.left = distribute(left, left.left, left.right);
                newRight.right = right;
                newParent.left = newLeft;
                newParent.right = newRight;
                return newParent;
            }
            else if (left.data.equals("&")) {
                newLeft.left = distribute(left.left, left.left.left, left.left.right);
                newLeft.right = distribute(right, right.left, right.right);
                newRight.left = distribute(left.right, left.right.left, left.right.right);
                newRight.right = distribute(right, right.left, right.right);
                newParent.left = newLeft;
                newParent.right = newRight;
                return newParent;
            }*/

        }

        return parent;
    }

    public static LinkedList<MyTreeNode> splitToClauses(MyTreeNode parent) {
        LinkedList<MyTreeNode> clauses = new LinkedList<>();
        if (!parent.isOperator || parent.data.equals("~")
            || parent.data.equals("|")) {
            clauses.add(parent);
        }
        else if (!parent.right.data.equals("&")
                && parent.left.data.equals("&")) {
            clauses.add(parent.right);
            clauses.addAll(splitToClauses(parent.left));
        }
        else if (!parent.left.data.equals("&")
                && parent.right.data.equals("&")) {
            clauses.add(parent.left);
            clauses.addAll(splitToClauses(parent.right));
        }
        else {
            clauses.addAll(splitToClauses(parent.left));
            clauses.addAll(splitToClauses(parent.right));
        }


        return clauses;
    }


    public static String printTree(MyTreeNode parent) {
        if (!parent.isOperator) {
            return parent.data;
        }
        if (!parent.right.isOperator) {
            return "(" + printTree(parent.left) + parent.data + parent.right.data + ")";
        }
        else if (!parent.left.isOperator) {
            return "(" + parent.left.data + parent.data + printTree(parent.right) + ")";
        }
        else {
            return "(" + printTree(parent.left) + parent.data + printTree(parent.right) + ")";
        }
    }
}