import javax.swing.*;

public class Game {

    public static final int WIDTH = 480, HEIGHT = 480;
    public static final int GRID_SIZE = 8;
    public static final int MINE_COUNT = 10;

    public Game() {
        new Window(WIDTH, HEIGHT, GRID_SIZE, "Minesweeper", this);
    }

    public static void main(String[] args) {
        new Game();
    }

}
