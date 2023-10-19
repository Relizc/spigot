package net.itsrelizc.mcserver.LanguageManager;

import net.itsrelizc.mcserver.IDEManager.ControlIDEs;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Builtins {
    public static List<String> funcNames = Arrays.asList(
            "execute","getPlayerByName","global","createArray","modifyArray","getArray","return"

    );
    public static Object executeBuiltinFunction(List<Object> args,String funcName,LocalStorage localStorage){
        switch(funcName){
            case "execute":
                if(args.size() == 1){
                    String cmd = (String) args.get(0);
                    System.out.println("DISPATCHING COMMAND"+cmd);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),cmd);
                    return true;
                }
                return false;
            case "getPlayerByName":
                if(args.size() == 1){
                    String name = (String) args.get(0);
                    return  Bukkit.getPlayer(name);

                }
                return false;
            case "global":
                if(args.size() == 1){
                    String name = (String) args.get(0);
                    System.out.println("got to here"+name);

                    if(!ScriptExecutor.globals.containsKey(name)){
                        if(localStorage.variables.containsKey(name)){
                            ScriptExecutor.globals.put(name,localStorage.variables.get(name));
                            localStorage.variables.remove(name);

                        }else ScriptExecutor.globals.put(name,null);

                    }

                }
                return true;
            case "createArray":
                if(!args.isEmpty()){
                    Integer length = (Integer) args.get(0);
                    Object[] ret = new Object[length];
                    List<Object> elements = args.subList(1,args.size());
                    for(int i=0;i<elements.size();i++){
                        ret[i] = elements.get(i);
                    }
                    return ret;
                }
                return false;
            case "modifyArray":
                if(args.size() == 3){
                    Object[] arr = (Object[]) args.get(0);
                    Integer index = (Integer) args.get(1);
                    Object value = args.get(2);
                    arr[index ]= value;
                    return arr;
                }
                return false;
            case "getArray":
                if(args.size() == 2){
                    Object[] arr = (Object[]) args.get(0);
                    Integer index = (Integer) args.get(1);
                    return arr[index];
                }
            case "return":
                if(args.size() == 1){
                    return new Object[]{"ret",args.get(0)};
                }
        }
        return null;
    }

}
