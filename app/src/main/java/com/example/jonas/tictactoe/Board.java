package com.example.jonas.tictactoe;


import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private int numRows;
    private int numCols;
    public int mCurrentPlayer;
    public int[][] board;
    public boolean gameEnded;

    // Default constructor 4x4 grid
    Board(){
        this.numRows = 4;
        this.numCols = 4;
        board = new int[numRows][numCols];

        newGame();
    }

    // Custom grid constructor any MxN where M == N
    Board(int numRows, int numCols){
        this.numCols = numCols;
        this.numRows = numRows;
        board = new int[numRows][numCols];

        newGame();
    }

    // starts new game with clean board
    public void newGame(){
        for (int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                board[i][j] = -1;
            }
        }

        // set current player
        mCurrentPlayer = 1;
    }

    // game end flag
    public boolean didGameEnd(){
        return gameEnded;
    }

    // player moves
    public boolean play(int i, int j){
        // check cell is empty
        if(board[i][j] == -1 ){
            if(getCurrentPlayer() == 1){
                board[i][j] = 1;
                Log.e(Board.class.getSimpleName(), Arrays.deepToString(board));
                return true;
            } else{
                board[i][j] = 0;
                Log.e(Board.class.getSimpleName(), Arrays.deepToString(board));
                return true;
            }
        } else{
            Log.e(Board.class.getSimpleName(), Arrays.deepToString(board));
            return false;
        }
    }

    // Win checker
    public boolean checkWin(){

        if(checkRows()){
            gameEnded = true;
            return true;
        }

        if(checkColumns()){
            gameEnded = true;
            return true;
        }

        if(checkRightDiagonal()){
            gameEnded = true;
            return true;
        }

        if(checkLeftDiagonal()){
            gameEnded = true;
            return true;
        }

        if(checkFourCorners()){
            gameEnded = true;
            return true;
        }

        if(check2x2Box()){
            gameEnded = true;
            return true;
        }

        gameEnded = false;
        return false;
    }


    //=================================== Helper Methods ===================================
    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getCurrentPlayer() {
        return mCurrentPlayer;
    }

    public String getCurrentPlayerSymbol(){
        if(mCurrentPlayer == 1){
            return "X";
        } else{
            return "O";
        }
    }

    public void changeCurrentPlayer(){
        if(mCurrentPlayer == 1){
            mCurrentPlayer = 0;
        } else {
            mCurrentPlayer = 1;
        }
    }

    public boolean checkRightDiagonal(){
        // array to save right diagonal, populated by -1
        int[] rightDiagonal = new int[numRows];
        for(int i=0; i<numRows; i++){
            rightDiagonal[i] = -1;
        }

        // gets the right diagonal
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){

                if(i + j == numRows - 1){
                    rightDiagonal[i] = board[i][j];
                }
            }
        }

        // checks values in right diagonal
        for(int value : rightDiagonal){
            if(value != -1 && value == getCurrentPlayer()){
                continue;
            } else {
                return false;
            }
        }

        return true;
    }

    public boolean checkLeftDiagonal(){
        int[] leftDiagonal = new int[numRows];
        for(int i=0; i<numRows; i++){
            leftDiagonal[i] = -1;
        }

        // gets the left diagonal
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){

                if(i == j){
                    leftDiagonal[i] = board[i][j];
                }
            }
        }

        // check values in left diagonal
        for (int value : leftDiagonal){
            if(value != -1 && value == getCurrentPlayer()){
                continue;
            } else {
                return false;
            }
        }

        return true;
    }

    public boolean check2x2Box(){
        int checkCount = 0;
        int[] squaredMat = new int[numRows];
        for(int i=0; i<numRows; i++){
            squaredMat[i] = -1;
        }

        for(int i = 0; i < numRows-1; i++){
            for (int j = 0; j < numCols-1; j++){
                if(board[i][j] == getCurrentPlayer() && board[i+1][j] == getCurrentPlayer()
                        && board[i][j+1] == getCurrentPlayer() && board[1+i][j+1] == getCurrentPlayer()){

                    squaredMat[0] = board[i][j];
                    squaredMat[1] = board[i+1][j];
                    squaredMat[2] = board[i][j+1];
                    squaredMat[3] = board[i+1][j+1];
                }
            }
        }

        for(int k = 0; k < squaredMat.length; k++){
            if(squaredMat[k] != -1 && squaredMat[k] == getCurrentPlayer()){
                checkCount++;
            }
        }

        return checkCount == 4;

    }

    public boolean checkRows(){
        int checkCount;
        for(int i = 0; i < numRows; i++){
            checkCount = 0;
            // check each column
            for (int j = 0; j < numCols; j++){
                if(board[i][j] != -1 && board[i][j] == getCurrentPlayer()){
                    checkCount++;
                }
            }

            // check counter after each row
            if(checkCount == getNumRows()){
                return true;
            }

        }

        return false;
    }

    public boolean checkColumns(){
        int checkCount;

        for(int i = 0; i < numRows; i++){
            checkCount = 0;
            for(int j = 0; j < numCols; j++) {
                if(board[j][i] != -1 && board[j][i] == getCurrentPlayer()){
                    checkCount++;
                }
            }

            // check counter after each row
            if(checkCount == getNumRows()){
                return true;
            }
        }

        return false;
    }



    public boolean checkFourCorners(){
        ArrayList<Integer> corners = new ArrayList<>();
        int checkCount = 0;
        for(int i = 0; i < numRows; i++){
            // check first row
            if(i == 0){
                if(board[0][0] != -1 && board[0][0] == getCurrentPlayer() && // top-left corner element
                        board[0][numRows -1] != -1 && board[0][numRows - 1] == getCurrentPlayer()) {// top-right corner element
                    corners.add(board[0][0]);
                    corners.add(board[0][numRows - 1]);
                }
            }
            // check last row
            if(i == numRows -1){
                if(board[numRows - 1][0] != -1 && board[numRows -1][0] == getCurrentPlayer() && // bottom-left corner element
                        board[numRows - 1][numCols - 1] != -1 && board[numRows - 1][numCols - 1] == getCurrentPlayer()){ // bottom-right corner element
                    corners.add(board[numRows - 1][0]);
                    corners.add(board[numRows - 1][numRows - 1]);
                }
            }
        }

        for(int value : corners){
            if(value == getCurrentPlayer()){
                checkCount++;
            }
        }

        if(checkCount == 4){
            return true;
        } else{
            return false;
        }

    }

    public int[] getLeftDiagonal(){
        int[] leftDiagonal = new int[numRows];

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){

                if(i == j){
                    leftDiagonal[i] = board[i][j];
                }
            }
        }
        return leftDiagonal;
    }

    public int[] getRightDiagonal(){
        int[] rightDiagonal = new int[numRows];

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){

                if(i + j == numRows - 1){
                    rightDiagonal[i] = board[i][j];
                }
            }
        }

        return rightDiagonal;
    }



    public int[] get2x2Box(int currentPlayer){

        int[] squaredMat = new int[numRows];

        if(currentPlayer == 1){
            for(int i = 0; i < numRows-1; i++){
                for (int j = 0; j < numCols-1; j++){
                    if(board[i][j] == 1 && board[i+1][j] == 1
                            && board[i][j+1] == 1 && board[1+i][j+1] == 1){

                        squaredMat[0] = board[i][j];
                        squaredMat[1] = board[i+1][j];
                        squaredMat[2] = board[i][j+1];
                        squaredMat[3] = board[i+1][j+1];
                    }
                }
            }
        } else if(currentPlayer == 0){
            for(int i = 0; i < numRows-1; i++){
                for (int j = 0; j < numCols-1; j++){
                    if(board[i][j] == 0 && board[i+1][j] == 0
                            && board[i][j+1] == 0 && board[1+i][j+1] == 0){

                        squaredMat[0] = board[i][j];
                        squaredMat[1] = board[i+1][j];
                        squaredMat[2] = board[i][j+1];
                        squaredMat[3] = board[i+1][j+1];
                    }
                }
            }
        }

        return squaredMat;
    }
}
