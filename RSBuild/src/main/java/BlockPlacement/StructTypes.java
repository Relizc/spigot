package BlockPlacement;

public enum StructTypes {
    RECT(1,"rect"),
    CIRCULAR(2,"sphere"),
    TRIANGLE(3,"triangle"),
    CYLINDER(4,"cylinder");
    public int id;
    public String name;

    private StructTypes(int id,String name){
        this.id = id;
        this.name = name;

    }
}
