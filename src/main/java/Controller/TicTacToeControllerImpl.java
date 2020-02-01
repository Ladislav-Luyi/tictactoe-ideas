package Controller;

import model.GameModeTicTacToelImpl;
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

        view.showField( gameModel.getArray() );
//
//        if (LOGGER.isDebugEnabled())
//            view.showFieldDebug( gameModel.getArray() );
    }


    @Override
    public void runPlayerTurn() {

        view.showMessage(gameModel.getCurrentPlayerMessage() + gameModel.getActivePlayer() + "\n");
        view.showMessage(gameModel.getMessageForInput());

        String s;

        while(true){
            s = view.readInput();
            if (gameModel.isInputForPlayerValid(s)){
                break;
            }
            view.showMessage(gameModel.getErrorMessage());
        }

        char activePlayer = gameModel.getActivePlayer();

        gameModel.addInputToGame(s, activePlayer);
        gameModel.procesInputs();

    }


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

    @Override
    public void configureGame() {
        view.showMessage(gameModel.getConfigGridSizeMessage());

        String s;
        while (true) {
            s = view.readInput();
            if (gameModel.configGridSizeIsValid(s)){
                break;
            }
            view.showMessage(gameModel.getErrorMessage());
        }

        gameModel.setGridSize(Integer.valueOf(s));


        view.showMessage(gameModel.getWinningConditionMessage());

        while (true) {
            s = view.readInput();
            if (gameModel.winningConditionIsValid(s)){
                break;
            }

            view.showMessage(gameModel.getErrorMessage());
        }

        gameModel.setWinningCounter(Integer.valueOf(s));

    }


}
