package net.itsrelizc.mcserver.lxscripttreeexecutorrealversion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileIOManager {
        public static File getFile(String fname){
            return new File(new File(System.getProperty("user.dir")).getParentFile().getParentFile().toString() + "\\database\\"+fname);


        }
        public static String getStringFromFile(String fname) {
            File f = getFile(fname);


            if(f.exists()){
                Scanner scan = null;
                try {
                    scan = new Scanner(f);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                StringBuilder ret = new StringBuilder();
                while(scan.hasNextLine()){

                    ret.append(scan.nextLine());
                    ret.append("\n");
                }
                return ret.toString();



            }else{
                return "";
            }
        }
    public static String getStringFromFile(File f) throws FileNotFoundException {

        if(f.exists()){
            Scanner scan = new Scanner(f);
            StringBuilder ret = new StringBuilder();
            while(scan.hasNextLine()){
                ret.append(scan.nextLine());
                ret.append("\n");
            }
            return ret.toString();

        }else{
            return "";
        }
    }
        public static void saveStringToFile(String fname,String sav) {
            File f = getFile(fname);
            if(f.exists()){
                System.out.println("EXISTS");
                try {
                    try (PrintWriter out = new PrintWriter(f)) {
                        out.println(sav);
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }else{
                try {
                    System.out.println(f.createNewFile());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try (PrintWriter out = new PrintWriter(f)) {
                    out.println(sav);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        }
}
