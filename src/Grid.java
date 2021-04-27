import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Grid extends JPanel {

    private final int bound = Game.GRID_SIZE * Game.GRID_SIZE;

    private boolean picked = false;

    private ArrayList<Integer> mines = new ArrayList<>(); /// Position of mines
    public static ArrayList<Cell> cellGrid = new ArrayList<Cell>();

    public Grid(GridLayout gridLayout) {
        super(gridLayout);
        createCell();
        addCells();
    }

    public void createCell() {
        for (int i = 1; i <= Game.MINE_COUNT; i++) {
            while (!picked) {
                int minePosition = (int) (Math.random() * bound);
                if (!mines.contains(minePosition)) {
                    mines.add(minePosition);
                    picked = true;
                }
            }
            picked = false;
        }
        for (int i = 0; i < bound; i++) {
            if (mines.contains(i)) {
                cellGrid.add(new Cell(1, false, false));
            } else if(i % Game.GRID_SIZE == 0) { // right edge
                if (mines.contains(i - Game.GRID_SIZE) ||
                        mines.contains(i - Game.GRID_SIZE + 1) ||
                        mines.contains(i + 1) ||
                        mines.contains(i + Game.GRID_SIZE) ||
                        mines.contains(i + Game.GRID_SIZE + 1)) {
                    cellGrid.add(new Cell(2, false, false));
                } else {
                    cellGrid.add(new Cell(0, false, false));
                }
            } else if(i % Game.GRID_SIZE == Game.GRID_SIZE - 1) { // left edge
                if (mines.contains(i - Game.GRID_SIZE - 1) ||
                        mines.contains(i - Game.GRID_SIZE) ||
                        mines.contains(i - 1) ||
                        mines.contains(i + Game.GRID_SIZE - 1) ||
                        mines.contains(i + Game.GRID_SIZE)) {
                    cellGrid.add(new Cell(2, false, false));
                } else {
                    cellGrid.add(new Cell(0, false, false));
                }
            } else {
                if (mines.contains(i - Game.GRID_SIZE - 1) ||
                        mines.contains(i - Game.GRID_SIZE) ||
                        mines.contains(i - Game.GRID_SIZE + 1) ||
                        mines.contains(i - 1) ||
                        mines.contains(i + 1) ||
                        mines.contains(i + Game.GRID_SIZE - 1) ||
                        mines.contains(i + Game.GRID_SIZE) ||
                        mines.contains(i + Game.GRID_SIZE + 1)) {
                    cellGrid.add(new Cell(2, false, false));
                } else {
                    cellGrid.add(new Cell(0, false, false));
                }
            }
        }
    }

    private void addCells() {
        for(int i = 0; i < cellGrid.size(); i++){
            add(cellGrid.get(i));
        }
    }

}
