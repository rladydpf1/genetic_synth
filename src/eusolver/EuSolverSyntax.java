package eusolver;
import java.util.ArrayList;

import util.Syntax;

public class EuSolverSyntax extends Syntax {

    public EuSolverSyntax(String funName, ArrayList<String> paNames, String returnType) {
        super(funName, paNames, returnType);
    }

    @Override
    public void setSyntacticBasic() {
        String syntax = getSyntax();
        syntax += "((Start Int (\n";
        syntax += "\t -1 0 1 2 3 5 7\n\t";
        for (int i = 0; i < getPaNames().size(); i++) {
            syntax += getPaNames().get(i) + " ";
        } syntax += "\n";
        syntax += "\t(+ Start Start)\n";
        syntax += "\t(- Start Start)\n";
        syntax += "\t(* Start Start)\n";
        // syntax += "\t(/ Start Start)\n";
        syntax += "\t(ite StartBool Start Start)\n";
        syntax += "))\n";
        syntax += "(StartBool Bool (\n";
        // syntax += "\t(or StartBool StartBool)\n";
        // syntax += "\t(and StartBool StartBool)\n";
        // syntax += "\t(not StartBool)\n";
        syntax += "\t(= Start Start)\n";
        syntax += "\t(< Start Start)\n";
        syntax += "\t(>= Start Start)\n";
        syntax += ")) ))\n";
        // syntax += ")) \n";
        setSyntax(syntax);
    }
    
}

        // String syntax = getSyntax();
        // syntax += "((Start Int (\n";
        // syntax += "\t -1 0 1 2\n\t";
        // for (int i = 0; i < getPaNames().size(); i++) {
        //     if (i > 12) break;
        //     syntax += getPaNames().get(i) + " ";
        // } syntax += "\n";
        // // syntax += "\t(+ Start Start)\n";
        // syntax += "\t(- Start Start)\n";
        // syntax += "\t(* Start Start)\n";
        // // syntax += "\t(/ Start Start)\n";
        // syntax += "\t(ite StartBool Start Start)\n";
        // syntax += "))\n";
        // syntax += "(StartBool Bool (\n";
        // // syntax += "\t(or StartBool StartBool)\n";
        // // syntax += "\t(and StartBool StartBool)\n";
        // // syntax += "\t(not StartBool)\n";
        // syntax += "\t(= Start Start)\n";
        // // syntax += "\t(< Start Start)\n";
        // syntax += "\t(>= Start Start)\n";
        // syntax += ")) ))\n";
        // setSyntax(syntax);
