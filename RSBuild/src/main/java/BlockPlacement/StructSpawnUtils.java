package BlockPlacement;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class StructSpawnUtils {
    public static void spawnStructure(StructTypes type, Material mat,byte data, Player p,String[] args){
        System.out.println(args.length);

        switch(type){
            case CIRCULAR:
                Integer radius12 = Integer.parseInt(args[0]);
                Integer distortionheight = (int) Math.round(Integer.parseInt(args[1])*Math.PI);
                for(int radius1 = radius12;radius1 >0;radius1--){

                    for (double i = 0; i <= Math.PI; i += (Math.PI / distortionheight)) {
                        double radius = Math.sin(i)*radius1;
                        double y = Math.cos(i)*(distortionheight)/Math.PI;
                        for (double a = 0; a < Math.PI * 2; a+= Math.PI / (radius1*2*Math.PI)) {
                            double x = Math.cos(a) * radius;
                            double z = Math.sin(a) * radius;
                            p.getWorld().getBlockAt(p.getLocation().getBlockX()+(int) Math.round(x), p.getLocation().getBlockY()+(int) Math.round(y), p.getLocation().getBlockZ()+(int) Math.round(z)).setType(mat);
                            p.getWorld().getBlockAt(p.getLocation().getBlockX()+(int) Math.round(x), p.getLocation().getBlockY()+(int) Math.round(y), p.getLocation().getBlockZ()+(int) Math.round(z)).setData(data);

                        }
                    }
                }
                break;
            case RECT:
                Integer width = Integer.parseInt(args[0]);
                Integer height = Integer.parseInt(args[1]);
                Integer length = Integer.parseInt(args[2]);
                for(int i=p.getLocation().getBlockX();i<=p.getLocation().getBlockX()+width;i++){
                    for(int j=p.getLocation().getBlockY();j<=p.getLocation().getBlockY()+height;j++){
                        for(int k=p.getLocation().getBlockZ();k<=p.getLocation().getBlockZ()+length;k++){
                            p.getWorld().getBlockAt(i,j,k).setType(mat);
                            p.getWorld().getBlockAt(i,j,k).setData(data);

                        }
                    }
                }
                break;
            case CYLINDER:
                Integer radius = Integer.parseInt(args[0]);
                Integer height2 = Integer.parseInt(args[2]);
                Integer radius2 = Integer.parseInt(args[1]);
                Integer circumference = (int) Math.round((Math.PI*2)*Math.sqrt(
                                        ((Math.pow(radius,2)+Math.pow(radius2,2))/2)
                                ));

                for(int i=0;i<height2;i++){
                    double incrementation = (2*Math.PI)/circumference;
                    for(int j=0;j<=circumference+1;j++){
                        Integer x =(int) Math.round(radius * Math.cos(j*incrementation));
                        Integer z = (int) Math.round(radius2 * Math.sin(j*incrementation));
                        Integer realX = p.getLocation().getBlockX()+x;
                        Integer realY = p.getLocation().getBlockY()+i;
                        Integer realZ = p.getLocation().getBlockZ()+z;
                        p.getWorld().getBlockAt(realX,realY,realZ).setType(mat);
                        p.getWorld().getBlockAt(realX,realY,realZ).setData(data);


                    }
                }
                break;
            case TRIANGLE:
                p.sendMessage("§4WIP!");
                break;

        }
    }
}
