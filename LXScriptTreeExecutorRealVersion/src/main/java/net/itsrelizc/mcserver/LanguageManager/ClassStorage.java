package net.itsrelizc.mcserver.LanguageManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassStorage {
    public Map<String, RawFunction> functions = new HashMap<>();
    public String name;
    public LocalStorage store;
    public ClassStorage(Map<String,RawFunction> functions,String name){
        this.name = name;
        this.functions = functions;
        this.store = new LocalStorage();

    }
}
