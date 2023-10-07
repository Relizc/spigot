package net.itsrelizc.mcserver.LanguageManager;

import java.util.HashMap;
import java.util.Map;

public class ParsedClass {
    public Map<String,ParsedFunction> parsedFunctionMap = new HashMap<>();
    public String name;
    public LocalStorage storage;
    public ParsedClass(String name,Map<String,ParsedFunction> funcs){
        this.parsedFunctionMap = funcs;
        this.name = name;
        this.storage = new LocalStorage();
    }
}
