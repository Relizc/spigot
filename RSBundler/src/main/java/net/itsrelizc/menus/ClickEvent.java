package net.itsrelizc.menus;

import java.util.function.Consumer;

public class ClickEvent {
    public ClickEvent(){

    }
    public static <Object> void functionAcceptsMethod(Consumer<Object> consumer, Object obj){
        consumer.accept(obj);

    }
}
