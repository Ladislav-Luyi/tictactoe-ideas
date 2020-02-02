import controller.TicTacToeController;
import controller.TicTacToeControllerImpl;
import model.GameModel;
import model.GameModelTicTacToeImpl;
import view.ConsoleViewImpl;
import view.View;


public class TicTacToeRunner {
    public static void main(String[] args) {
        // == setting up MVC pattern ==
        GameModel gameModel = new GameModelTicTacToeImpl();
        View view = new ConsoleViewImpl();
        TicTacToeController ticTacToeController = new TicTacToeControllerImpl(gameModel, view);

        ticTacToeController.configureGame();

        // == start testing for grid 16 - uncomment method from below ==
        //setUpPredefinedInputs(gameModel);

        while (ticTacToeController.getGameStatus() == true){
            ticTacToeController.displayGame();

            ticTacToeController.runPlayerTurn();

            ticTacToeController.changePlayers();
        }

        System.out.println();

        ticTacToeController.displayGameStatusMessage();

        ticTacToeController.displayGame();

    }

    /**
     * Method for debugging purposes to add predefined inputs before the game starts.
     * @param gameModel
     */
    private static void setUpPredefinedInputs(GameModel gameModel) {
        // == horizontal UP ==
        gameModel.addInputToGame(0,0,'x');
        gameModel.addInputToGame(0,1,'x');

// == vertical ==
        gameModel.addInputToGame(1,5,'x');
        gameModel.addInputToGame(2,5,'x');
        gameModel.addInputToGame(15,14,'x');

// == diagonal
        gameModel.addInputToGame(15,15,'x');
        gameModel.addInputToGame(14,14,'x');

        gameModel.addInputToGame(15,0,'x');
        gameModel.addInputToGame(14,1,'x');


        // == stop testing ==
    }
}
