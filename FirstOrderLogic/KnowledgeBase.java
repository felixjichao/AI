import java.util.*;

/**
 * Created by felixjichao on 2016/11/18.
 */
public class KnowledgeBase {

    private ArrayList<MyTreeNode> sentences;
    private int numOfSent;
    private Map<String, Predicate> knowledgeBase;
    private Stack<String> visiting = new Stack<>();

    public KnowledgeBase() {
        sentences = new ArrayList<>();
        numOfSent = 0;
        knowledgeBase = new HashMap<>();

    }

    public void tell(MyTreeNode sentence) {
        sentences.add(sentence);
        LinkedList<String> allAtoms = sentence.getAllAtoms();
        boolean isLiteral = (allAtoms.size() == 1);
        for (String atom : allAtoms) {
            String predicateName = getPredicateName(atom);
            if (knowledgeBase.containsKey(predicateName)) {
                knowledgeBase.put(predicateName,
                        knowledgeBase.get(predicateName).updatePred(atom, numOfSent, isLiteral));
            }
            else {
                knowledgeBase.put(predicateName, new Predicate(atom, numOfSent, isLiteral));
            }

        }
        numOfSent++;
    }

    public boolean ask(String query) {
        //MyTreeNode target = new MyTreeNode(query);
        if (query.charAt(0) == '~') {
            MyTreeNode target = new MyTreeNode(query.substring(1));
            //visiting.push(target);
            return resolve(target);
        }
        else {
            MyTreeNode target = new MyTreeNode("~" + query);
            //visiting.push(target);
            return resolve(target);
        }
    }

    private boolean resolve(MyTreeNode root) {
        MyTreeNode atom = root.getFirst();
        visiting.push(atom.toString());

        String predicateName;
        boolean isNegative;
        if (atom.data.equals("~")) {
            predicateName = atom.right.data.split("\\(")[0];
            isNegative = true;
        }
        else {
            predicateName = atom.data.split("\\(")[0];
            isNegative = false;
        }

        Predicate predicate = knowledgeBase.get(predicateName);
        LinkedList<Integer> literal = !isNegative
                                    ? predicate.negativeLiteral
                                    : predicate.positiveLiteral;
        LinkedList<Integer> clause = !isNegative
                                    ? predicate.negativeClause
                                    : predicate.positiveClause;

        LinkedList<Integer> possible = new LinkedList<>();

        possible.addAll(literal);
        possible.addAll(clause);
        Set<Integer> possibleSet = new HashSet<>(possible);
        for (int index : possibleSet) {
            LinkedList<MyTreeNode> results = binaryResolution(root, sentences.get(index));
            for (MyTreeNode res : results) {
                if (res != null && res.data.equals("")) {
                    visiting.pop();
                    return true;
                }
                else if (res != null) {
                    boolean visited = visiting.contains(res.toString());
                    if (!visited && resolve(res)) {
                        visiting.pop();
                        return true;
                    }
                }
            }
        }
        visiting.pop();
        return false;
    }

    private LinkedList<MyTreeNode> binaryResolution(MyTreeNode c1, MyTreeNode c2) {
        MyTreeNode clause1 = standardize(c1);
        String target = clause1.getFirst().getAllAtoms().getFirst();
        String predicate;
        boolean isNegative;
        if (target.charAt(0) == '~') {
            predicate = target.substring(1).split("\\(")[0];
            isNegative = true;
        }
        else {
            predicate = target.split("\\(")[0];
            isNegative = false;
        }
        LinkedList<MyTreeNode> resolution = new LinkedList<>();
        LinkedList<String[]> argu1List = findArgu(predicate, clause1, !isNegative);
        LinkedList<String[]> argu2List = findArgu(predicate, c2, isNegative);
        for (String[] argu1 : argu1List) {
            for (String[] argu2 : argu2List) {
                HashMap<String, String> unification = unify(argu1, argu2);
                if (unification.size() != argu1.length - 1) {
                    resolution.add(null);
                    continue;
                }
                LinkedList<String> newClause = new LinkedList<>();
                newClause.addAll(clause1.getAllAtoms());
                newClause.addAll(c2.getAllAtoms());
                resolution.add(resolve(unification, predicate, newClause));
            }
        }

        return resolution;
    }

    private MyTreeNode resolve(HashMap<String, String> unification, String predicate,
                               LinkedList<String> newClause) {
        MyTreeNode root;
        //LinkedList<String> unifyRes = new LinkedList<>();
        Set<String> unifyRes = new HashSet<>();

        newClause.forEach(atom -> unifyRes.add(substitute(unification, atom)));

        for (String atom : unifyRes) {
            if (atom.charAt(0) == '~' && unifyRes.contains(atom.substring(1))) {
                unifyRes.remove(atom);
                unifyRes.remove(atom.substring(1));
                break;
            }
        }

        if (unifyRes.size() == 0) {
            return new MyTreeNode("");
        }
        if (unifyRes.size() == 1) {
            String data = substitute(unification, unifyRes.toArray(new String[1])[0]);
            return new MyTreeNode(data);
        }
        root = new MyTreeNode("|");
        MyTreeNode iterator = root;
        for (String atom : unifyRes) {
            String data = substitute(unification, atom);
            iterator.left = new MyTreeNode("|");
            iterator.right = new MyTreeNode(data);
            iterator = iterator.left;
        }
        iterator = root;
        while (iterator.left.left.left != null) {
            iterator = iterator.left;
        }
        iterator.left = iterator.left.right;
/*        int numOfAtoms = 0;
        LinkedList<String> remaining = new LinkedList<>();
        for (String atom : newClause) {
            if (!getPredicateName(atom).equals(predicate)) {
                numOfAtoms++;
                remaining.add(atom);
            }
        }
        if (numOfAtoms == 0) {
            return new MyTreeNode("");
        }
        if (numOfAtoms == 1) {
            String data = substitute(unification, remaining.getFirst());
            return new MyTreeNode(data);
        }
        root = new MyTreeNode("|");
        MyTreeNode iterator = root;
        for (String atom : remaining) {
            String data = substitute(unification, atom);
            iterator.left = new MyTreeNode("|");
            iterator.right = new MyTreeNode(data);
            iterator = iterator.left;
        }
        iterator = root;
        while (iterator.left.left.left != null) {
            iterator = iterator.left;
        }
        iterator.left = iterator.left.right;*/
        return root;
    }

    private String substitute(HashMap<String, String> unification, String origin) {
        String[] predArgu = origin.split("[(,)]");
        String data = predArgu[0] + "(";
        for (int i = 1; i < predArgu.length - 1; i++) {
            if (unification.containsKey(predArgu[i])) {
                data += unification.get(predArgu[i]) + ",";
            }
            else {
                data += predArgu[i] + ",";
            }
        }
        if (unification.containsKey(predArgu[predArgu.length - 1])) {
            data += unification.get(predArgu[predArgu.length - 1]) + ")";
        }
        else {
            data += predArgu[predArgu.length - 1] + ")";
        }
        return data;
    }

    private HashMap<String, String> unify(String[] argu1, String[] argu2) {
        HashMap<String, String> unification = new HashMap<>();
        for (int i = 1; i < argu1.length; i++) {
            if (!isVariable(argu1[i])) {
                if (!isVariable(argu2[i])) {
                    if (argu1[i].equals(argu2[i])) {
                        unification.put(argu1[i], argu1[i]);
                    }
                }
                else {
                    unification.put(argu2[i], argu1[i]);
                }
            }
            else {
                if (!isVariable(argu2[i])) {
                    unification.put(argu1[i], argu2[i]);
                }
                else {
                    unification.put(argu1[i], argu2[i]);
                }
            }
        }
        return unification;
    }

    private LinkedList<String[]> findArgu(String predicate, MyTreeNode root, boolean isNegative) {
        LinkedList<String> allAtoms = root.getAllAtoms();
        LinkedList<String[]> res = new LinkedList<>();
        for (String atom : allAtoms) {
            if (!isNegative) {
                if (atom.contains("~")
                        && getPredicateName(atom).equals(predicate)) {
                    res.add(atom.split("[(,)]"));
                }
            }
            else {
                if (!atom.contains("~")
                        && getPredicateName(atom).equals(predicate)) {
                    res.add(atom.split("[(,)]"));
                }
            }

        }
        return res;
    }

    private MyTreeNode standardize(MyTreeNode root) {
        if (!root.isOperator) {
            String[] split = root.data.split("[(,)]");

            String newData = "";
            newData += split[0] + "(";
            for (int i = 1; i < split.length - 1; i++) {
                if (isVariable(split[i]) && split[i].length() == 1) {
                    newData += split[i] + split[i] + ",";
                }
                else {
                    newData += split[i] + ",";
                }
            }
            if (isVariable(split[split.length-1])
                    && split[split.length-1].length() == 1) {
                newData += split[split.length-1] + split[split.length-1] + ")";
            }
            else {
                newData += split[split.length-1] + ")";
            }
            return new MyTreeNode(newData);
        }
        else if (root.data.equals("~")) {
            root.right = standardize(root.right);
        }
        else {
            root.left = standardize(root.left);
            root.right = standardize(root.right);
        }

        return root;
    }

    private boolean isVariable(String str) {
        return str.charAt(0) <= 'z' && str.charAt(0) >= 'a';
    }

    private String getPredicateName(String atom) {
        return atom.charAt(0)=='~'
                ? atom.substring(1).split("\\(")[0]
                : atom.split("\\(")[0];
    }

    class Predicate {
        List<String> arguments = new LinkedList<>();
        LinkedList<Integer> positiveLiteral = new LinkedList<>();
        LinkedList<Integer> negativeLiteral = new LinkedList<>();
        LinkedList<Integer> positiveClause = new LinkedList<>();
        LinkedList<Integer> negativeClause = new LinkedList<>();

        Predicate(String atom, int sentIndex, boolean isLiteral) {
            String[] split = atom.split("[(,)]");
            boolean isNegative = (atom.charAt(0) == '~');
            for (int i = 1; i < split.length; i++) {
                arguments.add(split[i]);
            }
            insertIndex(sentIndex, isNegative, isLiteral);

        }

        Predicate updatePred(String newAtom, int sentIndex, boolean isLiteral) {
            String[] split = newAtom.split("[(,)]");
            boolean isNegative = (newAtom.charAt(0) == '~');
            for (int i = 1; i < split.length; i++) {
                if (split[i].charAt(0) <= 'z' && split[i].charAt(0) >= 'a') {
                    arguments.set(i-1, split[i]);
                }
            }
            insertIndex(sentIndex, isNegative, isLiteral);
            return this;
        }

        private void insertIndex(int sentIndex, boolean isNegative, boolean isLiteral) {
            if (isNegative) {
                if (isLiteral) {
                    negativeLiteral.add(sentIndex);
                }
                else {
                    negativeClause.add(sentIndex);
                }
            }
            else {
                if (isLiteral) {
                    positiveLiteral.add(sentIndex);
                }
                else {
                    positiveClause.add(sentIndex);
                }
            }

        }
    }

}


