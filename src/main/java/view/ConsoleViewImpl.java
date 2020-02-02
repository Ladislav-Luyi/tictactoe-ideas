package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleViewImpl implements View {

    @Override
    public void showField(char[][] gameGrid) {

        String margin = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t";

        System.out.println();
        // == grid adjustement in case it is under number 10 ==
        if (gameGrid.length <= 9) {
            for (int i = 0; i < gameGrid.length; i++) {
                if (i == 0)
                    System.out.print(margin + "   " + 1 + " ");
                else
                    System.out.print( i + 1 + " ");

            }
            System.out.println();
        }

        // == rendering regular grid ==
        for(int i = 0; i < gameGrid.length; i++){
            for(int j = 0; j < gameGrid[i].length; j++){
                if (j==0)
                    System.out.print(margin +  customIntegerFormatter(i + 1,"left") + " " + gameGrid[i][j]+" ");
                else if (j==gameGrid.length-1)
                    System.out.print( gameGrid[i][j]+ " " + customIntegerFormatter(i + 1,"right") );
                else
                    System.out.print( gameGrid[i][j]+" ");
            }
            System.out.println();
        }

        // == grid adjustement in case it is under number 10 ==
        if (gameGrid.length <= 9) {
            for (int i = 0; i < gameGrid.length; i++) {
                if (i == 0)
                    System.out.print(margin + "   " + 1 + " ");
                else
                    System.out.print( i + 1 + " ");
            }
            System.out.println();
        }

        System.out.println();
    }

    @Override
    public void showFieldDebug(char[][] gameGrid) {

        for(int i = 0; i < gameGrid.length; i++){
            for(int j = 0; j < gameGrid[i].length; j++){
                System.out.print("j=" + j + " ");
            }
            System.out.println("i=" + i);
        }
    }

    @Override
    public void showMessage(String s) {
        System.out.print(s);
    }

    @Override
    public String readPlayerInput() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String s = null;

        try {
            s = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    private String customIntegerFormatter(int i, String side){
        if (i > 9)
            return String.valueOf(i);

        if (side.equals("left"))
            return " " + String.valueOf(i);
        else
            return String.valueOf(i);
    }

}
