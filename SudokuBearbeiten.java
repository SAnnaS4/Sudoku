package com.example.anna.sudoku;

/**
 * Created by Anna on 17.05.2018.
 */

public class SudokuBearbeiten {
    public boolean durchsuchen(Integer x, Integer y, Integer a, Integer[][] array1){
        for(Integer minusi = x-2; minusi<=x;minusi++)
            for(Integer minusj=y-2; minusj<=y; minusj++){
                if (array1[minusi][minusj]==a) return false;
            };
        return true;
    };

    public void arraystauschen(Integer[][] array1,Integer[][] array2){
        for(Integer i=0;i<=8;i++)
            for(Integer j=0; j<=8; j++){
                array1[i][j]=array2[i][j];
            };
    }

    public int nullstellenzaehlen(Integer[][] array){
        int zaehler=0;
        for(Integer i=0;i<=8;i++)
            for(Integer j=0; j<=8; j++){
                if(array[i][j]==0)zaehler++;
            };
        return zaehler;
    }

    public boolean arraysVergleichen(Integer[][] array1,Integer[][] array2) {
        for(Integer i=0;i<=8;i++)
            for(Integer j=0; j<=8; j++){
                if (array1[i][j]!=array2[i][j]) return false;
            };
        return true;
    };

    public void LeeresSudoku(Integer[][] array) {
        for(Integer i=0;i<=8;i++)
            for(Integer j=0; j<=8; j++){
                array[i][j]=0;
            }
    };

}
