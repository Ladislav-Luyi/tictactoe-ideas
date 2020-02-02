package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GameModelTicTacToeImpl implements GameModel {

    private final static Logger LOGGER = LoggerFactory.getLogger(GameModelTicTacToeImpl.class);
    private char[][] grid;
    private int gridSize;

    private int winningCounter = 3;
    private Character ActivePlayer = 'x';
    private boolean isGameActive = true;
    private String currentErrorMessage;
    private int roundCounter = 0;


    private final String configGridSizeMessage = "Enter number for grid size in range of numbers 3 and 16: ";
    private final String winningConditionMessage = "Enter number for winning condition in range of 3 and 5: ";
    private final String currentPlayerMessage = "Turn for player with sign: ";

    private String messageForInput = "\tEnter input for row and column e.g. 1 1. In range of numbers 1 - ";
    private String gameStatusMessage = "Game is in progress";


    @Override
    public String getMessageForInput() {
        return messageForInput + gridSize + ": ";
    }

    @Override
    public String getCurrentPlayerMessage() {
        return currentPlayerMessage + ActivePlayer + "\n";
    }

    @Override
    public String getWinningConditionMessage() {
        return winningConditionMessage;
    }

    @Override
    public String getConfigGridSizeMessage() {
        return configGridSizeMessage;
    }

    @Override
    public String getErrorMessage() { return currentErrorMessage; }

    @Override
    public char getActivePlayer() { return ActivePlayer; }

    @Override
    public String getGameStatusMessage() { return gameStatusMessage; }

    @Override
    public char[][] getGrid() {
        return grid;
    }

    @Override
    public boolean getGameStatus() {
        LOGGER.debug("gameStatus: " + isGameActive);
        return isGameActive;
    }

    @Override
    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;

        grid = new char[gridSize][gridSize];
        for(char[] row : grid)
            Arrays.fill(row, '\u25A0');
    }

    @Override
    public void setWinningCounter(int winningCounter) {
        this.winningCounter = winningCounter;
    }

    /**
     * Method for validating input for grid size
     * @param s
     * @return
     */
    @Override
    public boolean isConfigGridSizeValid(String s) {

        if (!isNumberValid(s)) return false;

        int i = Integer.valueOf(s);

        if (isNumberInRange(i,3,16)) return false;

        return true;
    }

    /**
     * Method for validating condition for win. Number of consecutive tiles marked by player
     * @param s
     * @return
     */
    @Override
    public boolean isWinningConditionValid(String s) {
        if (!isNumberValid(s)) return false;

        int i = Integer.valueOf(s);

        if (isNumberInRange(i,3,5)) return false;

        if (i > gridSize){
            currentErrorMessage = "\tValue for winning condition cannot be higher than value of grid size. Try again: ";
            return false;
        }

        return true;
    }

    /**
     * Method for validating input from player. It validates whether it is a number, inputs format and inputs range
     * @param s
     * @return
     */
    public boolean isInputForPlayerValid(String s) {
        int[] i = new int[2];

        String patternString = "^[0-9]+\\ [0-9]+$";

        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher( s );

        if (! matcher.find()) {
            this.currentErrorMessage = "\t\tIncorrect format \"" + s + "\". Format should be \"line row\" e.g. 1 1. Try again: ";
            return false;
        }

        int cloopCounter = 0;
        for (String tmpS : s.split(" ")) {
            i[cloopCounter++] = Integer.valueOf(tmpS);
        }

        for (int tmpI : i) {
            if (tmpI < 1 || tmpI > grid.length) {
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

    /**
     * Method for manually changing the order for players
     */
    @Override
    public void changePlayers() {
        if (ActivePlayer == 'x')
            ActivePlayer = 'o';
        else
            ActivePlayer = 'x';
    }

    /**
     * Method takes input to the game. Which is then used as cross or circle in the game grid
     * @param s
     * @param sign
     */
    @Override
    public void addInputToGame(String s, char sign) {
        int[] i = new int[2];

        int cloopCounter = 0;
        for (String tmpS : s.split(" ")) {
            i[cloopCounter++] = Integer.valueOf(tmpS);
        }

        grid[ i[0] - 1 ][ i[1] - 1 ] = sign;
        roundCounter++;
    }


    @Override
    /**
     * Game main logic is validated. This method validates whether there is a winner or it is tie.
     */
    public void processInputs() {
        outerloop:
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                //

                if ( checkAdjutedFields(i,j,ActivePlayer,0) ) {
                    LOGGER.debug("Match for winning contion.");
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


    /**
     * Method takes input to the game. Which is then used as cross or circle in the game grid. Only for debugging purposes!
     * @param i row
     * @param j column
     * @param sign
     */
    // == method for debugging purposses ==
    public void addInputToGame(int i, int j, char sign){
        grid[ i ] [ j ] = sign;
    }

    // == private methods ==

    private boolean isFieldFree(int i, int j){
        if (grid[i][j] == 'x' || grid[i][j] == 'o')
            return false;

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

    /**
     * Method checks whether inputs from recursive calls in methods for checking horizontal, vertical and diagonals directions for match are in range
     * @param i
     * @return
     */
    private boolean validateInput(int i) {

        if (i < 0 || i >= grid.length)
            return false;

        return true;
    }

    // == main logic ===

    /**
     * Wrapper method for calling methods to check horizontal, vertical and diagonals directions for match
     * @param i
     * @param j
     * @param sign
     * @param counter
     * @return
     */

    private boolean checkAdjutedFields(int i, int j, char sign, int counter) {
        LOGGER.debug("Start of checking fields for: " + i + " " + j);

        if (grid[i][j] != sign )
            return false;

        // == down == i+1 j
        if( checkAdjutedFieldsVertical(i+1,j,sign,counter) ) return true;

        // == right == i j+1
        if( checkAdjutedFieldsHorizontal(i,j+1,sign,counter) ) return true;

        // == left diagonal down == i+1 j-1
        if( checkAdjutedLeftDiagonal(i+1, j-1,sign,counter)  ) return true;

        // == right diagonal down == i+1 j+1
        if( checkAdjutedFieldRightDiagonal(i+1,j+1,sign,counter) ) return true;

        return false;
    }

    // == down == i+1 j
    private boolean checkAdjutedFieldsVertical(int i, int j, char sign, int counter){
        counter++;

        LOGGER.debug("checkAdjutedFieldsDown"+ " " + counter + " match");

        if (counter == winningCounter)
            return true;

        if (!validateInput(i)||
                !validateInput(j)
        )
            return false;

        if (grid[i][j] == sign)
            return checkAdjutedFieldsVertical(i + 1,j,sign,counter);

        return false;
    }


    // == right == i j+1
    private boolean checkAdjutedFieldsHorizontal(int i, int j, char sign, int counter){
        counter++;

        LOGGER.debug("checkAdjutedFieldsRight" + " " + counter + " match");

        if (counter == winningCounter)
            return true;

        if (!validateInput(i)||
                !validateInput(j)
        )
            return false;

        if (grid[i][j] == sign)
            return checkAdjutedFieldsHorizontal(i,j+1,sign,counter);

        return false;
    }


    // == left diagonal down == i+1 j-1
    private boolean checkAdjutedLeftDiagonal(int i, int j, char sign, int counter){
        counter++;

        LOGGER.debug("checkAdjutedLeftDiagonalDown"+ " " + counter + " match");

        if (counter == winningCounter)
            return true;

        if (!validateInput(i)||
                !validateInput(j)
        )
            return false;

        if (grid[i][j] == sign)
            return checkAdjutedLeftDiagonal(i+1, j-1,sign,counter);

        return false;
    }


    // == right diagonal down == i+1 j+1
    private boolean checkAdjutedFieldRightDiagonal(int i, int j, char sign, int counter){
        counter++;

        LOGGER.debug("checkAdjutedFieldRightDiagonalDown"+ " " + counter + " match");

        if (counter == winningCounter)
            return true;

        if (!validateInput(i)||
                !validateInput(j)
        )
            return false;

        if (grid[i][j] == sign)
            return checkAdjutedFieldRightDiagonal(i+1,j+1,sign,counter);

        return false;
    }
}