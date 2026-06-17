package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
    int[][] array ={{2, 20, 15}, {16, 7, 32}, {50, 4, 27}};
    for(int i=0; i<array.length; i++){
        for(int j=0; j<array[i].length; j++){
                if (array[i][j] <10)
                    break;
                System.out.printf("%d ", array[i][j]);
            }
        }
    }
}

