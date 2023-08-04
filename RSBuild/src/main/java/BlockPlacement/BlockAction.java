package BlockPlacement;

import Items.MaterialAndData;
import org.bukkit.Material;

public class BlockAction {
    public MaterialAndData[][][] structure;
    public void setStructureSize(int x,int y,int z){
        structure = new MaterialAndData[x][y][z];

    }
    public void RotateHorizontalLeft(){
        for(int ij =0;ij<structure.length;ij++){
            MaterialAndData[][] i = structure[ij];

            MaterialAndData[][] rotted = rotateArray(i);
            structure[ij] = rotted.clone();

        }
    }
    public void RotateHorizontalLeft(int times){
        for(int j=0;j<times;j++){
            for(int ij =0;ij<structure.length;ij++){
                MaterialAndData[][] i = structure[ij];
                MaterialAndData[][] rotted = rotateArray(i);
                structure[ij] = rotted.clone();

            }
        }
    }
    public void RotateUp(){
        MaterialAndData[][][] convertedList = new MaterialAndData[structure[0][0].length][structure.length][structure[0].length];
        for(int i=0;i<structure.length;i++){
            for(int j=0;j<structure[0].length;j++){
                for(int k=0;k<structure[0][0].length;k++){
                    convertedList[k][i][j] = structure[i][j][k];
                }
            }
        }
        for(int i=0;i<convertedList.length;i++){
            MaterialAndData[][] toConvert = convertedList[i];
            MaterialAndData[][] rotted = rotateArray(toConvert);
            convertedList[i] = rotted.clone();
        }
        MaterialAndData[][][] convertionBuffer = new MaterialAndData[structure.length][structure[0].length][structure[0][0].length];
        for(int i=0;i<structure[0][0].length;i++){
            for(int j=0;j<structure[0].length;j++){
                for(int k=0;k<structure.length;k++){
                    convertionBuffer[k][j][i] = convertedList[i][j][k];

                }
            }
        }
        structure = convertionBuffer.clone();

    }
    void reverse(MaterialAndData a[], int n)
    {
        int i;
        MaterialAndData t;
        for (i = 0; i < n / 2; i++) {
            t = a[i];
            a[i] = a[n - i - 1];
            a[n - i - 1] = t;
        }


    }
    MaterialAndData[][] rotateArray(MaterialAndData[][] toD1){
        MaterialAndData[][] toD = toD1.clone();
        MaterialAndData[][] ret = new MaterialAndData[toD[0].length][toD.length];
        for(int i=0;i<toD.length;i++){
            for(int j=0;j<toD[0].length;j++){
                ret[j][i] = toD[i][j];

            }



        }
        for(int i=0;i<ret.length;i++){
            reverse(ret[i],ret[i].length);
        }
        return ret;
    }

}
