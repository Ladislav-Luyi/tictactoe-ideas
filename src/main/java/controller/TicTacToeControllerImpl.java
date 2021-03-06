package controller;

import model.GameModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.View;

public class TicTacToeControllerImpl implements TicTacToeController {
    private final static Logger LOGGER = LoggerFactory.getLogger(TicTacToeController.class);
    private final GameModel gameModel;
    private final View view;

    public TicTacToeControllerImpl(GameModel gameModel, View view) {
        this.gameModel = gameModel;
        this.view = view;
    }

    public void displayGame(){

        view.showField( gameModel.getGrid() );
//
//        if (LOGGER.isDebugEnabled())
//            view.showFieldDebug( gameModel.getArray() );
    }

    /**
     * Method encapsulates game logic for one turn in the game by reading input from player and processing it by calling appropriate controller and view methods
     */
    @Override
    public void runPlayerTurn() {

        view.showMessage(gameModel.getCurrentPlayerMessage());
        view.showMessage(gameModel.getMessageForInput());

        String s;

        while(true){
            s = view.readPlayerInput();
            if (gameModel.isInputForPlayerValid(s)){
                break;
            }
            view.showMessage(gameModel.getErrorMessage());
        }

        char activePlayer = gameModel.getActivePlayer();

        gameModel.addInputToGame(s, activePlayer);
        gameModel.processInputs();

    }


    /**
     * Controller method for calling models method for changing players
     */
    @Override
    public void changePlayers() {
        gameModel.changePlayers();
    }

    @Override
    public boolean getGameStatus() {
        return gameModel.getGameStatus();
    }

    @Override
    public void displayGameStatusMessage() {
        view.showMessage(gameModel.getGameStatusMessage());
    }

    /**
     * Method encapsulates game logic for configuring the game - grid size, winning conditions - by reading input from a player and processing it by calling appropriate controller and view methods
     */
    @Override
    public void configureGame() {
        view.showMessage(gameModel.getConfigGridSizeMessage());

        String s;
        while (true) {
            s = view.readPlayerInput();
            if (gameModel.isConfigGridSizeValid(s)){
                break;
            }
            view.showMessage(gameModel.getErrorMessage());
        }

        gameModel.setGridSize(Integer.valueOf(s));


        view.showMessage(gameModel.getWinningConditionMessage());

        while (true) {
            s = view.readPlayerInput();
            if (gameModel.isWinningConditionValid(s)){
                break;
            }

            view.showMessage(gameModel.getErrorMessage());
        }

        gameModel.setWinningCounter(Integer.valueOf(s));

    }


}
