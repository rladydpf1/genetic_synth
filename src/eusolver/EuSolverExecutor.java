package eusolver;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import logdata.LogData;
import synth.SynthCode;

public class EuSolverExecutor {
    private final static String eusolver = "./eusolver/eusolver";
    private File specFile;
    private HashSet<LogData> data;
    private HashSet<String> grammar;

    public EuSolverExecutor(String filePath, EuSolverSyntax syntax, HashSet<LogData> data) {
        this.data = data;
        this.grammar = syntax.getUsedGrammars();
        specFile = new File("./eusolver/" + filePath);
        String spec = syntax.getSyntax() + "\n";
        for (LogData log : data) {
            spec += log.getConstraintFormat() + "\n";
        }
        spec += "(check-synth)\n";
        try {
            FileWriter writer = new FileWriter(specFile);
            writer.append(spec);
            writer.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public SynthCode synthesize(int timeout, String name) {
        String runner = eusolver + "_" + name + ".sh";
        String program = "", stat = "";
        try {
            File file = new File(runner);
            file.createNewFile();
            file.setReadable(true);
            file.setWritable(true);
            file.setExecutable(true);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.append("#!/bin/bash\n");
            writer.append("PYTHONPATH=./eusolver/thirdparty/z3/build/python:./eusolver/thirdparty/libeusolver/build:$PYTHONPATH " + 
               "python3 ./eusolver/src/benchmarks.py ");
            writer.append(specFile.getCanonicalPath() + " > ./out/eusolver.synth_" + name + " 2>&1\n");
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            Process process = Runtime.getRuntime().exec(runner);
            if(!process.waitFor(timeout, TimeUnit.SECONDS)) {
                process.destroyForcibly();
                return null;
            };
            process.destroy();
            File file = new File("./out/eusolver.synth_" + name);
            FileReader filereader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(filereader);
            program = bufReader.readLine();
            String line = null;
            while((line = bufReader.readLine()) != null){
                stat += line + "\n";
            }
            bufReader.close();
        } catch (Exception e) {return null;}
        return new SynthCode(program, stat);
    }

    public File getSpecFile() {
        return specFile;
    }

    public HashSet<LogData> getData() {
        return data;
    }

    public HashSet<String> getGrammar() {
        return grammar;
    }
    
}
