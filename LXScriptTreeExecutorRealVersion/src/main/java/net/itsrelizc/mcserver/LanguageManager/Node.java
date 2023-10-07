package net.itsrelizc.mcserver.LanguageManager;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    private Object value;
    private String name;
    private Integer id;
    private Node parent = null;
    private List<Node> children = new ArrayList<>();
    public Node(Object value,String name){
        this.value = value;
        this.name = name;
        this.id = InitTimes.initThing;
        InitTimes.initThing++;

    }
    public Node addChildNew(Object val,String name){
        Node new1 = new Node(val,name);
        new1.setParent(this);
        this.children.add(new1);
        return new1;
    }
    public Node addChild(Node child){
        this.children.add(child);
        child.setParent(this);
        return child;
    }

    public Node getNodeByValue(Object value){
        for(Node i:this.children){
            if(i.value == value){
                return i;
            }
        }
        return null;
    }
    public Node getNodebyName(String name){
        for(Node i:this.children){
            if(i.name == name){
                return i;
            }
        }
        return null;
    }

}
