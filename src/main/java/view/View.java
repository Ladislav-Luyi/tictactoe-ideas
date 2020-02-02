package view;

public interface View {

    void showField(char[][] gameField);
    void showFieldDebug(char[][] gameField);
    void showMessage(String s);

    String readPlayerInput();
}
