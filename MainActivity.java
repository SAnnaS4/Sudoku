package com.example.anna.sudoku;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Size;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Sudoku mySudoku = new Sudoku();
        final SudokuBearbeiten bearbeiten=new SudokuBearbeiten();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bearbeiten.LeeresSudoku(mySudoku.array);
        ausgeben(mySudoku.array);
       
        //mgw. noch Hinweis auf Sudoku lösen? 
        Toast.makeText(getApplicationContext(),"Bitte lesen Sie ein Sudoku ein oder geben Sie ein wie viele Felder belegt sein sollen.",Toast.LENGTH_LONG).show();
        
        //array mit Buttons füllen
        final Button []et= new Button[81];
        for(Integer i=1;i<=81;i++)
        {
            Integer in = i-1;
            String textID = "t" + i.toString();
            int resID = getResources().getIdentifier(textID, "id", getPackageName());
            et[in] = ((Button) findViewById((resID)));
        }
        
        Button erstellen = (Button) findViewById(R.id.erstellen);
        erstellen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et1 = ((EditText) findViewById((R.id.getSchwierig)));
                String text= et1.getText().toString();
                Integer zahl = Integer.parseInt(text);
                if (!((zahl<=53)&&(zahl>=0))){
                    bearbeiten.LeeresSudoku(mySudoku.array);
                    ausgeben(mySudoku.array);
                    Toast.makeText(getApplicationContext(),"Um ein eindeutiges Sudoku zu erstellen braucht man mehr Zahlen. Sie haben den Sudokuerstellmodus geöffnet.",Toast.LENGTH_LONG).show();
                }
                else {mySudoku.setSchwierig(zahl);
                    mySudoku.erstellen();
                    ausgeben(mySudoku.array);
                    mySudoku.loesbarkeitpruefen(mySudoku.array);}
                unantastbar(et);
            }
        });


        Button controll=(Button) findViewById(R.id.controll);
            controll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i=0;
                    boolean falsch=false;
                    for (Button button : et) {
                        Integer x = (i % 9);
                        Integer y = (i / 9);
                        i++;
                        if(button.getText().toString().equals(mySudoku.array[x][y].toString())){
                            button.setBackgroundResource(R.drawable.bg_edittext_normal);
                            button.setClickable(false);
                            button.setTypeface(null, Typeface.BOLD);
                            button.setTextColor(Color.rgb(0,100,0));
                        }
                        else {
                            //der Button leer ?
                            for (Integer j = 1; j <= 9; j++) {
                                if (button.getText().toString().equals(j.toString())) {
                                    button.setBackgroundResource(R.drawable.bg_edittext_wrong);
                                    falsch = true;
                                }
                            }
                            if (!falsch) button.setBackgroundResource(R.drawable.bg_edittext_normal);
                        }
                    }
                }
            });


        final ToggleButton hilfe = (ToggleButton) findViewById(R.id.mitHilfe);
        hilfe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(hilfe.isChecked()){
                    Integer i=0;
                    for (final Button button : et) {
                        final Integer x = (i % 9);
                        final Integer y = (i / 9);
                        i++;
                        button.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {}
                            @Override
                            public void afterTextChanged(Editable s) {
                                boolean falsch=false;
                                if (button.getText().toString().equals(mySudoku.array[x][y].toString())) {
                                    button.setBackgroundResource(R.drawable.bg_edittext_normal);
                                } else {
                                    // ist der button leer?
                                    for (Integer i = 1; i <= 9; i++) {
                                        if (button.getText().toString().equals(i.toString())) {
                                            button.setBackgroundResource(R.drawable.bg_edittext_wrong);
                                            falsch = true;
                                        }
                                    }
                                    if (!falsch) button.setBackgroundResource(R.drawable.bg_edittext_normal);
                                }
                            }
                        });
                    }
                }
                else {for (final Button button : et) {
                    button.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {}
                        @Override
                        public void afterTextChanged(Editable s) {
                            button.setBackgroundResource(R.drawable.bg_edittext_normal);
                        }
                    });
                }}
            }
        });

        //Eingabe in Sudoku
        for (final Button button : et) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (button.getText().toString()){
                        case "1":{ button.setText("2");
                                    break;}
                        case "2":{ button.setText("3");
                            break;}
                        case "3":{ button.setText("4");
                            break;}
                        case "4":{ button.setText("5");
                            break;}
                        case "5":{ button.setText("6");
                            break;}
                        case "6":{ button.setText("7");
                            break;}
                        case "7":{ button.setText("8");
                            break;}
                        case "8":{ button.setText("9");
                            break;}
                        case "9":{ button.setText("1");
                                break;}
                        default:{ button.setText("1");
                            break;}
                    }
                    Integer[][] kopie=new Integer[9][9];
                    Einlesen(kopie);
                    if(bearbeiten.nullstellenzaehlen(kopie)==0){
                        int i=0;
                        boolean richtig=false;
                        if(bearbeiten.arraysVergleichen(mySudoku.array,kopie)){richtig=true;}
                        //else richtig=false;
                        if(richtig){
                            Toast toast= new Toast(getApplicationContext());
                            toast.makeText(getApplicationContext(),"Herzlichen Glückwunsch! Sie haben gewonnen!",Toast.LENGTH_LONG).show();
                         }
                    }
                }
            });

        }

        //einlesen und lösen
        final ToggleButton einlesen = (ToggleButton) findViewById(R.id.einlesen);
        einlesen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(einlesen.isChecked()){
                    Einlesen(mySudoku.array);
                }
                else{
                   if(mySudoku.loesbarkeitpruefen(mySudoku.array)){}
                   else mySudoku.loesen(mySudoku.array);
                   ausgeben(mySudoku.array);
                }
            }
        });

    }

    public void ausgeben(Integer[][] array){
        for (Integer i = 0; i < 81; i++) {
            Integer iplus = i + 1;
            int x = (i % 9);
            int y = (i / 9);
            String textID = "t" + iplus.toString();
            int resID = getResources().getIdentifier(textID, "id", getPackageName());
            Button et = ((Button) findViewById((resID)));
            if (array[x][y] != 0) {
                String wert = (array[x][y]).toString();
                et.setText(wert);
            } else et.setText("");
        }
    }

    public void Einlesen(Integer[][] array){
        for (Integer i = 0; i < 81; i++) {
            Integer iplus = i + 1;
            int x = (i % 9);
            int y = (i / 9);
            String textID = "t" + iplus.toString();
            int resID = getResources().getIdentifier(textID, "id", getPackageName());
            Button et = ((Button) findViewById((resID)));
            if ((et.getText().toString()).equals("")) {
                array[x][y] = 0;
            } else {
                Integer zahl = Integer.parseInt(et.getText().toString());
                array[x][y] = zahl;
            }
        }
    }

    //Buttons können nicht mehr verändert werden
    public void unantastbar(Button[] buttons){
        for (Button button : buttons) {
            button.setClickable(true);
            button.setTypeface(null, Typeface.NORMAL);
            button.setTextColor(Color.BLACK);
        }
        for (Button button : buttons) {
            for(Integer i=1; i<=9; i++){
                if(button.getText().toString().equals(i.toString())){
                    button.setClickable(false);
                    button.setTypeface(null, Typeface.BOLD);
                    button.setTextColor(Color.rgb(0,100,0));
                }
            }
        }
    }
}
