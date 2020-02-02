package controller;



public interface TicTacToeController {
    void configureGame();
    void displayGame();
    void runPlayerTurn();
    void changePlayers();
    void displayGameStatusMessage();
    boolean getGameStatus();
}
