package model;

public interface GameModel {
    void addInputToGame(String s, char sign);
    void addInputToGame(int i, int j, char sign);
    void procesInputs();
    void setWinningCounter(int winningCounter);
    void changePlayers();
    void setGridSize(int gridSize);

    char[][] getArray();
    char getActivePlayer();

    boolean getGameStatus();
    boolean configGridSizeIsValid(String s);
    boolean winningConditionIsValid(String s);
    boolean isInputForPlayerValid(String s);

    String getGameStatusMessage();
    String getConfigGridSizeMessage();
    String getWinningConditionMessage();
    String getCurrentPlayerMessage();
    String getMessageForInput();
    String getErrorMessage();

}
