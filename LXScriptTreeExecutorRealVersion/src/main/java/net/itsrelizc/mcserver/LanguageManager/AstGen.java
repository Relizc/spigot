package net.itsrelizc.mcserver.LanguageManager;

import jdk.nashorn.internal.objects.annotations.Function;
import org.apache.commons.io.CopyUtils;

import java.sql.Array;
import java.util.*;

public class AstGen {
    private List<LexerObject> lexxed;
    private Node tree;

    public AstGen(List<LexerObject> lexxed){
        this.lexxed = lexxed;
        this.tree = new StartNode();

    }
    public Integer[] getCurlyPairs(Integer start,Integer end,List<LexerObject> lst){
        Integer i = start;
        Integer j = end;
        Integer[] parentPairs = {0,0};
        while(i<j){
            if(lst.get(i).getValue() == LexerInnerObjectType.STARTCURL && !(lst.get(j).getValue() == LexerInnerObjectType.ENDCURL)){
                j--;
            }else if(lst.get(j).getValue() == LexerInnerObjectType.ENDCURL && !(lst.get(i).getValue()==LexerInnerObjectType.STARTCURL)){
                i++;
            }
            else if(lst.get(j).getValue()==LexerInnerObjectType.ENDCURL && lst.get(i).getValue() == LexerInnerObjectType.STARTCURL){
                parentPairs[0] = i;
                parentPairs[1] = j;
                break;
            }
            else{
                j--;
                i++;
            }
        }
        return parentPairs;

    }
    public Integer[] getCurlyPairs(Integer start,Integer end){
        List<LexerObject> lst = this.lexxed;
        Integer i = start;
        Integer j = end;
        Integer[] parentPairs = {0,0};
        while(i<j){
            if(lst.get(i).getValue() == LexerInnerObjectType.STARTCURL && !(lst.get(j).getValue() == LexerInnerObjectType.ENDCURL)){
                j--;
            }else if(lst.get(j).getValue() == LexerInnerObjectType.ENDCURL && !(lst.get(i).getValue()==LexerInnerObjectType.STARTCURL)){
                i++;
            }
            else if(lst.get(j).getValue()==LexerInnerObjectType.ENDCURL && lst.get(i).getValue() == LexerInnerObjectType.STARTCURL){
                parentPairs[0] = i;
                parentPairs[1] = j;
                break;
            }
            else{
                j--;
                i++;
            }
        }
        return parentPairs;

    }
    public Integer[] getCurlyPairs(Integer start,List<LexerObject> lst){
        Integer end = 0;
        Integer parentLevles = 0;
        boolean sawParent = false;
        for(int i=start;i<lst.size();i++){
            if(lst.get(i).getValue() == LexerInnerObjectType.STARTCURL){
                sawParent = true;
                parentLevles+=1;
            }
            else if(lst.get(i).getValue() == LexerInnerObjectType.ENDCURL){
                parentLevles -=1;
            }
            if(sawParent && parentLevles == 0){
                end = i;
                break;
            }

        }
        Integer i = start;
        Integer j = end;
        Integer[] parentPairs = {0,0};
        while(i<j){
            if(lst.get(i).getValue() == LexerInnerObjectType.STARTCURL && !(lst.get(j).getValue() == LexerInnerObjectType.ENDCURL)){
                j--;
            }else if(lst.get(j).getValue() == LexerInnerObjectType.ENDCURL && !(lst.get(i).getValue()==LexerInnerObjectType.STARTCURL)){
                i++;
            }
            else if(lst.get(j).getValue()==LexerInnerObjectType.ENDCURL && lst.get(i).getValue() == LexerInnerObjectType.STARTCURL){
                parentPairs[0] = i;
                parentPairs[1] = j;
                break;
            }
            else{
                j--;
                i++;
            }
        }
        return parentPairs;

    }
    public Integer[] getCurlyPairs(Integer start){
        List<LexerObject> lst = this.lexxed;

        Integer end = 0;
        Integer parentLevles = 0;
        boolean sawParent = false;
        for(int i=start;i<lst.size();i++){
            if(lst.get(i).getValue() == LexerInnerObjectType.STARTCURL){
                sawParent = true;
                parentLevles+=1;
            }
            else if(lst.get(i).getValue() == LexerInnerObjectType.ENDCURL){
                parentLevles -=1;
            }
            if(sawParent && parentLevles == 0){
                end = i;
                break;
            }

        }
        Integer i = start;
        Integer j = end;
        Integer[] parentPairs = {0,0};
        while(i<j){
            if(lst.get(i).getValue() == LexerInnerObjectType.STARTCURL && !(lst.get(j).getValue() == LexerInnerObjectType.ENDCURL)){
                j--;
            }else if(lst.get(j).getValue() == LexerInnerObjectType.ENDCURL && !(lst.get(i).getValue()==LexerInnerObjectType.STARTCURL)){
                i++;
            }
            else if(lst.get(j).getValue()==LexerInnerObjectType.ENDCURL && lst.get(i).getValue() == LexerInnerObjectType.STARTCURL){
                parentPairs[0] = i;
                parentPairs[1] = j;
                break;
            }
            else{
                j--;
                i++;
            }
        }
        return parentPairs;

    }












    public Integer[] getParentPairs(Integer start,Integer end,List<LexerObject> lst){
        Integer i = start;
        Integer j = end;
        Integer[] parentPairs = {0,0};
        while(i<j){
            if(lst.get(i).getValue() == LexerInnerObjectType.STARTPARENT && !(lst.get(j).getValue() == LexerInnerObjectType.ENDPARENT)){
                j--;
            }else if(lst.get(j).getValue() == LexerInnerObjectType.ENDPARENT && !(lst.get(i).getValue()==LexerInnerObjectType.STARTPARENT)){
                i++;
            }
            else if(lst.get(j).getValue()==LexerInnerObjectType.ENDPARENT && lst.get(i).getValue() == LexerInnerObjectType.STARTPARENT){
                parentPairs[0] = i;
                parentPairs[1] = j;
                break;
            }
            else{
                j--;
                i++;
            }
        }
        return parentPairs;

    }
    public Integer[] getParentPairs(Integer start,Integer end){
        List<LexerObject> lst = this.lexxed;
        Integer i = start;
        Integer j = end;
        Integer[] parentPairs = {0,0};
        while(i<j){
            if(lst.get(i).getValue() == LexerInnerObjectType.STARTPARENT && !(lst.get(j).getValue() == LexerInnerObjectType.ENDPARENT)){
                j--;
            }else if(lst.get(j).getValue() == LexerInnerObjectType.ENDPARENT && !(lst.get(i).getValue()==LexerInnerObjectType.STARTPARENT)){
                i++;
            }
            else if(lst.get(j).getValue()==LexerInnerObjectType.ENDPARENT && lst.get(i).getValue() == LexerInnerObjectType.STARTPARENT){
                parentPairs[0] = i;
                parentPairs[1] = j;
                break;
            }
            else{
                j--;
                i++;
            }
        }
        return parentPairs;

    }
    public Integer[] getParentPairs(Integer start,List<LexerObject> lst){
        Integer end = 0;
        Integer parentLevles = 0;
        boolean sawParent = false;
        for(int i=start;i<lst.size();i++){
            if(lst.get(i).getValue() == LexerInnerObjectType.STARTPARENT){
                sawParent = true;
                parentLevles+=1;
            }
            else if(lst.get(i).getValue() == LexerInnerObjectType.ENDPARENT){
                parentLevles -=1;
            }
            if(sawParent && parentLevles == 0){
                end = i;
                break;
            }

        }
        Integer i = start;
        Integer j = end;
        Integer[] parentPairs = {0,0};
        while(i<j){
            if(lst.get(i).getValue() == LexerInnerObjectType.STARTPARENT && !(lst.get(j).getValue() == LexerInnerObjectType.ENDPARENT)){
                j--;
            }else if(lst.get(j).getValue() == LexerInnerObjectType.ENDPARENT && !(lst.get(i).getValue()==LexerInnerObjectType.STARTPARENT)){
                i++;
            }
            else if(lst.get(j).getValue()==LexerInnerObjectType.ENDPARENT && lst.get(i).getValue() == LexerInnerObjectType.STARTPARENT){
                parentPairs[0] = i;
                parentPairs[1] = j;
                break;
            }
            else{
                j--;
                i++;
            }
        }
        return parentPairs;

    }
    public Integer[] getParentPairs(Integer start){
        List<LexerObject> lst = this.lexxed;

        Integer end = 0;
        Integer parentLevles = 0;
        boolean sawParent = false;
        for(int i=start;i<lst.size()-1;i++){
            if(lst.get(i).getValue() == LexerInnerObjectType.STARTPARENT){
                sawParent = true;
                parentLevles+=1;
            }
            else if(lst.get(i).getValue() == LexerInnerObjectType.ENDPARENT){
                parentLevles -=1;
            }
            if(sawParent && parentLevles == 0){
                end = i;
                break;
            }

        }
        Integer i = start;
        Integer j = end;
        Integer[] parentPairs = {0,0};
        while(i<j){
            if(lst.get(i).getValue() == LexerInnerObjectType.STARTPARENT && !(lst.get(j).getValue() == LexerInnerObjectType.ENDPARENT)){
                j--;
            }else if(lst.get(j).getValue() == LexerInnerObjectType.ENDPARENT && !(lst.get(i).getValue()==LexerInnerObjectType.STARTPARENT)){
                i++;
            }
            else if(lst.get(j).getValue()==LexerInnerObjectType.ENDPARENT && lst.get(i).getValue() == LexerInnerObjectType.STARTPARENT){
                parentPairs[0] = i;
                parentPairs[1] = j;
                break;
            }
            else{
                j--;
                i++;
            }
        }
        return parentPairs;

    }
    public Map<String,ClassStorage> classes = new HashMap<>();
    public void parseFunctions(){
        List<FunctionLocations> functionLocations = new ArrayList<>();

        boolean underClass = false;
        Integer classEndLocation = 0;
        String className = "";
        for(int i=0;i<this.lexxed.size();i++){
            if(i >= classEndLocation){
                underClass = false;
                classEndLocation = 0;
                className = "";
            }
            LexerType typeI = this.lexxed.get(i).getType();
            LexerInnerObjectType typeVal = this.lexxed.get(i).getValue();
            Object val = this.lexxed.get(i).getInner_Val();
            if(typeI == LexerType.DEFS){


                if(typeVal==LexerInnerObjectType.FUNC){
                    String funcName = (String) this.lexxed.get(i+1).getInner_Val();



                    List<String> args=new ArrayList<>();
                    List<String> adding = new ArrayList<>();
                    Integer[] parentPairs = this.getParentPairs(i+2);

                    for(int j=parentPairs[0]+1;j<parentPairs[1];j++){
                        LexerObject cur = this.lexxed.get(j);
                        if(cur.getValue() == LexerInnerObjectType.COMMA){
                            args.addAll(adding);
                            adding = new ArrayList<>();

                        }else if(this.lexxed.get(j).getInner_Val()!=null){
                            adding.add((String) this.lexxed.get(j).getInner_Val());

                        }
                    }
                    LocalStorage store = new LocalStorage();
                    StartNode newTree = new StartNode();
                    AcceptsArguments acceptedArgs = new AcceptsArguments(args,store);

                    if(this.lexxed.get(parentPairs[1]+1).getValue() == LexerInnerObjectType.STARTCURL){
                        if(underClass){
                            Integer[] pairs = this.getCurlyPairs(parentPairs[1]);
                            List<LexerObject> code = new ArrayList<>();
                            for(int j=pairs[0]+1;j<pairs[1];j++){
                                LexerObject cur = this.lexxed.get(j);
                                code.add(cur);

                            }
                            RawFunction tempRawFunc = new RawFunction(code,acceptedArgs,store);
                            this.classes.get(className).functions.put(funcName,tempRawFunc);

                        }
                        else functionLocations.add(new FunctionLocations(funcName,store,newTree,acceptedArgs,parentPairs[1]+1));

                    }



                }else if(typeVal == LexerInnerObjectType.CLASS&&!underClass){
                    className = (String) this.lexxed.get(i+1).getInner_Val();
                    underClass = true;
                    Integer[] curlies = this.getCurlyPairs(i+2);
                    classEndLocation = curlies[1];
                    this.classes.put(className,new ClassStorage(new HashMap<String, RawFunction>(),className));

                }
            }


        }
        for(FunctionLocations i:functionLocations){
            Integer[] pairs = this.getCurlyPairs(i.loc-1);
            List<LexerObject> code = new ArrayList<>();
            for(int j=pairs[0]+1;j<pairs[1];j++){
                LexerObject cur = this.lexxed.get(j);
                code.add(cur);

            }
            RawFunction tempRawFunc = new RawFunction(code,i.acceptedArgs,i.store);
            TemporaryRawFunctionStorageForParsing.variables.put(i.funcName,tempRawFunc);
        }

    }
    public Map<String,ParsedFunction> parseInFuncs(){
        Map<String,ParsedFunction> map = new HashMap<>();
        for(String i:TemporaryRawFunctionStorageForParsing.variables.keySet()){
            RawFunction cur = TemporaryRawFunctionStorageForParsing.variables.get(i);
            String funcName = i;
            Node parsed = this.parse(cur.code,new StartNode());
            AcceptsArguments args = cur.args;
            ParsedFunction func = new ParsedFunction(parsed,funcName,args);
            map.put(funcName,func);
        }
        return map;
    }
    public Map<String,ParsedClass> parseClasses(){
        Map<String,ParsedClass> ret = new HashMap<>();
        for(String i:this.classes.keySet()){
            ClassStorage thisStorage = this.classes.get(i);
            ParsedClass parsedClass = new ParsedClass(i,new HashMap<String, ParsedFunction>());
            for(String j:thisStorage.functions.keySet()){
                RawFunction cur = thisStorage.functions.get(j);
                String fName= j;
                Node parsed = this.parse(cur.code,new StartNode());
                AcceptsArguments args = cur.args;
                ParsedFunction func = new ParsedFunction(parsed,fName,args);
                parsedClass.parsedFunctionMap.put(fName,func);


            }
            ret.put(i,parsedClass);
        }
        return ret;
}
    public Node parse(List<LexerObject> toParse, Node op){
        List<LexerObject> curCodes = toParse;
        int i=0;
        while(i<curCodes.size()){
            LexerObject thisElement = curCodes.get(i);
            Object thisValue = null;
            LexerType thisType;
            if(thisElement.getInner_Val() == null){
                thisValue = thisElement.getValue();
            }
            else{
                thisValue = thisElement.getInner_Val();
            }
            thisType = thisElement.getType();
            Object firstEl = thisValue;
            if(firstEl == LexerInnerObjectType.PRN){
                Integer[] outermost = this.getParentPairs(i,curCodes);

                List<LexerObject> body = new ArrayList<>();
                for(int j=outermost[0]+1;j<outermost[1];j++){
                    body.add(curCodes.get(j));
                }
                System.out.println(curCodes.get(0).getValue());
                System.out.println(Arrays.toString(outermost));
                for(LexerObject dd:body) System.out.println(dd.getValue() + " | "+dd.getInner_Val() + " | "+dd.getType());
                i=outermost[1]+1;
                System.out.println(i);
                Node e = new Node("print","operator");
                Node e2 = this.parse(body,e);

                op.addChild(e2);


            }
            else if(firstEl == LexerInnerObjectType.IF){
                Integer [] parenthesis = this.getParentPairs(i,curCodes);
                List<LexerObject> body = new ArrayList<>();
                for(int j=parenthesis[0]+1;j<parenthesis[1];j++){
                    body.add(curCodes.get(j));
                }
                Integer[] curlies = this.getCurlyPairs(parenthesis[1]+1,curCodes);
                List<LexerObject> contents = new ArrayList<>();

                for(int j=curlies[0]+1;j<curlies[1];j++){
                    contents.add(curCodes.get(j));
                }
                Node thisIf = op.addChild(new Node("if","operator"));
                Node conditions = thisIf.addChild(new Node("conditions","null"));
                for(LexerObject j:body){
                    System.out.println("BODYLOLIF "+j.getValue()+ " | "+j.getType() + " | "+j.getInner_Val());
                }
                this.parse(body,conditions);
                Node toRun = thisIf.addChild(new Node("contents","null"));
                this.parse(contents,toRun);
                op.addChild(thisIf);
                i = curlies[1]+1;


            }
            else if(firstEl instanceof Integer){
                boolean flag = true;
                if(i<curCodes.size()-1){
                    if(curCodes.get(i+1).getType() == LexerType.EXP){
                        flag = false;
                    }

                }
                if(i>0){
                    if(curCodes.get(i-1).getType()==LexerType.EXP){
                        flag = false;
                    }
                }
                if(flag) op.addChild(new Node((Integer) firstEl,"integer"));

            }
            else if(thisType == LexerType.STR){
                boolean flag = true;
                if(i<curCodes.size()-1){
                    if(curCodes.get(i+1).getType() == LexerType.EXP){
                        flag = false;
                    }

                }
                if(i>0){
                    if(curCodes.get(i-1).getType()==LexerType.EXP){
                        flag = false;
                    }
                }
                if(flag) op.addChild(new Node((String) firstEl,"string"));
            }
            else if(firstEl == LexerInnerObjectType.STARTPARENT){
                boolean flag = true;

                if(i>0){
                    if(curCodes.get(i-1).getType()==LexerType.EXP){
                        flag = false;
                    }
                }
                Integer[] pairs = this.getParentPairs(i,curCodes);
                System.out.println("PAIRS"+Arrays.toString(pairs));
                if(pairs[1]+1<curCodes.size()&&curCodes.get(pairs[1]+1).getType() == LexerType.EXP){
                    i = pairs[1]+1;
                    flag = false;
                    List<LexerObject> side1 = new ArrayList<>();
                    List<LexerObject> side2 = new ArrayList<>();
                    Integer loops = 0;
                    for(int j=i+1;j<curCodes.size();j++){
                        if(curCodes.get(j).getValue() == LexerInnerObjectType.STOPL){
                            break;
                        }
                        loops++;
                        side2.add(curCodes.get(j));

                    }
                    for(int j=0;j<i;j++){
                        side1.add(curCodes.get(j));

                    }
                    for(LexerObject dd:side1) System.out.println("dddd"+dd.getValue() + " | "+dd.getInner_Val() + " | "+dd.getType());
                    Node temp1 = op.addChild(new Node(new Operator((String) curCodes.get(i).getInner_Val()).name,"operator"));
                    temp1.addChild(this.parse(side1,new Node("ReturnVale","null")));
                    temp1.addChild(this.parse(side2,new Node("ReturnValue","null")));
                    i+= loops;
                }
                if(flag){


                    List<LexerObject> body = new ArrayList<>();
                    for(int j=pairs[0]+1;j<pairs[1];j++){
                        body.add(curCodes.get(j));

                    }
                    i=pairs[1]+1;
                    Node e= new Node("Parenthesis","null");
                    e = this.parse(body,e);
                    op.addChild(e);

                }

            }
            else if(thisType == LexerType.EXP){

                List<LexerObject> side1 = new ArrayList<>();
                List<LexerObject> side2 = new ArrayList<>();
                Integer loops = 0;
                for(int j=i+1;j<curCodes.size();j++){
                    if(curCodes.get(j).getValue() == LexerInnerObjectType.STOPL){
                        break;
                    }
                    loops++;
                    side2.add(curCodes.get(j));

                }
                for(int j=0;j<i;j++){
                    side1.add(curCodes.get(j));

                }
                for(LexerObject dd:side1) System.out.println("dddd"+dd.getValue() + " | "+dd.getInner_Val() + " | "+dd.getType());
                Node temp1 = op.addChild(new Node(new Operator((String) firstEl).name,"operator"));
                temp1.addChild(this.parse(side1,new Node("ReturnVale","null")));
                temp1.addChild(this.parse(side2,new Node("ReturnValue","null")));
                i+= loops;
            }
            else if(thisType == LexerType.ASS){
                String side1;
                List<LexerObject> side2 = new ArrayList<>();
                Integer loops = 0;
                for(int j=i+1;j<curCodes.size();j++){
                    if(curCodes.get(j).getValue() == LexerInnerObjectType.STOPL){
                        break;
                    }
                    loops += 1;
                    side2.add(curCodes.get(j));

                }

                side1 = (String) curCodes.get(i-1).getInner_Val();
                Node temp1 = op.addChild(new Node("ASSIGN","operator"));
                temp1.addChild(new Node(side1,"loc"));
                temp1.addChild(this.parse(side2,new Node("VALUE","val")));
                i+=loops;
            }
            else if(thisType == LexerType.GETM){
                boolean condition = true;
                for(int j=i+1;j<curCodes.size();j++){
                    if(curCodes.get(j).getValue() == LexerInnerObjectType.STOPL || curCodes.get(j).getType() == LexerType.EXP) break;
                    if(curCodes.get(j).getValue() == LexerInnerObjectType.GETM) {condition = false;break;}
                }
                if(condition) {

                    List<LexerObject> side1 = new ArrayList<>();
                    List<LexerObject> side2 = new ArrayList<>();
                    Integer loops = 0;
                    for (int j = i + 1; j < curCodes.size(); j++) {
                        if (curCodes.get(j).getValue() == LexerInnerObjectType.STOPL || curCodes.get(j).getValue() == LexerInnerObjectType.GETM ||
                        curCodes.get(j).getType() == LexerType.EXP) {
                            break;
                        }
                        loops++;
                        side2.add(curCodes.get(j));

                    }

                    for(int j=i-1;j>=0;j--){
                        if(curCodes.get(j).getValue() == LexerInnerObjectType.NEWLINE) break;
                        side1.add(curCodes.get(j));
                    }
                    Collections.reverse(side1);


                    for(LexerObject jj : side1) System.out.println("SIDE2"+jj.getValue());
                    if(i+loops+1 >= curCodes.size()||curCodes.get(i+loops+1).getType() != LexerType.EXP){
                        Node temp1 = op.addChild(new Node("GETM", "operator"));
                        temp1.addChild(this.parse(side1, new Node("Class", "null")));
                        temp1.addChild(this.parse(side2, new Node("Method", "null")));
                        i+=loops+1;
                    }

                }
            }
            else if(thisType == LexerType.UNKNOWN){
                boolean flag = true;
                if(i<curCodes.size()-1){
                    if(curCodes.get(i+1).getType() == LexerType.EXP || curCodes.get(i+1).getType() == LexerType.ASS){
                        flag = false;
                    }

                }
                if(i>0){
                    if(curCodes.get(i-1).getType()==LexerType.EXP || curCodes.get(i-1).getType()==LexerType.ASS){
                        flag = false;
                    }
                }
                if(i<curCodes.size()-1){
                    if(curCodes.get(i+1).getValue() == LexerInnerObjectType.STARTPARENT){
                        Integer[] parentPairs = getParentPairs(i+1,curCodes);

                        List<List<LexerObject>> args=new ArrayList<>();
                        List<LexerObject> adding = new ArrayList<>();

                        for(int j=parentPairs[0]+1;j<parentPairs[1];j++){
                            LexerObject cur = curCodes.get(j);
                            if(cur.getValue() == LexerInnerObjectType.COMMA){
                                args.add(adding);
                                adding = new ArrayList<>();

                            }else{
                                adding.add(curCodes.get(j));

                            }
                        }
                        List<Node> parsedArgs = new ArrayList<>();
                        Node execFunc = op.addChild(new Node("EXEC","operator"));
                        execFunc.addChild(new Node((String) thisValue,"funcName"));

                        Node argumentsHolder = execFunc.addChild(new Node("ARGS","null"));
                        int cnt = 0;
                        for(List<LexerObject> j:args){
                            Node d = argumentsHolder.addChild(new Node(cnt,"argNum"));


                            Node l = this.parse(j,d);





                            cnt++;
                            parsedArgs.add(d);
                        }
                        i += parentPairs[1] - parentPairs[0];





                    }else if (flag) op.addChild(new Node(thisValue,"UNDEFINED"));
                }

                else if(flag) op.addChild(new Node(thisValue,"UNDEFINED"));

            }
            i++;

        }
        return op;


    }

}

class FunctionLocations{
    public String funcName;
    public LocalStorage store;
    public Node newTree;
    public AcceptsArguments acceptedArgs;
    public Integer loc;
    public FunctionLocations(String funcName,LocalStorage store,Node newTree,AcceptsArguments acceptedArgs,Integer loc){
        this.funcName = funcName;
        this.newTree = newTree;
        this.acceptedArgs = acceptedArgs;
        this.loc = loc;

    }
}
