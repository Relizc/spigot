package BlockPlacement;

import javax.xml.stream.Location;

public class LocationStorer {
    public Integer x,y,z;
    public Location loc = null;
    public LocationStorer(Integer x,Integer y,Integer z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public LocationStorer(Integer x,Integer y,Integer z,Location loc){
        this.x = x;
        this.y = y;
        this.z = z;
        this.loc = loc;
    }
}
