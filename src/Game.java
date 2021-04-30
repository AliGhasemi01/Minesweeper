import javax.swing.*;
import java.util.ArrayList;

public class Game {

    public static final int WIDTH = 400, HEIGHT = 500;
    public static final int GRID_SIZE = 8;
    public static final int MINE_COUNT = 10;

    public Game() {
        new Window(WIDTH, HEIGHT, GRID_SIZE, "Minesweeper");
    }

    public static void main(String[] args) {
        new Game();
    }

}
