package net.itsrelizc.mcserver.LanguageManager;

import net.itsrelizc.mcserver.lxscripttreeexecutorrealversion.FileIOManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class LanguageManager {

//    private String content;
//    public LanguageManager(String contents){
//        this.content = contents;
//    }
////    public Map<String, String> exec(Script script) throws FileNotFoundException {
////        File file = FileIOManager.getFile("lx\\main.py");
////        String scriptPath = script.getF().getAbsolutePath();
////        Map<String,String> functions = new HashMap<>();
////        if(file!=null){
////            String absPath = file.getAbsolutePath();
////            Runtime.getRuntime().exec("py "+absPath+" "+scriptPath);
////            File results = FileIOManager.getFile("lx\\parsed");
////            File[] ls = results.listFiles();
////            if(ls != null){
////                for(File i:ls){
////                    String name = i.getName();
////                    String contents = FileIOManager.getStringFromFile(i);
////                    functions.put(name,contents);
////                }
////            }
////        }
////        return functions;
////    }
}
