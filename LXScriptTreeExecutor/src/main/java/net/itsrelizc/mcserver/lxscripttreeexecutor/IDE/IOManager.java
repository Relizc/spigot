package net.itsrelizc.mcserver.lxscripttreeexecutor.IDE;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IOManager {
    public static File openFile(String name){
        File cur = new File(System.getProperty("user.dir"));
        File file = new File(cur.getParentFile().getParentFile().toString()+"\\database\\"+name);
        return file
    }
    public static String loadStringFromFile(String name){
        File f = openFile(name);
        Path path = Paths.get(f.getAbsolutePath());
        try {
            Files.readAllLines(path)
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
