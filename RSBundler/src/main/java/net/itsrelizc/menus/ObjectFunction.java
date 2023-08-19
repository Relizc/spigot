package net.itsrelizc.menus;

public class ObjectFunction {
    private Runnable run;
    private Integer argsID;
    public ObjectFunction(Runnable toRun){
        this.run = toRun;
    }
    public void acceptArgs(Integer id,Object args){
        if(!RunnableArgumentHolder.arguments.containsKey(id)){
            System.out.println("ERROR:Key "+id+" already exists.");
            return;
        }
        this.argsID = id;
        RunnableArgumentHolder.arguments.put(id,args);
    }
    public void acceptArgs(Integer id){
        if(!RunnableArgumentHolder.arguments.containsKey(id)){
            System.out.println("ERROR:Key "+id+" already exists.");
            return;
        }
        this.argsID = id;
        RunnableArgumentHolder.arguments.put(id,null);
    }
    public void setArgs(Object args){
        if(!RunnableArgumentHolder.arguments.containsKey(this.argsID)){
            System.out.println("ERROR:Key "+this.argsID+" already exists.");
            return;
        }
        RunnableArgumentHolder.arguments.replace(this.argsID,args);
    }

    public void run(){

        this.run.run();
    }

}
