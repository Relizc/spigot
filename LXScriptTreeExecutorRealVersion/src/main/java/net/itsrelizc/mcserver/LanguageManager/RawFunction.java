package net.itsrelizc.mcserver.LanguageManager;

import java.util.List;

public class RawFunction {
    public List<LexerObject> code;
    public AcceptsArguments args;
    public LocalStorage localStorage;

    public RawFunction(List<LexerObject> code, AcceptsArguments arguments, LocalStorage localStorage){
        this.code = code;
        this.args = arguments;
        this.localStorage = localStorage;

    }
}
