import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Window {

    private static JFrame gameFrame;
    private JLabel statusbar;
    private JPanel upperField;

    public Window(int width, int height, int gridSize, String title){

        gameFrame = new JFrame(title);


        gameFrame.setPreferredSize(new Dimension(width, height));
        gameFrame.setMinimumSize(new Dimension(width, height));
        gameFrame.setMaximumSize(new Dimension(width, height));
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setResizable(false);

        setUpJFrame(gameFrame, width, height, gridSize);
        //gameFrame.setContentPane(gamePanel);
        gameFrame.pack();

        gameFrame.setVisible(true);

    }

    private void setUpJFrame(JFrame gameFrame, int width, int height, int gridSize) {

        // status bar
        statusbar = new JLabel("");
        gameFrame.add(statusbar, BorderLayout.SOUTH);

        // game panel
        JPanel gamePanel = new Grid(new GridLayout(gridSize, gridSize));
        gameFrame.add(gamePanel, BorderLayout.CENTER);

        // upper field
        upperField = new JPanel();
        upperField.setLayout(new BoxLayout(upperField, BoxLayout.X_AXIS));
        upperField.setPreferredSize(new Dimension(width, 30));

        JLabel timeLabel = new JLabel();
        JLabel flagsLabel = new JLabel();

        upperField.add(flagsLabel, BorderLayout.EAST);

        upperField.add(timeLabel);

        /*final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        ActionListener timerListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Date date = new Date();
                String time = timeFormat.format(date);
                timeLabel.setText(time);
            }
        };
        Timer timer = new Timer(1000, timerListener);
        // to make sure it doesn't wait one second at the start
        timer.setInitialDelay(0);
        timer.start();*/



        gameFrame.add(upperField, BorderLayout.NORTH);
    }

    public JLabel getStatusbar() {
        return statusbar;
    }
}
