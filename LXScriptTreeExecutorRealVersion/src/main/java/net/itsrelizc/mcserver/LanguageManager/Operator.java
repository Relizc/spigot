package net.itsrelizc.mcserver.LanguageManager;

import com.google.common.collect.Sets;

import javax.swing.plaf.nimbus.State;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Operator {
    public String id;
    String name;
    public Operator(String id){
        this.id = id;
        Set<String> tempVals = new HashSet<>(Arrays.asList(getNames(LexerInnerObjectType.class)));
        if(tempVals.contains(id)){
            this.name = id;
        }
        else{
            System.out.println("COUNT               d");
            this.name = Lexer.temp2names[Arrays.asList(Lexer.temp2).indexOf(id)];
            System.out.println(this.name);

        }



    }
    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
    }
}
