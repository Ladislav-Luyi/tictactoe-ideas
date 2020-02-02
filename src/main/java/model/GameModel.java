package model;

public interface GameModel {
    void addInputToGame(String s, char sign);
    void addInputToGame(int i, int j, char sign);
    void processInputs();
    void setWinningCounter(int winningCounter);
    void changePlayers();
    void setGridSize(int gridSize);

    char[][] getGrid();
    char getActivePlayer();

    boolean getGameStatus();
    boolean isConfigGridSizeValid(String s);
    boolean isWinningConditionValid(String s);
    boolean isInputForPlayerValid(String s);

    String getGameStatusMessage();
    String getConfigGridSizeMessage();
    String getWinningConditionMessage();
    String getCurrentPlayerMessage();
    String getMessageForInput();
    String getErrorMessage();

}
