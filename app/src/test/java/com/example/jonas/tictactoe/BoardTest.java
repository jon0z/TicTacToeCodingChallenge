package com.example.jonas.tictactoe;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Test Board class - This class is used to test the helper methods for array manipulation
 */
public class BoardTest {

    private boolean defaultSizeBoard = true;
    private int defaultNumRows = 4;
    private int defaultNumCols = 4;
    private int presetNumRows = 5;
    private int presetNumCols = 5;
    private Board testBoard;
    private int[] winningCombination;
    private int[][] boxWinninMat;


    @Before
    public void setUp(){
        if(defaultSizeBoard){
            testBoard = new Board();
            winningCombination = new int[defaultNumRows];
        }
        else{
            testBoard = new Board(presetNumRows, presetNumCols);
            winningCombination = new int[presetNumRows];
        }

        // winning combination
        for(int i = 0; i< winningCombination.length; i++){
            winningCombination[i] = 1;
        }
    }

    @After
    public void tearDown(){
        testBoard = null;
        winningCombination = null;
    }

    @Test
    public void testBoardConstructor(){
        assertNotNull(testBoard);
    }

    @Test
    public void testGetters(){
        if(defaultSizeBoard){
            int defaultRows = testBoard.getNumRows();
            int defaultCols = testBoard.getNumCols();

            assertEquals(defaultNumRows, defaultRows);
            System.out.println("Default number of rows = " +defaultRows);
            assertEquals(defaultNumCols, defaultCols);
            System.out.println("Default number of rows = " +defaultRows);
        }
        else{
            int defaultRows = testBoard.getNumRows();
            int defaultCols = testBoard.getNumCols();

            assertEquals(presetNumRows, defaultRows);
            System.out.println("preset number of rows = " +defaultRows);
            assertEquals(presetNumCols, defaultCols);
            System.out.println("preset number of cols = "+defaultCols);
        }
    }

    @Test
    public void testNewGame(){
        // creates a new game
        testBoard.newGame();
        // board with empty char at each element
        assertNotNull(testBoard.board);
        // set the current player
        assertEquals("x", testBoard.getCurrentPlayer());
    }

    @Test
    public void testGetRightDiagonal(){
        createRightDiagonalTestMat();
        for(int i=0; i<testBoard.board.length; i++){
            System.out.println(Arrays.toString(testBoard.board[i]));
        }

        System.out.println(" ");

        int[] actualRightDiagonal = testBoard.getRightDiagonal();
        System.out.println(Arrays.toString(actualRightDiagonal));

        for (int actualVal : actualRightDiagonal) {
            assertEquals(1, actualVal);
        }
    }

    @Test
    public void testCheckRightDiagonal(){
        createRightDiagonalTestMat();
        for(int i=0; i<testBoard.board.length; i++){
            System.out.println(Arrays.toString(testBoard.board[i]));
        }

        assertTrue(testBoard.checkRightDiagonal());
    }

    @Test
    public void testGetLeftDiagonla(){
        createLeftDiagonalTestMat();
        for(int i = 0; i < testBoard.board.length; i++){
            System.out.println(Arrays.toString(testBoard.board[i]));
        }

        System.out.println(" ");

        int[] actualLeftDiagonal = testBoard.getLeftDiagonal();
        System.out.println(Arrays.toString(actualLeftDiagonal));

        for(int actualVal : actualLeftDiagonal){
            assertEquals(1, actualVal);
        }
    }

    @Test
    public void testGet2x2Box(){
        create2x2BoxTestMat(3);
        for(int i = 0; i < testBoard.board.length; i++){
            System.out.println(Arrays.toString(testBoard.board[i]));
        }

        System.out.println(" ");

        int[] outputMat = testBoard.get2x2Box(0);
        System.out.println(Arrays.toString(outputMat));

        for (int actualVal : outputMat){
            assertEquals(0, actualVal);
        }
    }

    @Test
    public void testCheckFourCorners(){
        createFourCornersMat();
        System.out.println(Arrays.toString(testBoard.board));
        boolean fourCornerWin = testBoard.checkFourCorners();

        assertTrue(fourCornerWin);

    }


    // helper functions
    private void createFourCornersMat(){
        for(int i = 0; i < testBoard.board.length; i++){
            for(int j = 0; j < testBoard.board.length; j++){
                if(i == 0){
                    testBoard.board[0][0] = 1;
                    testBoard.board[0][testBoard.getNumRows() - 1] = 1;
                }else if(i == testBoard.getNumRows() - 1){
                    testBoard.board[testBoard.getNumRows() - 1][0] = 1;
                    testBoard.board[testBoard.getNumRows() - 1][testBoard.getNumCols() - 1] = 1;
                }


            }
        }
    }
    private void createLeftDiagonalTestMat(){
        for(int i = 0; i < testBoard.board.length; i++){
            for(int j=0; j < testBoard.board[i].length; j++){
                if(i==j){
                    testBoard.board[i][j] = 1;
                } else{
                    testBoard.board[i][j] = 0;
                }
            }
        }
    }

    private void createRightDiagonalTestMat(){
        for(int i = 0; i < testBoard.board.length; i++){
            for (int j = 0; j < testBoard.board[i].length; j++){
                if(i + j == testBoard.board.length - 1){
                    testBoard.board[i][j] = 1;
                }else{
                    testBoard.board[i][j] = 0;
                }
            }
        }
    }

    private void create2x2BoxTestMat(int variation){

        switch (variation){
            case 0:
                testBoard.board[0][0] = 1;
                testBoard.board[0][1] = 1;
                testBoard.board[0][2] = 0;
                testBoard.board[0][3] = 0;
                testBoard.board[1][0] = 1;
                testBoard.board[1][1] = 1;
                testBoard.board[1][2] = 0;
                testBoard.board[1][3] = 0;
                testBoard.board[2][0] = 0;
                testBoard.board[2][1] = 0;
                testBoard.board[2][2] = 0;
                testBoard.board[2][3] = 0;
                testBoard.board[3][0] = 0;
                testBoard.board[3][1] = 0;
                testBoard.board[3][2] = 0;
                testBoard.board[3][3] = 0;
                break;
            case 1:
                testBoard.board[0][0] = 0;
                testBoard.board[0][1] = 0;
                testBoard.board[0][2] = 0;
                testBoard.board[0][3] = 0;
                testBoard.board[1][0] = 0;
                testBoard.board[1][1] = 1;
                testBoard.board[1][2] = 1;
                testBoard.board[1][3] = 0;
                testBoard.board[2][0] = 0;
                testBoard.board[2][1] = 1;
                testBoard.board[2][2] = 1;
                testBoard.board[2][3] = 0;
                testBoard.board[3][0] = 0;
                testBoard.board[3][1] = 0;
                testBoard.board[3][2] = 0;
                testBoard.board[3][3] = 0;
                break;
            case 2:
                testBoard.board[0][0] = 0;
                testBoard.board[0][1] = 0;
                testBoard.board[0][2] = 0;
                testBoard.board[0][3] = 0;
                testBoard.board[1][0] = 0;
                testBoard.board[1][1] = 0;
                testBoard.board[1][2] = 0;
                testBoard.board[1][3] = 0;
                testBoard.board[2][0] = 0;
                testBoard.board[2][1] = 0;
                testBoard.board[2][2] = 1;
                testBoard.board[2][3] = 1;
                testBoard.board[3][0] = 0;
                testBoard.board[3][1] = 0;
                testBoard.board[3][2] = 1;
                testBoard.board[3][3] = 1;
                break;
            case 3:
                testBoard.board[0][0] = 1;
                testBoard.board[0][1] = 1;
                testBoard.board[0][2] = 1;
                testBoard.board[0][3] = 1;
                testBoard.board[1][0] = 1;
                testBoard.board[1][1] = 1;
                testBoard.board[1][2] = 1;
                testBoard.board[1][3] = 1;
                testBoard.board[2][0] = 1;
                testBoard.board[2][1] = 1;
                testBoard.board[2][2] = 0;
                testBoard.board[2][3] = 0;
                testBoard.board[3][0] = 1;
                testBoard.board[3][1] = 1;
                testBoard.board[3][2] = 0;
                testBoard.board[3][3] = 0;
                break;
            case 4:
                testBoard.board[0][0] = 1;
                testBoard.board[0][1] = 1;
                testBoard.board[0][2] = 1;
                testBoard.board[0][3] = 1;
                testBoard.board[1][0] = 1;
                testBoard.board[1][1] = 0;
                testBoard.board[1][2] = 0;
                testBoard.board[1][3] = 1;
                testBoard.board[2][0] = 1;
                testBoard.board[2][1] = 0;
                testBoard.board[2][2] = 0;
                testBoard.board[2][3] = 1;
                testBoard.board[3][0] = 1;
                testBoard.board[3][1] = 1;
                testBoard.board[3][2] = 1;
                testBoard.board[3][3] = 1;
                break;
        }

    }
}