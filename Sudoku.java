package com.example.anna.sudoku;

import java.util.Random;

/**
 * Created by Anna on 15.05.2018.
 */

public class Sudoku {
    public Sudoku() {
        for(Integer i=0;i<=8;i++)
            for(Integer j=0; j<=8; j++){
                array[i][j]=0;
            };
    }

    public void zufallsarray() {
        for(Integer i=0;i<=8;i++)
            for(Integer j=0; j<=8; j++){
                array[i][j]=0;
            }
        for(Integer i=0;i<=20;i++){
            i--;
            Random zufallsgenerator = new Random();
            Integer lage = zufallsgenerator.nextInt(80);
            Integer number = (zufallsgenerator.nextInt(8))+1;
            Integer x=lage%9;
            Integer y=lage/9;
            if(fuellenpruefen(x,number,y,array)){
                array[x][y]=number;
                i++;
            }
        }
    };

    public boolean fuellenpruefen(Integer x, Integer a, Integer y, Integer[][] array1) {
        for(Integer i=0; i<=8;i++){
            if(array1[i][y]==a)
                return false; }
        for(Integer j=0; j<=8;j++){
            if(array1[x][j]==a)
                return false; }
        if(x<=2&&y<=2){
            if(!bearbeiten.durchsuchen(2,2,a, array1))
                return false;
        }
        else if (x<=5&&y<=2){
            if(!bearbeiten.durchsuchen(5,2,a, array1))
                return false;
        }
        else if (x<=8&&y<=2){
            if(!bearbeiten.durchsuchen(8,2,a, array1))
                return false;
        }
        else if (x<=2&&y<=5){
            if(!bearbeiten.durchsuchen(2,5,a, array1))
                return false;
        }
        else if (x<=5&&y<=5){
            if(!bearbeiten.durchsuchen(5,5,a, array1))
                return false;
        }
        else if (x<=8&&y<=5){
            if(!bearbeiten.durchsuchen(8,5,a,array1))
                return false;
        }
        else if (x<=2){
            if(!bearbeiten.durchsuchen(2,8,a,array1))
                return false;
        }
        else if (x<=5){
            if(!bearbeiten.durchsuchen(5,8,a,array1))
                return false;
        }
        else {
            if(!bearbeiten.durchsuchen(8,8,a,array1))
                return false;
        }
        return true;
    };

    public boolean loesen(Integer[][] array) {
        //leere Stelle suchen
        Integer x=0,y=0;
        boolean nullstelle=false;
        for(Integer i=0;i<=8;i++){
            for(Integer j=0; j<=8; j++){
                if(array[i][j]==0){
                    x=i;
                    y=j;
                    nullstelle=true;
                }
            }}
        //keine Nullstelle gefunden
        if (!nullstelle) return true;
        //Zahlen probieren
        for(Integer number=1;number<=9;number++){
            if (fuellenpruefen(x,number,y, array)){
                array[x][y]=number;
                //Rekursion
                if((loesen(array))){
                    return true;}
                //zurück
                else array[x][y]=0;
            }
        }
        //nicht lösbar
        return false;
    }

    public void soweitwiemoeglich(Integer[][] array){
        boolean eingesetzt=false;
        for(Integer i=0;i<=8;i++){
            for(Integer j=0; j<=8; j++){
                if(array[i][j]==0){
                    Integer x=i;
                    Integer y=j;
                    int zaehler=0;
                    for(Integer r=1; r<10; r++){
                        if(fuellenpruefen(x, r, y, array)){
                            zaehler=(zaehler+1);
                            array[x][y]=r;
                            eingesetzt=true;
                        }
                    }
                    if(zaehler>=2){
                        array[x][y]=0;
                        eingesetzt=false;
                    }
                }
            }};
    }

    public boolean loesbarkeitpruefen(Integer[][] array) {
        int vorher=81;
        int nachher=5;
        do{
            vorher=bearbeiten.nullstellenzaehlen(array);
            soweitwiemoeglich(array);
            nachher=bearbeiten.nullstellenzaehlen(array);
        }while(vorher!=nachher);
        if(nachher==0){return true;}
        else return false;
    };

    public void leeren() {
        for(Integer i=1; i<=schwierig; i++){
            i--;
            Random zufallsgenerator = new Random();
            Integer lage = zufallsgenerator.nextInt(80);
            Integer x=lage%9;
            Integer y=lage/9;
            if(array[x][y]!=0){
                array[x][y]=0;
                i++;
            };
        };
    };

    public void erstellen() {
        boolean loese=false;
        boolean leeren=false;
        Integer[][] kopie= new Integer[9][9];
        Integer[][] kopie1= new Integer[9][9];
        do {
            zufallsarray();
            loese= loesbarkeitpruefen(array);
            if(!loese)loese = loesen(array);
        }
        while (loese == false);
        bearbeiten.arraystauschen(kopie,array);
        do{
        leeren();
            bearbeiten.arraystauschen(kopie1,array);
            leeren=loesbarkeitpruefen(array);
        if(!leeren){
            bearbeiten.arraystauschen(array,kopie);
        }
        }while (!leeren);
        bearbeiten.arraystauschen(array, kopie1);
    };

    public void setSchwierig(Integer schwierig) {
        this.schwierig = schwierig;
    }

    public Integer[][] array = new Integer[9][9];
    private SudokuBearbeiten bearbeiten= new SudokuBearbeiten();
    private Integer schwierig=30;
}
