package net.itsrelizc.mcserver.lxscripttreeexecutorrealversion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileIOManager {
        public static File getFile(String fname){
            File f = new File(new File(System.getProperty("user.dir")).getParentFile().getParentFile().toString() + "\\database\\"+fname);
            if(f.exists()) return f;
            else return null;


        }
        public static String getStringFromFile(String fname) throws FileNotFoundException {
            File f = getFile(fname);
            if(f!=null){
                Scanner scan = new Scanner(f);
                StringBuilder ret = new StringBuilder();
                while(scan.hasNextLine()){
                    ret.append(scan.nextLine());
                    ret.append("\n");
                }
                return ret.toString();

            }else{
                return null;
            }
        }
    public static String getStringFromFile(File f) throws FileNotFoundException {

        if(f!=null){
            Scanner scan = new Scanner(f);
            StringBuilder ret = new StringBuilder();
            while(scan.hasNextLine()){
                ret.append(scan.nextLine());
                ret.append("\n");
            }
            return ret.toString();

        }else{
            return null;
        }
    }
        public static void saveStringToFile(String fname,String sav) {
            File f = getFile(fname);
            if(f!=null){

                try {
                    try (PrintWriter out = new PrintWriter(f)) {
                        out.println(sav);
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        }
}
