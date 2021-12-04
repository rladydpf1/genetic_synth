package util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class FunctionDriver {
    private String code;
    private File file;

    public FunctionDriver(String fileName) {
        this.code = "#include <stdio.h>\n#include <stdlib.h>\n#include <time.h>";
        this.file = new File("./out/", fileName);
    }

    public void setGlobalVar(String type, String name) {
        this.code += '\n' + type + " " + name + ";";
    }

    public void addCode(String code) {
        this.code += '\n' + code + '\n';
    }

    public void writeCode() {
        try {
            file.createNewFile();
            file.setReadable(true);
            file.setWritable(true);
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] bytes = code.getBytes();
            outputStream.write(bytes);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String run() {
        String res = "";
        try {
            String target = file.getCanonicalPath().substring(0, file.getCanonicalPath().length()-2);
            Process proc = Runtime.getRuntime().exec("gcc " + file.getCanonicalPath() + " -o " + target);
            BufferedReader err = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String erline = null;
            if ((erline = err.readLine()) != null) {
                System.out.println(erline);
                System.exit(0);
            }
            proc.waitFor();
            File file = new File(target);
            file.setExecutable(true);
            proc = Runtime.getRuntime().exec(target);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                res += line + '\n';
                
            }
            proc.waitFor();
             
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public String getEvluatorCode() {
        return code;
    }

}