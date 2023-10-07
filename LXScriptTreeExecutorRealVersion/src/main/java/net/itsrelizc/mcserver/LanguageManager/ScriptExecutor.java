package net.itsrelizc.mcserver.LanguageManager;

import org.apache.commons.io.CopyUtils;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScriptExecutor {
    public static Map<String,Object> globals = new HashMap<>();
    public Map<String,ParsedClass> classes = new HashMap<>();


    public Map<String,ParsedFunction> funcs;
    public ScriptExecutor(Map<String,ParsedFunction> funcs,Map<String,ParsedClass> classes){
        this.funcs = funcs;
        this.classes = classes;
    }
    public void executeMain(){


        Node main = this.funcs.get("main").contents;
        for(ParsedFunction i:this.funcs.values()) globals.put(i.name,i);
        for(ParsedClass i:this.classes.values()) globals.put(i.name,i);

        for(Node i:main.getChildren()){
            this.execute(i,this.funcs.get("main").args.store);
        }
    }
    public Object execute(Node torun,LocalStorage storage){


        switch(torun.getName()){
            case "operator":
                if(torun.getValue() == "ASSIGN"){

                    if(globals.containsKey((String) torun.getNodebyName("loc").getValue())){
                        globals.put((String) torun.getNodebyName("loc").getValue(),this.execute(torun.getNodebyName("val").getChildren().get(0),storage));
                    }else{

                        storage.variables.put((String) torun.getNodebyName("loc").getValue(),this.execute(torun.getNodebyName("val").getChildren().get(0),storage));
                    }
                    return true;
                }else if(torun.getValue() == "print"){
                    StringBuilder result = new StringBuilder();
                    for(Node i:torun.getChildren()){

                        result.append(this.execute(i,storage));

                    }




                    Bukkit.broadcastMessage("[SERVER]"+result.toString());
                    return true;

                }else if(torun.getValue() == "EXEC"){

                    if(Builtins.funcNames.contains((String) torun.getNodebyName("funcName").getValue())) {
                        String funcName = (String) torun.getNodebyName("funcName").getValue();
                        List<Object> args = new ArrayList<>();
                        for(Node i:torun.getNodeByValue("ARGS").getChildren()){
                            if(!i.getChildren().isEmpty())  args.add(this.execute(i.getChildren().get(0),storage));
                        }


                        return Builtins.executeBuiltinFunction(args,funcName,storage);



                    }else{
                        if(globals.get((String) torun.getNodebyName("funcName").getValue()) instanceof  ParsedFunction) {

                            ParsedFunction wFunc = (ParsedFunction) globals.get((String) torun.getNodebyName("funcName").getValue());


                            List<String> args = wFunc.args.args;


                            Object[] acceptedArgs = new Object[args.size()];


                            for (Node i : torun.getNodeByValue("ARGS").getChildren()) {
                                Integer argId = (Integer) i.getValue();


                                if (!i.getChildren().isEmpty()) {
                                    Object content = this.execute(i.getChildren().get(0), storage);


                                    wFunc.args.store.variables.put(args.get(argId), content);
                                }


                            }

                            for (Node i : wFunc.contents.getChildren()) {
                                this.execute(i, wFunc.args.store);
                            }

                            return true;
                        }else if(globals.get((String) torun.getNodebyName("funcName").getValue()) instanceof  ParsedClass){
                            ParsedClass wClass = (ParsedClass) globals.get((String) torun.getNodebyName("funcName").getValue());
                            ParsedFunction init=wClass.parsedFunctionMap.get("init");
                            for (Node i : torun.getNodeByValue("ARGS").getChildren()) {
                                Integer argId = (Integer) i.getValue();


                                if (!i.getChildren().isEmpty()) {
                                    Object content = this.execute(i.getChildren().get(0), storage);


                                    init.args.store.variables.put(init.args.args.get(argId), content);
                                }


                            }
                            for(Node i:wClass.parsedFunctionMap.get("init").contents.getChildren()){
                                this.execute(i,wClass.parsedFunctionMap.get("init").args.store,wClass.storage);
                            }


                            return wClass;
                        }
                    }
                }
                else if (torun.getValue() == "if"){
                    Node cond = torun.getNodeByValue("conditions").getChildren().get(0);

                    boolean condition = (boolean) this.execute(cond,storage);
                    List<Node> contentsNode=torun.getNodeByValue("contents").getChildren();
                    if(condition){
                        for(Node i:contentsNode){
                            this.execute(i,storage);
                        }
                        return true;
                    }
                    return false;


                }else if(torun.getValue() == "GETM"){



                    ParsedClass theClass = (ParsedClass) this.execute(torun.getNodeByValue("Class").getChildren().get(0),storage);


                    Node theMethod = torun.getNodeByValue("Method").getChildren().get(0);

                    String fName = (String) theMethod.getNodebyName("funcName").getValue();



                    for (Node i : theMethod.getNodeByValue("ARGS").getChildren()) {
                        Integer argId = (Integer) i.getValue();


                        if (!i.getChildren().isEmpty()) {
                            Object content = this.execute(i.getChildren().get(0), storage);


                            theClass.parsedFunctionMap.get(fName).args.store.variables.put(theClass.parsedFunctionMap.get(fName).args.args.get(argId), content);
                        }


                    }
                    for(Node i:theClass.parsedFunctionMap.get(fName).contents.getChildren()){
                        this.execute(i,theClass.parsedFunctionMap.get(fName).args.store,theClass.storage);
                    }
                    return true;


                }
                else{
                    Object side1 = this.execute(torun.getChildren().get(0).getChildren().get(0),storage);
                    Object side2 = this.execute(torun.getChildren().get(1).getChildren().get(0),storage);

                    switch((String) torun.getValue()){

                        case "PLUS":


                            assert side2 instanceof Integer;
                            assert side1 instanceof Integer;
                            return (
                                    (Integer)side1+(Integer)side2
                                    );
                        case "MINUS":
                            assert side2 instanceof Integer;
                            assert side1 instanceof Integer;
                            return (
                                    (Integer) side1-(Integer)side2
                            );

                        case "MULTIPLY":
                            assert side2 instanceof Integer;
                            assert side1 instanceof Integer;
                            return (
                                    (Integer)side1*(Integer)side2
                            );
                        case "DIVIDE":
                            assert side2 instanceof Integer;
                            assert side1 instanceof Integer;
                            return (
                                    (Integer)side1/(Integer)side2
                            );
                        case "POWER":
                            assert side2 instanceof Integer;
                            assert side1 instanceof Integer;
                            return (
                                    Math.round((float) Math.pow((Integer) side1,(Integer) side2))

                            );
                        case "BITMOV_LEFT":
                            assert side2 instanceof Integer;
                            assert side1 instanceof Integer;
                            return (
                                    (Integer)side1<<(Integer)side2
                            );
                        case "BITMOV_RIGHT":
                            assert side2 instanceof Integer;
                            assert side1 instanceof Integer;
                            return (
                                    (Integer)side1>>(Integer)side2
                            );
                        case "OR":
//                            assert side2 instanceof Integer;
//                            assert side1 instanceof Integer;
                            return (
                                    (Integer) side1|(Integer) side2
                            );
                        case "AND":
                            assert side2 instanceof Integer;
                            assert side1 instanceof Integer;
                            return (
                                    (Integer)side1&(Integer)side2
                            );
                        case "XOR":
                            assert side2 instanceof Integer;
                            assert side1 instanceof Integer;
                            return (
                                    (Integer)side1^(Integer)side2
                            );
                        case "EQUALS_CHECK":

                            return (
                                    side1==side2
                            );
                        case "UNEQUAL_CHECK":
                            return side1!=side2;
                        case "LESS_EQUALS":
                            return (Integer) side1 <= (Integer) side2;
                        case "LESS":
                            return (Integer) side1 < (Integer) side2;
                        case "GREATER_EQUALS":
                            return (Integer) side1 >= (Integer) side2;
                        case "GREATER":
                            return (Integer) side1 > (Integer) side2;
                        case "INSTANCEOF" :
                            if(side1 instanceof ParsedClass && side2 instanceof ParsedClass){
                                return ((ParsedClass) side1).name.equals(((ParsedClass) side2).name);
                            }
                            Class<?> class2 = side2.getClass();
                            Class<?> class1 = side1.getClass();

                            return class1==class2;

                    }
                }
            case "integer":
                System.out.println("INTLOL"+torun.getValue());
                return (Integer) torun.getValue();
            case "string":


                return (String) torun.getValue();
            case "null":
                if(torun.getValue() == "Parenthesis"){
                    if(!torun.getChildren().isEmpty())  return this.execute(torun.getChildren().get(0),storage);
                }
                break;
            case "UNDEFINED":
                String name = (String) torun.getValue();
                if(!Builtins.funcNames.contains(name)){
                    if(globals.containsKey(name)){
                        if(globals.get(name) instanceof ParsedClass){
                            ParsedClass d = (ParsedClass) globals.get(name);
                            ParsedClass newOne = new ParsedClass(d.name,d.parsedFunctionMap);
                            return newOne;
                        }
                        return globals.get(name);
                    }else if(storage.variables.containsKey(name)){
                        return storage.variables.get(name);
                    }else{
                        return false;
                    }
                }
                return false;
        }
        return null;
    }













    public Object execute(Node torun,LocalStorage storage,LocalStorage objectStorage){


        switch(torun.getName()){
            case "operator":
                if(torun.getValue() == "ASSIGN"){
                    if(((String) torun.getNodebyName("loc").getValue()).contains("this")){

                        objectStorage.variables.put((String) torun.getNodebyName("loc").getValue(),this.execute(torun.getNodebyName("val").getChildren().get(0),storage,objectStorage));
                        System.out.println(objectStorage.variables.toString());

                    }
                    else if(globals.containsKey((String) torun.getNodebyName("loc").getValue())){
                        globals.put((String) torun.getNodebyName("loc").getValue(),this.execute(torun.getNodebyName("val").getChildren().get(0),storage));
                    }else{

                        storage.variables.put((String) torun.getNodebyName("loc").getValue(),this.execute(torun.getNodebyName("val").getChildren().get(0),storage));
                    }
                    return true;
                }else if(torun.getValue() == "print"){
                    StringBuilder result = new StringBuilder();
                    for(Node i:torun.getChildren()){


                        result.append(this.execute(i,storage,objectStorage));

                    }




                    Bukkit.broadcastMessage("[SERVER]"+result.toString());
                    return true;

                }else if(torun.getValue() == "EXEC"){

                    if(Builtins.funcNames.contains((String) torun.getNodebyName("funcName").getValue())) {
                        String funcName = (String) torun.getNodebyName("funcName").getValue();
                        List<Object> args = new ArrayList<>();
                        for(Node i:torun.getNodeByValue("ARGS").getChildren()){
                            if(!i.getChildren().isEmpty())  args.add(this.execute(i.getChildren().get(0),storage,objectStorage));
                        }


                        return Builtins.executeBuiltinFunction(args,funcName,storage);



                    }else{
                        if(globals.get((String) torun.getNodebyName("funcName").getValue()) instanceof  ParsedFunction) {

                            ParsedFunction wFunc = (ParsedFunction) globals.get((String) torun.getNodebyName("funcName").getValue());


                            List<String> args = wFunc.args.args;


                            Object[] acceptedArgs = new Object[args.size()];


                            for (Node i : torun.getNodeByValue("ARGS").getChildren()) {
                                Integer argId = (Integer) i.getValue();


                                if (!i.getChildren().isEmpty()) {
                                    Object content = this.execute(i.getChildren().get(0), storage,objectStorage);


                                    wFunc.args.store.variables.put(args.get(argId), content);
                                }


                            }

                            for (Node i : wFunc.contents.getChildren()) {
                                this.execute(i, wFunc.args.store);
                            }

                            return true;
                        }else if(globals.get((String) torun.getNodebyName("funcName").getValue()) instanceof  ParsedClass){
                            ParsedClass wClass = (ParsedClass) globals.get((String) torun.getNodebyName("funcName").getValue());
                            for(Node i:wClass.parsedFunctionMap.get("init").contents.getChildren()){
                                this.execute(i,wClass.parsedFunctionMap.get("init").args.store,wClass.storage);
                            }
                            return wClass;
                        }
                    }
                }
                else if (torun.getValue() == "if"){
                    Node cond = torun.getNodeByValue("conditions").getChildren().get(0);

                    boolean condition = (boolean) this.execute(cond,storage,objectStorage);
                    List<Node> contentsNode=torun.getNodeByValue("contents").getChildren();
                    if(condition){
                        for(Node i:contentsNode){
                            this.execute(i,storage,objectStorage);
                        }
                        return true;
                    }
                    return false;


                }else if(torun.getValue() == "GETM"){

                    ParsedClass theClass = (ParsedClass) this.execute(torun.getNodeByValue("Class"),storage,objectStorage);
                    Node theMethod = torun.getNodeByValue("Method");
                    String fName = String.valueOf(theMethod.getNodebyName("funcName").getValue());

                    for (Node i : theMethod.getNodeByValue("ARGS").getChildren()) {
                        Integer argId = (Integer) i.getValue();


                        if (!i.getChildren().isEmpty()) {
                            Object content = this.execute(i.getChildren().get(0), storage,objectStorage);


                            theClass.parsedFunctionMap.get(fName).args.store.variables.put(theClass.parsedFunctionMap.get(fName).args.args.get(argId), content);
                        }


                    }
                    for(Node i:theClass.parsedFunctionMap.get(fName).contents.getChildren()){
                        this.execute(i,theClass.parsedFunctionMap.get(fName).args.store,theClass.storage);
                    }
                    return true;


                }
                else{
                    Object side1 = this.execute(torun.getChildren().get(0).getChildren().get(0),storage,objectStorage);
                    Object side2 = this.execute(torun.getChildren().get(1).getChildren().get(0),storage,objectStorage);

                    switch((String) torun.getValue()){

                        case "PLUS":


                            assert side2 instanceof Integer;
                            assert side1 instanceof Integer;
                            return (
                                    (Integer)side1+(Integer)side2
                            );
                        case "MINUS":
                            assert side2 instanceof Integer;
                            assert side1 instanceof Integer;
                            return (
                                    (Integer) side1-(Integer)side2
                            );

                        case "MULTIPLY":
                            assert side2 instanceof Integer;
                            assert side1 instanceof Integer;
                            return (
                                    (Integer)side1*(Integer)side2
                            );
                        case "DIVIDE":
                            assert side2 instanceof Integer;
                            assert side1 instanceof Integer;
                            return (
                                    (Integer)side1/(Integer)side2
                            );
                        case "POWER":
                            assert side2 instanceof Integer;
                            assert side1 instanceof Integer;
                            return (
                                    Math.round((float) Math.pow((Integer) side1,(Integer) side2))

                            );
                        case "BITMOV_LEFT":
                            assert side2 instanceof Integer;
                            assert side1 instanceof Integer;
                            return (
                                    (Integer)side1<<(Integer)side2
                            );
                        case "BITMOV_RIGHT":
                            assert side2 instanceof Integer;
                            assert side1 instanceof Integer;
                            return (
                                    (Integer)side1>>(Integer)side2
                            );
                        case "OR":
//                            assert side2 instanceof Integer;
//                            assert side1 instanceof Integer;
                            return (
                                    (Integer) side1|(Integer) side2
                            );
                        case "AND":
                            assert side2 instanceof Integer;
                            assert side1 instanceof Integer;
                            return (
                                    (Integer)side1&(Integer)side2
                            );
                        case "XOR":
                            assert side2 instanceof Integer;
                            assert side1 instanceof Integer;
                            return (
                                    (Integer)side1^(Integer)side2
                            );
                        case "EQUALS_CHECK":

                            return (
                                    side1==side2
                            );
                        case "UNEQUAL_CHECK":
                            return side1!=side2;
                        case "LESS_EQUALS":
                            return (Integer) side1 <= (Integer) side2;
                        case "LESS":
                            return (Integer) side1 < (Integer) side2;
                        case "GREATER_EQUALS":
                            return (Integer) side1 >= (Integer) side2;
                        case "GREATER":
                            return (Integer) side1 > (Integer) side2;
                        case "INSTANCEOF" :
                            if(side1 instanceof ParsedClass && side2 instanceof ParsedClass){
                                return ((ParsedClass) side1).name.equals(((ParsedClass) side2).name);
                            }
                            Class<?> class2 = side2.getClass();
                            Class<?> class1 = side1.getClass();

                            return class1==class2;

                    }
                }
            case "integer":

                return (Integer) torun.getValue();
            case "string":


                return (String) torun.getValue();
            case "null":
                if(torun.getValue() == "Parenthesis"){
                    if(!torun.getChildren().isEmpty())  return this.execute(torun.getChildren().get(0),storage,objectStorage);
                }
                break;
            case "UNDEFINED":
                String name = (String) torun.getValue();
                if(!Builtins.funcNames.contains(name)){

                    if(objectStorage.variables.containsKey(name)) {

                        return objectStorage.variables.get(name);
                    }
                    else if(globals.containsKey(name)){
                        if(globals.get(name) instanceof ParsedClass){
                            ParsedClass d = (ParsedClass) globals.get(name);
                            ParsedClass newOne = new ParsedClass(d.name,d.parsedFunctionMap);
                            return newOne;
                        }
                        return globals.get(name);
                    }else if(storage.variables.containsKey(name)){
                        return storage.variables.get(name);
                    }else{
                        return false;
                    }
                }
                return false;
        }
        return null;
    }
}
