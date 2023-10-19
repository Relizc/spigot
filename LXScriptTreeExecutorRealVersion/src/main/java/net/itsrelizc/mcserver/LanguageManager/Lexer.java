package net.itsrelizc.mcserver.LanguageManager;



import java.util.*;

public class Lexer {
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<LexerObject> getLexxed() {
        return lexxed;
    }

    public void setLexxed(List<LexerObject> lexxed) {
        this.lexxed = lexxed;
    }

    public Map<String, LexerInnerObjectType> getKwrds() {
        return kwrds;
    }

    public void setKwrds(Map<String, LexerInnerObjectType> kwrds) {
        this.kwrds = kwrds;
    }

    private String code;
    private List<LexerObject> lexxed = new ArrayList<>();

    private Map<String, LexerInnerObjectType> kwrds = new HashMap<>();
    private final String[] temp1 = {" ","+","-","*","/","^","<<",">>","|","&","`","(",")","{","}",";",",","."};
    public final static String[] temp2 = {"+","-","*","/","^","<<",">>","|","&","`","==","!=","<=","<",">=",">",".="};
    public final static String[] temp2names = {"PLUS","MINUS","MULTIPLY","DIVIDE","POWER","BITMOV_LEFT","BITMOV_RIGHT","OR","AND","XOR","EQUALS_CHECK","UNEQUAL_CHECK",
    "LESS_EQUAL","LESS","GREATER_EQUAL","GREATER","INSTANCEOF"
    };
    public Lexer(String code){
        this.kwrds.put("print",LexerInnerObjectType.PRN);
        this.kwrds.put("if",LexerInnerObjectType.IF);
        this.kwrds.put("while",LexerInnerObjectType.WHILE);
        this.kwrds.put("func",LexerInnerObjectType.FUNC);

        this.kwrds.put("class",LexerInnerObjectType.CLASS);
        this.kwrds.put("import",LexerInnerObjectType.IMPORT);
//        this.kwrds.put("global",LexerInnerObjectType.GLOBAL);
        this.code = code;

    }
    public Integer count(String toCount,char to){
        int a = 0;
        for(char i:toCount.toCharArray()){
            if(i==to) a++;
        }
        return a;
    }
    public boolean checkInt(String s){
        boolean ret = true;
        try{
            Integer e = Integer.parseInt(s);}
        catch (NumberFormatException e) {
            ret = false;

        }
        return ret;

    }

    public void lex(){
        StringBuilder read = new StringBuilder();
        Boolean appearedQuote = false;


        for(String k:code.split("\n")){
            for(int i=0;i<k.length();i++){
                read.append(k.charAt(i));

                if(!appearedQuote) read = new StringBuilder(read.toString().trim());
                if(i+1<k.length()){
                    if(Arrays.asList(this.temp1).contains(String.valueOf(k.charAt(i+1))) &&!appearedQuote){


                        if(this.kwrds.containsKey(read.toString())){

                            LexerInnerObjectType thisKey = this.kwrds.get(read.toString());
                            if(thisKey == LexerInnerObjectType.PRN) this.lexxed.add(new LexerObject(LexerType.FUNC,thisKey));
                            else if(thisKey == LexerInnerObjectType.IF) this.lexxed.add(new LexerObject(LexerType.IF,thisKey));
                            else if(thisKey == LexerInnerObjectType.WHILE) this.lexxed.add(new LexerObject(LexerType.LOOP,thisKey));
                            else if(thisKey == LexerInnerObjectType.FUNC||thisKey == LexerInnerObjectType.CLASS) this.lexxed.add(new LexerObject(LexerType.DEFS,thisKey));

                            else if(thisKey == LexerInnerObjectType.IMPORT) this.lexxed.add(new LexerObject(LexerType.FUNC,thisKey));
                            else if(thisKey == LexerInnerObjectType.GLOBAL) this.lexxed.add(new LexerObject(LexerType.FUNC,thisKey));
                            read = new StringBuilder();

                        }else{
                            if(count(read.toString(),'"')==1){
                                appearedQuote = true;
                            }
                            else if(count(read.toString(),'"')==2){
                                String read114 = read.substring(1,read.length()-1);
                                read = new StringBuilder();
                                this.lexxed.add(new LexerObject(LexerType.STR,read114));
                                appearedQuote = false;

                            }else if(Arrays.asList(temp2).contains(read.toString())){
                                if(k.charAt(i+1)!='='){
                                    this.lexxed.add(new LexerObject(LexerType.EXP,read.toString()));
                                    read = new StringBuilder();
                                }
                            }else if(read.toString().equals("(")){
                                this.lexxed.add(new LexerObject(LexerType.SYN,LexerInnerObjectType.STARTPARENT));
                                read = new StringBuilder();
                            }else if(read.toString().equals(")")){
                                this.lexxed.add(new LexerObject(LexerType.SYN,LexerInnerObjectType.ENDPARENT));
                                read = new StringBuilder();
                            }else if(read.toString().equals("{")){
                                this.lexxed.add(new LexerObject(LexerType.SYN,LexerInnerObjectType.STARTCURL));
                                read = new StringBuilder();
                            }else if(read.toString().equals("}")){
                                this.lexxed.add(new LexerObject(LexerType.SYN,LexerInnerObjectType.ENDCURL));
                                read = new StringBuilder();
                            }else if(read.toString().equals(";")){
                                this.lexxed.add(new LexerObject(LexerType.ID,LexerInnerObjectType.STOPL));
                                read = new StringBuilder();
                            }else if(read.toString().equals(",")){
                                this.lexxed.add(new LexerObject(LexerType.ID,LexerInnerObjectType.COMMA));
                                read = new StringBuilder();
                            }else if(read.toString().equals("=")){
                                if(k.charAt(i+1) != '='){
                                    this.lexxed.add(new LexerObject(LexerType.ASS,LexerInnerObjectType.ASS));
                                    read = new StringBuilder();
                                }
                            }else if(read.toString().equals(".")){
                                if(k.charAt(i+1) != '='){
                                    this.lexxed.add(new LexerObject(LexerType.GETM,LexerInnerObjectType.GETM));
                                    read = new StringBuilder();
                                }
                            }
                            else if (checkInt(read.toString())&&(i==k.length()-1||!checkInt(String.valueOf(k.charAt(i+1))))) {
                                this.lexxed.add(new LexerObject(LexerType.SYM,(Integer) Integer.parseInt(read.toString())));
                                read = new StringBuilder();
                            }else{
                                if(!read.toString().isEmpty()){
                                    this.lexxed.add(new LexerObject(LexerType.UNKNOWN,read.toString()));
                                    read = new StringBuilder();

                                }
                            }
                        }

                    }

                }
                if(count(read.toString(),'"')==1){
                    appearedQuote = true;
                }
                else if(count(read.toString(),'"')==2){
                    String read114 = read.substring(1,read.length()-1);
                    read = new StringBuilder();
                    this.lexxed.add(new LexerObject(LexerType.STR,read114));
                    appearedQuote = false;

                }else if(Arrays.asList(temp2).contains(read.toString())){
                    if(k.charAt(i+1)!='='){
                        this.lexxed.add(new LexerObject(LexerType.EXP,read.toString()));
                        read = new StringBuilder();
                    }
                }else if(read.toString().equals("(")){
                    this.lexxed.add(new LexerObject(LexerType.SYN,LexerInnerObjectType.STARTPARENT));
                    read = new StringBuilder();
                }else if(read.toString().equals(")")){
                    this.lexxed.add(new LexerObject(LexerType.SYN,LexerInnerObjectType.ENDPARENT));
                    read = new StringBuilder();
                }else if(read.toString().equals("{")){
                    this.lexxed.add(new LexerObject(LexerType.SYN,LexerInnerObjectType.STARTCURL));
                    read = new StringBuilder();
                }else if(read.toString().equals("}")){
                    this.lexxed.add(new LexerObject(LexerType.SYN,LexerInnerObjectType.ENDCURL));
                    read = new StringBuilder();
                }else if(read.toString().equals(";")){
                    this.lexxed.add(new LexerObject(LexerType.ID,LexerInnerObjectType.STOPL));
                    read = new StringBuilder();
                }else if(read.toString().equals(",")){
                    this.lexxed.add(new LexerObject(LexerType.ID,LexerInnerObjectType.COMMA));
                    read = new StringBuilder();
                }else if(read.toString().equals("=")){
                    if(k.charAt(i+1) != '='){
                        this.lexxed.add(new LexerObject(LexerType.ASS,LexerInnerObjectType.ASS));
                        read = new StringBuilder();
                    }
                } else if (checkInt(read.toString())&&(i==k.length()-1||!checkInt(String.valueOf(k.charAt(i+1))))) {
                    this.lexxed.add(new LexerObject(LexerType.SYM,(Integer) Integer.parseInt(read.toString())));
                    read = new StringBuilder();
                }

            }
            this.lexxed.add(new LexerObject(LexerType.ID,LexerInnerObjectType.NEWLINE));
        }
    }
}
