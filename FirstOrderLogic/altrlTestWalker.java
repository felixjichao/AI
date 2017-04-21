import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.LinkedList;

public class altrlTestWalker extends folBaseListener {
    public int count = 0;
    public int negation = 0;
    public int level = 0;
    public LinkedList<String> result = new LinkedList<>();

    public LinkedList<MyTreeNode> newTree = new LinkedList<>();
    private String mode;

    public altrlTestWalker(String mode) {
        this.mode = mode;
    }

    @Override
    public void enterNegation(folParser.NegationContext ctx) {

        negation++;
    }

    @Override
    public void exitNegation(folParser.NegationContext ctx) {
        this.buildNegaTree();
        if (mode.equals("negaInward")) {
            ParseTree current = ctx.getChild(1).getChild(1);
            if (count == 0) {
                if ((negation & 1) == 0) {
                    result.add("(" + current.getText() + ")");
                }
                else {
                    String term1 = current.getChild(0).getText();
                    String term2 = current.getChild(2).getText();
                    result.add("(" + (term1.contains("~") ? term1.substring(1) : ("~" + term1))
                            + (current.getText() == "&" ? "|" : "&")
                            + (term2.contains("~") ? term1.substring(1) : ("~" + term2)) + ")");
                }
            }
            else {
                if ((negation & 1) == 0) {
                    result.add("(" + current.getChild(0).getText() + (current.getChild(1).getText()) + result.get(count-1) +  ")");
                }
                else {
                    String term = current.getChild(0).getText();
                    result.add("(" + (term.contains("~") ? term.substring(1) : ("~" + term))
                            + (current.getChild(1).getText() == "&" ? "|" : "&")
                            + result.get(count-1) + ")");
                }
            }
            count++;
            negation--;
        }

    }

    @Override
    public void enterConjunction(folParser.ConjunctionContext ctx) {
        level++;
    }

    @Override
    public void exitConjunction(folParser.ConjunctionContext ctx) {
        //ParseTree current = ctx.getChild(1).getChild(1);
        this.buildTree(ctx);
        if (mode.equals("distribution")) {
            this.buildTree(ctx);
        }

    }

    @Override
    public void enterDisjunction(folParser.DisjunctionContext ctx) {
        level++;
    }

    @Override
    public void exitDisjunction(folParser.DisjunctionContext ctx) {
        this.buildTree(ctx);
        if (mode.equals("distribution")) {
            this.buildTree(ctx);
        }
    }

    @Override
    public void enterImplication(folParser.ImplicationContext ctx) {


//        System.out.println("Premise:" + ctx.getChild(0).getChildCount());
//        ctx.getChild(1);
//        System.out.println("Conclusion:" + ctx.getChild(2).getChildCount());
    }

    @Override
    public void exitImplication(folParser.ImplicationContext ctx) {
        this.buildTree(ctx);
        if (mode.equals("implyElim")) {
            if (count == 0) {
                result.add("(~" + ctx.getChild(0).getText() + ") |" + ctx.getChild(2).getText());
            }
            else {
                result.add("(~" + result.get(count-1) + ") |" + ctx.getChild(2).getText());
            }
            count++;
        }

    }

    @Override
    public void enterAtom(folParser.AtomContext ctx) { }

    @Override
    public void exitAtom(folParser.AtomContext ctx) { }


    @Override
    public void enterEveryRule(ParserRuleContext ctx) { }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) { }

    @Override
    public void visitTerminal(TerminalNode node) { }

    @Override
    public void visitErrorNode(ErrorNode node) { }

    private void buildTree(ParseTree ctx) {
        int leftLen = ctx.getChild(0).getChildCount();
        int rightLen = ctx.getChild(2).getChildCount();
        MyTreeNode parent = new MyTreeNode(ctx.getChild(1).getText());
        if (leftLen >= 4 || (ctx.getChild(0).getText().charAt(1) == '~'
            && ctx.getChild(0).getText().charAt(2) != '(')) {
            parent.left = processNega(ctx.getChild(0).getText());
            if (rightLen >= 4 || (ctx.getChild(2).getText().charAt(1) == '~'
                && ctx.getChild(2).getText().charAt(2) != '(')) {
                parent.right = processNega(ctx.getChild(2).getText());
            }
            else {
                parent.right = newTree.get(newTree.size() - 1);
            }
        }
        else {
            if (rightLen >= 4 || (ctx.getChild(2).getText().charAt(1) == '~'
                && ctx.getChild(2).getText().charAt(2) != '(')) {
                parent.left = newTree.get(newTree.size() - 1);
                parent.right = processNega(ctx.getChild(2).getText());
            }
            else {
                parent.left = newTree.get(newTree.size() - 2);
                parent.right = newTree.get(newTree.size() - 1);
            }
        }
        newTree.add(parent);
        count++;
        level--;
    }

    private MyTreeNode processNega(String atom) {
        if (atom.charAt(1) == '~') {
            MyTreeNode nega = new MyTreeNode("~");
            nega.left = new MyTreeNode("");
            nega.right = new MyTreeNode(atom.substring(2, atom.length() - 1));
            return nega;
        }
        else {
            return new MyTreeNode(atom);
        }
    }

    private void buildNegaTree() {
        MyTreeNode parent = new MyTreeNode("~");
        parent.right = newTree.get(newTree.size() - 1);
        parent.left = new MyTreeNode("");
        newTree.add(parent);
    }
}