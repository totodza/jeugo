package com.example.juego;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    private Button[][] botonmatriz =new Button[3][3];
    private boolean jugador1turno=true;
    private int ronda;
    private int jugador1puntos;
    private int jugador2puntos;
    private TextView textViewjugador1;
    private TextView textViewjugador2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textViewjugador1=findViewById(R.id.text_view_p1);
        textViewjugador2=findViewById(R.id.text_view_p2);

        for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                String buttonID="button_" +i+j;
                int resID= getResources().getIdentifier(buttonID, "id",getPackageName());
                botonmatriz[i][j]=findViewById(resID);
                botonmatriz[i][j].setOnClickListener(this);
            }
        }
        Button  button_reset=findViewById(R.id.button_reset);


        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(!((Button) v).getText().toString().equals("")){
            return;
        }if(jugador1turno){
            ((Button) v).setText("X");
        }else{
            ((Button)v).setText("O");
        }
        ronda++;
        if(checkForwin()){
            if(jugador1turno){
                player1Wins();
            }else {
                player2Wins();
            }
        } else if(ronda==9){
            draw();
        }else{
            jugador1turno=!jugador1turno;
        }
    }
    private boolean checkForwin(){
        String[][] field =new String [3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                field[i][j]=botonmatriz[i][j].getText().toString();
            }
        }
        for(int i=0;i<3;i++){
            if(field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")){
                return true;
            }
        }
        for(int i=0;i<3;i++){
            if(field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")){
                return true;
            }
        }
        if(field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")){
            return true;
        }
        if(field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")){
            return true;
        }
        return false;
    }
    private void player1Wins() {
        jugador1puntos++;
        Toast.makeText(this,"GANASTE JUGADOR 1 MIJIN ",Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();

    }
    private void player2Wins() {
        jugador2puntos++;
        Toast.makeText(this,"GANASTE JUGADOR 2 MIJIN ",Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void draw() {
        Toast.makeText(this, " EMPATE QUE COSA !JUEGA VIVO !", Toast.LENGTH_SHORT).show();
        resetBoard();
    }
    private void updatePointsText() {
        textViewjugador1.setText(jugador1puntos+": ");
        textViewjugador2.setText(" :"+jugador2puntos);
    }
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botonmatriz[i][j].setText("");
            }
        }
        ronda = 0;
        jugador1turno = true;
    }
    private void resetGame(){
        jugador1puntos=0;
        jugador2puntos=0;
        updatePointsText();
        resetBoard();

    }

    @Override
    protected void onSaveInstanceState( Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ronda",ronda);
        outState.putInt("Jugador 1 Puntos",jugador1puntos);
        outState.putInt("Jugador 2 Puntos",jugador2puntos);
        outState.putBoolean("Turno jugador !", jugador1turno);
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ronda=savedInstanceState.getInt("ronda");
        jugador1puntos=savedInstanceState.getInt("Jugador 1 puntos");
        jugador2puntos=savedInstanceState.getInt("Jugador 2 puntos");
        jugador1turno=savedInstanceState.getBoolean("Jugador 1 Turno");

    }
}
