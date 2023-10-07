package net.itsrelizc.mcserver.LanguageManager;

import java.util.List;

public class AcceptsArguments {
    public List<String> args;
    public LocalStorage store;
    public AcceptsArguments(List<String> args, LocalStorage localStorage){
        this.args = args;
        this.store = localStorage;
        for(String i:args){
            this.store.variables.put(i,null);
        }
    }
}
