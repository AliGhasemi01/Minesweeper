import javax.swing.*;
import java.awt.*;

public class Game {

    public static final int WIDTH = 560, HEIGHT = 600;
    public static int grid_size;
    public static int mine_count;

    final JMenuBar menuBar = new JMenuBar();
    private static JLabel statusbar;

    Game(int gridSize, String title) {

        JFrame gameFrame = new JFrame(title);
        gameFrame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        gameFrame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        gameFrame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setResizable(false);

        // status bar
        statusbar = new JLabel("");
        gameFrame.add(statusbar, BorderLayout.SOUTH);

        // game panel
        JPanel gamePanel = new Grid(new GridLayout(gridSize, gridSize));
        gameFrame.add(gamePanel, BorderLayout.CENTER);

        gameFrame.pack();
        gameFrame.setVisible(true);

    }

    public static JLabel getStatusbar() {
        return statusbar;
    }

    public static void main(String[] args) {

        //new Game(GRID_SIZE, "Minesweeper");

        int choice = JOptionPane.showOptionDialog(null,
                "Select Difficulty",
                "Game Difficulty",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, //Icon icon,
                new String[]{"Easy", "Medium", "Hard"},
                "Medium");

        if (choice == 0) {
            grid_size = 8;
            mine_count = 10;
            new Game(grid_size, "Minesweeper");
        } else if (choice == 1) {
            grid_size = 12;
            mine_count = 20;
            new Game(grid_size, "Minesweeper");
        } else if (choice == 2) {
            grid_size = 16;
            mine_count = 40;
            new Game(grid_size, "Minesweeper");
        }
    }
}
