package eusolver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class EuSolverSyntax {
    private String syntax;
    private String returnType;
    private ArrayList<String> paNames;

    private final List<String> operators_Int = Arrays.asList("+", "-", "*", "/", "ite");
    private final List<String> operators_Bool = Arrays.asList("<", "<=", ">", ">=", "or", "and", "=");

    public EuSolverSyntax(String funName, ArrayList<String> paNames, String returnType) {
        this.paNames = paNames;
        this.returnType = returnType;
        syntax = "(set-logic LIA)\n\n";
        syntax += String.format("(synth-fun %s (", funName);
        for (int i = 0; i < paNames.size(); i++) {
            syntax += " (" + paNames.get(i) + " Int)";
        }
        syntax += ") " + returnType + "\n";
    }

    public void setGrammars(HashSet<String> grammars) {
        HashSet<String> constants = new HashSet<>();
        HashSet<String> parameters = new HashSet<>();
        HashSet<String> operatorInt = new HashSet<>();
        HashSet<String> operatorBool = new HashSet<>();
        for (String grammar : grammars) {
            if (operators_Int.contains(grammar)) {
                operatorInt.add(grammar);
            }
            else if (operators_Bool.contains(grammar)) {
                operatorBool.add(grammar);
            }
            else {
                try {
                    Integer.parseInt(grammar);
                    constants.add(grammar);
                }
                catch (Exception e) {
                    parameters.add(grammar);
                }
            }
        }
        String syntax = getSyntax();
        syntax += "((Start Int (\n\t";
        for (String constant : constants) {
            syntax += constant + " ";
        } syntax += "\n";

        for (String parameter : parameters) {
            syntax += parameter + " ";
        } syntax += "\n";

        boolean ite_flag = false;
        for (String op_int : operatorInt) {
            if (op_int.equals("ite")) {
                syntax += "\t(ite StartBool Start Start)\n";
                ite_flag = true;
            }
            else {
                syntax += String.format("\t(%s Start Start)\n", op_int);
            }
        }
        syntax += "))\n";
        if (ite_flag) {
            syntax += "(StartBool Bool (\n";
            for (String op_bool : operatorBool) {
                if (op_bool.equals("or") || op_bool.equals("and")) {
                    syntax += String.format("\t(%s StartBool StartBool)\n", op_bool);
                }
                else {
                    syntax += String.format("\t(%s Start Start)\n", op_bool);
                }
            }
            syntax += ")) ))\n";
        }
        else {
            syntax += ")) \n";
        }

        setSyntax(syntax);
    }

    public void setRandomGrammars() {
        HashSet<String> grammars = new HashSet<>();
        Random random = new Random();
        for (String op :  operators_Int) {
            if (random.nextInt(2) == 0) {
                grammars.add(op);
            } 
        }
        for (String op :  operators_Bool) {
            if (random.nextInt(2) == 0) {
                grammars.add(op);
            } 
        }
        for (String pa :  paNames) {
            if (random.nextInt(2) == 0) {
                grammars.add(pa);
            } 
        }
        int const_num = random.nextInt(10);
        while(const_num > 0) {
            Integer constant = random.nextInt(100) - 1;
            grammars.add(constant.toString());
            const_num--;
        }
        setGrammars(grammars);
    }

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
