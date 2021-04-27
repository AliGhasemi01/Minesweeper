import javax.swing.*;
import java.awt.*;

public class Window {

    private static JFrame gameFrame;

    public Window(int width, int height, int gridSize, String title, Game game){

        gameFrame = new JFrame(title);

        gameFrame.setPreferredSize(new Dimension(width, height));
        gameFrame.setMinimumSize(new Dimension(width, height));
        gameFrame.setMaximumSize(new Dimension(width, height));
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);

        JPanel panel = new Grid(new GridLayout(gridSize, gridSize));

        gameFrame.setContentPane(panel);
        gameFrame.pack();

        gameFrame.setVisible(true);

    }


}
