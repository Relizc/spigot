package net.itsrelizc.mcserver.LanguageManager;

public class ParsedFunction {
    public Node contents;
    public String name;
    public AcceptsArguments args;
    public ParsedFunction(Node contents, String name, AcceptsArguments args){
        this.contents = contents;
        this.name = name;
        this.args = args;
    }
}
