import java.util.LinkedList;

public class Atom {
    private String strPredicate;
    private String predicate;
    private LinkedList<String> arguments = new LinkedList<>();
    private boolean negation;

    public Atom(String origin) {
        String[] splitRes = origin.split("\\(|\\)");
        predicate = splitRes[0];
        if (predicate.contains("~")) {
            negation = true;
            predicate = predicate.substring(1);
        }
        String[] remaining = splitRes[1].split(",");
        for (int i = 0; i < remaining.length; i++) {
            arguments.add(remaining[i].trim());

        }
        strPredicate = origin;
    }

    public String getPredicte() {
        return predicate;
    }

    public LinkedList<String> getArguments() {
        return arguments;
    }

    public boolean getNegation() {
        return negation;
    }

    @Override
    public String toString() {
        return strPredicate;
    }
}