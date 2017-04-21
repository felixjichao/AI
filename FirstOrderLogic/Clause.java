import java.util.LinkedList;

public class Clause {
    private LinkedList<Atom> predicate = new LinkedList<>();
    private String strClause;

    public Clause(String origin) {
        String[] split = origin.split("\\|");
        for (int i = 0; i < split.length; i++) {
            predicate.add(new Atom(split[i].trim()));
        }
        strClause = origin;
    }

    public LinkedList<Atom> getPredicate() {
        return predicate;
    }

    @Override
    public String toString() {
        return strClause;
    }
}