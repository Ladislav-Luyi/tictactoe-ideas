package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GameModeTicTacToelImpl implements GameModel {

    private int gridSize;
    private char[][] array;
    private int winningCounter = 3;
    private Character ActivePlayer = 'x';
    private char Winner;
    private boolean isGameActive = true;
    private String currentErrorMessage;
    private int roundCounter = 0;
    private final static Logger LOGGER = LoggerFactory.getLogger(GameModeTicTacToelImpl.class);

    public void setWinningCounter(int winningCounter) {
        this.winningCounter = winningCounter;
    }

    private String gameStatusMessage = "Game is in progress";
    private String configGridSizeMessage = "Enter number for grid size in range of numbers 3 and 16: ";
    private String winningConditionMessage = "Enter number for winning condition in range of 3 and 5: ";
    private String messageForInput = "\tEnter input for row and column e.g. 1 1 (in range of numbers 1 - " + gridSize + "): ";

    public String getMessageForInput() {
        return messageForInput;
    }

    public String getCurrentPlayerMessage() {
        return currentPlayerMessage;
    }

    private String currentPlayerMessage = "Turn for player with sign: ";

    public String getWinningConditionMessage() {
        return winningConditionMessage;
    }

    public String getConfigGridSizeMessage() {
        return configGridSizeMessage;
    }

    @Override
    public boolean configGridSizeIsValid(String s) {

        if (!isNumberValid(s)) return false;

        int i = Integer.valueOf(s);

        if (isNumberInRange(i,3,16)) return false;

        return true;
    }

    private boolean isNumberInRange(int i, int min, int max) {
        if (i < min || i > max) {
            currentErrorMessage="\tValue is not in the range. Try again: ";
            return true;
        }
        return false;
    }

    private boolean isNumberValid(String s) {
        String patternString = "^[0-9]+$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher( s );
        if (!matcher.find()) {
            currentErrorMessage="\tValue is not a number. Try again: ";
            return false;
        }
        return true;
    }

    @Override
    public boolean winningConditionIsValid(String s) {
        if (!isNumberValid(s)) return false;

        int i = Integer.valueOf(s);

        if (isNumberInRange(i,3,5)) return false;

        if (i > gridSize){
            currentErrorMessage = "\tValue for winning condition cannot be higher than value of grid size. Try again: ";
            return false;
        }

        return true;
    }

    @Override
    public String getErrorMessage() {
        return currentErrorMessage;
    }

    @Override
    public void changePlayers() {
        if (ActivePlayer == 'x')
            ActivePlayer = 'o';
        else
            ActivePlayer = 'x';
    }

    @Override
    public char getActivePlayer() {
        return ActivePlayer;
    }


    @Override
    public String getGameStatusMessage() {
        return gameStatusMessage;
    }

    @Override
    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;

        array = new char[gridSize][gridSize];
        for(char[] row :array)
            Arrays.fill(row,'■');
    }



    public boolean isInputForPlayerValid(String s) {

        int[] i = new int[2];
        String patternString = "^[0-9]+\\ [0-9]+$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher( s );

        if (! matcher.find()) {
            currentErrorMessage = "\t\tIncorrect format \"" + s +"\". Format should be \"line row\" e.g. 1 1. Try again: ";
            return false;
        }

        int cloopCounter = 0;
        for (String tmpS : s.split(" ")) {
            i[cloopCounter++] = Integer.valueOf(tmpS);
        }

        for (int tmpI : i) {
            if (tmpI < 1 || tmpI > array.length) {
                currentErrorMessage = "\t\tInput \"" + i[0] + " " + i[1]+ "\" is not in the range. Try again: ";
                return false;
            }
        }

        if (!isFieldFree(i[0]-1,i[1]-1)){
            currentErrorMessage = "\t\tField is already taken. Try again: ";
            return false;
        }

        return true;
    }

    private boolean isFieldFree(int i, int j){
        if (array[i][j] == 'x' || array[i][j] == 'o')
            return false;

        return true;
    }

    // == method for debugging purposses ==
    public void addInputToGame(int i, int j, char sign){
        array[ i ] [ j ] = sign;
    }

    @Override
    public void addInputToGame(String s, char sign) {
        int[] i = new int[2];

        int cloopCounter = 0;
        for (String tmpS : s.split(" ")) {
            i[cloopCounter++] = Integer.valueOf(tmpS);
        }

        array[ i[0] - 1 ][ i[1] - 1 ] = sign;
        roundCounter++;
    }

    public char[][] getArray() {
        return array;
    }

    @Override
    public boolean getGameStatus() {
        LOGGER.debug("gameStatus: " + isGameActive);
        return isGameActive;
    }

    @Override
    public void procesInputs() {
        outerloop:
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[i].length; j++){
                //

                if ( checkAdjutedFields(i,j,ActivePlayer,0) ) {
                    LOGGER.debug("Match for winning contion.");
                    Winner = ActivePlayer;
                    isGameActive = false;
                    gameStatusMessage = ActivePlayer + " won!";
                    break outerloop;
                }

                //make validation for tie
                if (roundCounter == gridSize*gridSize){
                    isGameActive = false;
                    gameStatusMessage = "Tie!";
                    break outerloop;
                }

            }
        }
    }



    // == private methods ==
    private boolean checkAdjutedFields(int i, int j, char sign, int counter) {
        LOGGER.debug("Start of checking fields for: " + i + " " + j);

        if (array[i][j] != sign )
            return false;

        // == down == i+1 j
        if( checkAdjutedFieldsDown(i+1,j,sign,counter) ) return true;

        // == right == i j+1
        if( checkAdjutedFieldsRight(i,j+1,sign,counter) ) return true;

        // == left diagonal down == i+1 j-1
        if( checkAdjutedLeftDiagonalDown(i+1, j-1,sign,counter)  ) return true;

        // == right diagonal down == i+1 j+1
        if( checkAdjutedFieldRightDiagonalDown(i+1,j+1,sign,counter) ) return true;

        // == left diagonal up == i-1 j-1
        if(               checkAdjutedFieldLeftDiagonalUp(i-1,j-1,sign,counter)  ) return true;

        // == right diagonal up == i-1 j+1
        if(               checkAdjutedFieldsRightDiagonalUp(i-1,j+1,sign,counter) ) return true;

        return false;


    }

    private boolean validateInput(int i) {

        if (i < 0 || i >= array.length)
            return false;

        return true;
    }

    // == down == i+1 j
    private boolean checkAdjutedFieldsDown(int i, int j, char sign, int counter){
        counter++;

        LOGGER.debug("checkAdjutedFieldsDown"+ " " + counter + " match");

        if (counter == winningCounter)
            return true;

        if (!validateInput(i)||
                !validateInput(j)
        )
            return false;


        if (array[i][j] == sign)
            return checkAdjutedFieldsDown(i + 1,j,sign,counter);

        return false;
    }


    // == right == i j+1
    private boolean checkAdjutedFieldsRight(int i, int j, char sign, int counter){
        counter++;

        LOGGER.debug("checkAdjutedFieldsRight" + " " + counter + " match");

        if (counter == winningCounter)
            return true;


        if (!validateInput(i)||
                !validateInput(j)
        )
            return false;

        if (array[i][j] == sign)
            return checkAdjutedFieldsRight(i,j+1,sign,counter);

        return false;
    }


    // == left diagonal down == i+1 j-1
    private boolean checkAdjutedLeftDiagonalDown(int i, int j, char sign, int counter){
        counter++;

        LOGGER.debug("checkAdjutedLeftDiagonalDown"+ " " + counter + " match");

        if (counter == winningCounter)
            return true;

        if (!validateInput(i)||
                !validateInput(j)
        )
            return false;


        if (array[i][j] == sign)
            return checkAdjutedLeftDiagonalDown(i+1, j-1,sign,counter);

        return false;
    }


    // == right diagonal down == i+1 j+1
    private boolean checkAdjutedFieldRightDiagonalDown(int i, int j, char sign, int counter){
        counter++;

        LOGGER.debug("checkAdjutedFieldRightDiagonalDown"+ " " + counter + " match");

        if (counter == winningCounter)
            return true;

        if (!validateInput(i)||
                !validateInput(j)
        )
            return false;



        if (array[i][j] == sign)
            return checkAdjutedFieldRightDiagonalDown(i+1,j+1,sign,counter);

        return false;
    }

    private boolean checkAdjutedFieldLeftDiagonalUp(int i, int j, char sign, int counter){
        counter++;

        LOGGER.debug("checkAdjutedFieldLeftDiagonalUp"+ " " + counter + " match");

        if (counter == winningCounter)
            return true;

        if (!validateInput(i)||
                !validateInput(j)
        )
            return false;


        if (array[i][j] == sign)
            return checkAdjutedFieldLeftDiagonalUp(i-1, j-1, sign, counter);

        return false;
    }

    // == right diagonal up == i-1 j+1
    private boolean checkAdjutedFieldsRightDiagonalUp(int i, int j, char sign, int counter){
        counter++;

        LOGGER.debug("checkAdjutedFieldsRightDiagonalUp"+ " " + counter + " match");

        if (counter == winningCounter)
            return true;

        if (!validateInput(i)||
                !validateInput(j)
        )
            return false;



        if (array[i][j] == sign)
            return checkAdjutedFieldsRightDiagonalUp(i-1,j+1,sign,counter);

        return false;
    }




}