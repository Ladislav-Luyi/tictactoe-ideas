import Controller.TicTacToeController;
import Controller.TicTacToeControllerImpl;
import model.GameModel;
import model.GameModeTicTacToelImpl;
import view.ConsoleViewImpl;
import view.View;

public class TicTacToeRunner {
    public static void main(String[] args) {
        GameModel gameModel = new GameModeTicTacToelImpl();
        View view = new ConsoleViewImpl();

        TicTacToeController ticTacToeController = new TicTacToeControllerImpl(gameModel, view);
        ticTacToeController.configureGame();

        // == start testing for grid 16 ==

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

        while (ticTacToeController.getGameStatus() == true){
            ticTacToeController.displayGame();

            ticTacToeController.runPlayerTurn();

            ticTacToeController.changePlayers();
        }

        System.out.println();

        ticTacToeController.displayGameStatusMessage();

        ticTacToeController.displayGame();

    }
}
