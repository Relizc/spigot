package BlockPlacement;

import Items.MaterialAndData;

public class BlockAction {
    public MaterialAndData[][][] structure;
    public void setStructureSize(int x,int y,int z){
        structure = new MaterialAndData[x][y][z];

    }
    public void RotateHorizontalLeft(){
        for(int ij =0;ij<structure.length;ij++){
            MaterialAndData[][] i = structure[ij];

            MaterialAndData[][] rotted = rotateMatrix(i.length,i[0].length,i);
            structure[ij] = rotted.clone();

        }
    }
    public void RotateHorizontalLeft(int times){
        for(int j=0;j<times;j++){
            for(int ij =0;ij<structure.length;ij++){
                MaterialAndData[][] i = structure[ij];

                MaterialAndData[][] rotted = rotateMatrix(i.length,i[0].length,i);
                structure[ij] = rotted.clone();

            }
        }
    }

    MaterialAndData[][] rotateMatrix(int m, int n, MaterialAndData[][] arr1){
        int row = 0, col = 0;
        MaterialAndData prev, curr;
        MaterialAndData[][] arr = arr1.clone();

        while (row < m && col < n) {

            if (row + 1 == m || col + 1 == n)
                break;
            prev = arr[row + 1][col];
            for (int i = col; i < n; i++) {
                curr = arr[row][i]; arr[row][i] = prev;
                prev = curr;
            }
            row++;
            for (int i = row; i < m; i++) {
                curr = arr[i][n - 1]; arr[i][n - 1] = prev;
                prev = curr;
            }
            n--;
            if (row < m) {
                for (int i = n - 1; i >= col; i--) {
                    curr = arr[m - 1][i]; arr[m - 1][i] = prev;
                    prev = curr;
                }
            }
            m--;
            if (col < n) {
                for (int i = m - 1; i >= row; i--) {
                    curr = arr[i][col];
                    arr[i][col] = prev;
                    prev = curr;
                }
            }
            col++;
    }
    return arr.clone();

    }

}
