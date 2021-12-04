package util;
import java.util.ArrayList;

public abstract class Syntax {
    private String syntax;
    private String returnType;
    private ArrayList<String> paNames;

    public Syntax(String funName, ArrayList<String> paNames, String returnType) {
        this.paNames = paNames;
        this.returnType = returnType;
        syntax = "(set-logic LIA)\n\n";
        syntax += String.format("(synth-fun %s (", funName);
        for (int i = 0; i < paNames.size(); i++) {
            syntax += " (" + paNames.get(i) + " Int)";
        }
        syntax += ") " + returnType + "\n";
    }

    abstract public void setSyntacticBasic();

    public String getSyntax() {
        return syntax;
    }
    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }
    public ArrayList<String> getPaNames() {
        return paNames;
    }
    public String getReturnType() {
        return returnType;
    }
    
}
