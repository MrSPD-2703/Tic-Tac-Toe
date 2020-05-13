package com.example.tic_tac_toe;

import java.util.Random;

public class GameEngine {


    private char[] element ;
    Random RANDOM = new Random();
    private char currentPlayer;
    private boolean ended;
    public GameEngine() {
        element = new char[9];
        newGame();

    }

    void newGame() {
        for (int i=0 ; i < 9 ; i++){
            element[i] = ' ';
        }
        currentPlayer = 'X';
        ended = false;
    }

    public  boolean isEnded(){
        return ended;
    }
    public char play(int x , int y){
        if (element[3*y + x]== ' '){
            element[3*y + x] = currentPlayer;
            changePlayer();
        }

        return checkEnd();

    }

    public char getElement(int x , int y) {
        return element[3*y +x];
    }

    private char checkEnd() {
        for (int i =0 ; i <3 ; i++){

            if (getElement(i,0)!= ' '  &&
           getElement(i,0) == getElement(i ,1) && getElement(i ,1 ) == getElement(i ,2)
            ){
                return getElement(i,0);
            }
            if (getElement(0 ,i) != ' ' && getElement(0 ,i) == getElement(1,i ) &&
            getElement(1 ,i)== getElement(2 ,i)){
                return  getElement(0,i);
            }
        }

        if (getElement(0,0)!= ' ' && getElement(0,0) == getElement(1,1) &&
                getElement(1,1 ) == getElement(2,2)){
            return getElement(0,0);

        }
        if (getElement(0,2 )!= ' ' && getElement(0,2 )== getElement(1,1)
        && getElement(1,1) == getElement(2,0)){
            return getElement(2,0);
        }
        for (int  i = 0 ; i <9 ; i ++){
            if (element[i] == ' '){
                return ' ';
            }
        }

        return 'D';

    }

    public char computerPlay(){
        if (!ended){
            int position = -1;
            do {
                position = RANDOM.nextInt(9);
            }while (element[position] != ' ');

            element[position] = currentPlayer;
            changePlayer();
        }
        return  checkEnd();

    }

    private void changePlayer() {
        currentPlayer = (currentPlayer=='X'?'O':'X');
    }


}
