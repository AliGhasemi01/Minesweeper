import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static javax.swing.SwingUtilities.isRightMouseButton;

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
            Cell cell = null;
            if (mines.contains(i)) {
                cell = new Cell(-1);
                addMineCellAction(cell);
                cellGrid.add(cell);
            }
            else if (countBomb(i) == 0){
                cell = new Cell(countBomb(i));
                addEmptyCellAction(cell);
                cellGrid.add(cell);
            }
            else{
                cell = new Cell(countBomb(i));
                addNumberCellAction(cell);
                cellGrid.add(cell);
            }
        }
    }

    private void addCells() {
        for (Cell cell : cellGrid) {
            add(cell);
            addFlagAction(cell);
        }
        setBackgroundColor(Color.BLACK);
        setIcons();
    }

    private void finishGame() {
        for(Cell cell : cellGrid){
            cell.setIcon(cell.getCellIcon());
            cell.setDiscovered(true);
            if(cell.getBombCount() == 0)
                cell.setBackground(Color.LIGHT_GRAY);
        }
    }

    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private void setIcons() {
        ImageIcon bombIcon = new ImageIcon("./art/bomb.png");
        for(Cell cell : cellGrid) {
            if(cell.getBombCount() == -1){
                cell.setCellIcon(resizeIcon(bombIcon, 40, 40));
                //cell.setBackground(Color.LIGHT_GRAY);
            }
            else if(cell.getBombCount() == 0){
                //cell.setBackground(Color.LIGHT_GRAY);
            }
            else {
                String imagePath = "./art/" + cell.getBombCount() + ".png";
                //cell.setBackground(Color.LIGHT_GRAY);
                cell.setCellIcon(resizeIcon(new ImageIcon(imagePath), 40, 40));
            }
        }
    }

    private void setBackgroundColor(Color color){
        for(Cell cell : cellGrid) {
            cell.setBackground(Color.DARK_GRAY);
            cell.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
    }

    private int countBomb(int position) {

        if (isFirstRow(position) && isLeftEdge(position)) {
            int R = mines.contains(position + 1) ? 1 : 0;
            int B = mines.contains(position + Game.GRID_SIZE) ? 1 : 0;
            int BR = mines.contains(position + Game.GRID_SIZE + 1) ? 1 : 0;
            return R + B + BR;
        } else if (isFirstRow(position) && isRightEdge(position)) {
            int L = mines.contains(position - 1) ? 1 : 0;
            int BL = mines.contains(position + Game.GRID_SIZE - 1) ? 1 : 0;
            int B = mines.contains(position + Game.GRID_SIZE) ? 1 : 0;
            return L + BL + B;
        } else if (isLastRow(position) && isLeftEdge(position)) {
            int T = mines.contains(position - Game.GRID_SIZE) ? 1 : 0;
            int TR = mines.contains(position - Game.GRID_SIZE + 1) ? 1 : 0;
            int R = mines.contains(position + 1) ? 1 : 0;
            return T + TR + R;
        } else if (isLastRow(position) && isRightEdge(position)) {
            int TL = mines.contains(position - Game.GRID_SIZE - 1) ? 1 : 0;
            int T = mines.contains(position - Game.GRID_SIZE) ? 1 : 0;
            int L = mines.contains(position - 1) ? 1 : 0;
            return TL + T + L;
        } else if (isRightEdge(position)) {
            int TL = mines.contains(position - Game.GRID_SIZE - 1) ? 1 : 0;
            int T = mines.contains(position - Game.GRID_SIZE) ? 1 : 0;
            int L = mines.contains(position - 1) ? 1 : 0;
            int BL = mines.contains(position + Game.GRID_SIZE - 1) ? 1 : 0;
            int B = mines.contains(position + Game.GRID_SIZE) ? 1 : 0;
            return TL + T + L + BL + B;
        } else if (isLeftEdge(position)) {
            int T = mines.contains(position - Game.GRID_SIZE) ? 1 : 0;
            int TR = mines.contains(position - Game.GRID_SIZE + 1) ? 1 : 0;
            int R = mines.contains(position + 1) ? 1 : 0;
            int B = mines.contains(position + Game.GRID_SIZE) ? 1 : 0;
            int BR = mines.contains(position + Game.GRID_SIZE + 1) ? 1 : 0;
            return T + TR + R + B + BR;
        } else if (isFirstRow(position)) {
            int L = mines.contains(position - 1) ? 1 : 0;
            int R = mines.contains(position + 1) ? 1 : 0;
            int BL = mines.contains(position + Game.GRID_SIZE - 1) ? 1 : 0;
            int B = mines.contains(position + Game.GRID_SIZE) ? 1 : 0;
            int BR = mines.contains(position + Game.GRID_SIZE + 1) ? 1 : 0;
            return L + R + BL + B + BR;
        } else if (isLastRow(position)) {
            int TL = mines.contains(position - Game.GRID_SIZE - 1) ? 1 : 0;
            int T = mines.contains(position - Game.GRID_SIZE) ? 1 : 0;
            int TR = mines.contains(position - Game.GRID_SIZE + 1) ? 1 : 0;
            int L = mines.contains(position - 1) ? 1 : 0;
            int R = mines.contains(position + 1) ? 1 : 0;
            return TL + T + TR + L + R;
        } else {
            int TL = mines.contains(position - Game.GRID_SIZE - 1) ? 1 : 0;
            int T = mines.contains(position - Game.GRID_SIZE) ? 1 : 0;
            int TR = mines.contains(position - Game.GRID_SIZE + 1) ? 1 : 0;
            int L = mines.contains(position - 1) ? 1 : 0;
            int R = mines.contains(position + 1) ? 1 : 0;
            int BL = mines.contains(position + Game.GRID_SIZE - 1) ? 1 : 0;
            int B = mines.contains(position + Game.GRID_SIZE) ? 1 : 0;
            int BR = mines.contains(position + Game.GRID_SIZE + 1) ? 1 : 0;
            return TL + T + TR + L + R + BL + B + BR;
        }
        /*
        if (mines.contains(position - Game.GRID_SIZE - 1) ||
                    mines.contains(position - Game.GRID_SIZE) ||
                    mines.contains(position - Game.GRID_SIZE + 1) ||
                    mines.contains(position - 1) ||
                    mines.contains(position + 1) ||
                    mines.contains(position + Game.GRID_SIZE - 1) ||
                    mines.contains(position + Game.GRID_SIZE) ||
                    mines.contains(position + Game.GRID_SIZE + 1))
        */
    }

    private boolean isRightEdge(int position){
        return position % Game.GRID_SIZE == Game.GRID_SIZE - 1;
    }

    private boolean isLeftEdge(int position){
        return position % Game.GRID_SIZE == 0;
    }

    private boolean isLastRow(int position){
        return position < bound && position >= bound - Game.GRID_SIZE;
    }

    private boolean isFirstRow(int position){
        return position < Game.GRID_SIZE && position >= 0;
    }

    private void addFlagAction(Cell cell){
        cell.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isRightMouseButton(e)) {
                    ImageIcon flagIcon = new ImageIcon("./art/flag.png");
                    if(!cell.isDiscovered()){
                        if(cell.getIcon() == null){
                            cell.setIcon(resizeIcon(flagIcon, 40, 40));
                        }
                        else {
                            cell.setIcon(null);
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void addMineCellAction(Cell cell) {
        cell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finishGame();
            }
        });
    }

    private void addEmptyCellAction(Cell cell){
        cell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                findEmptyCells(cellGrid.indexOf(cell));
            }
        });
    }

    private void addNumberCellAction(Cell cell) {
        cell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cell.setIcon(cell.getCellIcon());
                cell.setDiscovered(true);
            }
        });
    }
    public void findEmptyCells(int i){
        Cell cell = cellGrid.get(i);
        if(cell.getBombCount() == 0){
            cell.setBackground(Color.LIGHT_GRAY);
            cell.setIcon(null);
            cell.setDiscovered(true);

            if (isFirstRow(i) && isLeftEdge(i)) {
                if(!cellGrid.get(i + 1).isDiscovered())
                    findEmptyCells(i + 1); // right
                if(!cellGrid.get(i + Game.GRID_SIZE).isDiscovered())
                    findEmptyCells(i + Game.GRID_SIZE); // Bottom
                if(!cellGrid.get(i + Game.GRID_SIZE + 1).isDiscovered())
                    findEmptyCells(i + Game.GRID_SIZE + 1); // Bottom-right
            } else if (isFirstRow(i) && isRightEdge(i)) {
                if(!cellGrid.get(i - 1).isDiscovered())
                    findEmptyCells(i - 1); // left
                if(!cellGrid.get(i + Game.GRID_SIZE).isDiscovered())
                    findEmptyCells(i + Game.GRID_SIZE); // Bottom
                if(!cellGrid.get(i + Game.GRID_SIZE - 1).isDiscovered())
                    findEmptyCells(i + Game.GRID_SIZE - 1); // Bottom-left
            } else if (isLastRow(i) && isLeftEdge(i)) {
                if(!cellGrid.get(i + 1).isDiscovered())
                    findEmptyCells(i + 1); // right
                if(!cellGrid.get(i - Game.GRID_SIZE).isDiscovered())
                    findEmptyCells(i - Game.GRID_SIZE); // Top
                if(!cellGrid.get(i - Game.GRID_SIZE + 1).isDiscovered())
                    findEmptyCells(i - Game.GRID_SIZE + 1); // Top-right
            } else if (isLastRow(i) && isRightEdge(i)) {
                if(!cellGrid.get(i - 1).isDiscovered())
                    findEmptyCells(i - 1); // left
                if(!cellGrid.get(i - Game.GRID_SIZE).isDiscovered())
                    findEmptyCells(i - Game.GRID_SIZE); // Top
                if(!cellGrid.get(i - Game.GRID_SIZE - 1).isDiscovered())
                    findEmptyCells(i - Game.GRID_SIZE - 1); // Top-left
            } else if (isRightEdge(i)) {
                if(!cellGrid.get(i - 1).isDiscovered())
                    findEmptyCells(i - 1); // left
                if(!cellGrid.get(i - Game.GRID_SIZE).isDiscovered())
                    findEmptyCells(i - Game.GRID_SIZE); // Top
                if(!cellGrid.get(i + Game.GRID_SIZE).isDiscovered())
                    findEmptyCells(i + Game.GRID_SIZE); // Bottom
                if(!cellGrid.get(i - Game.GRID_SIZE - 1).isDiscovered())
                    findEmptyCells(i - Game.GRID_SIZE - 1); // Top-left
                if(!cellGrid.get(i + Game.GRID_SIZE - 1).isDiscovered())
                    findEmptyCells(i + Game.GRID_SIZE - 1); // Bottom-left
            } else if (isLeftEdge(i)) {
                if(!cellGrid.get(i + 1).isDiscovered())
                    findEmptyCells(i + 1); // right
                if(!cellGrid.get(i - Game.GRID_SIZE).isDiscovered())
                    findEmptyCells(i - Game.GRID_SIZE); // Top
                if(!cellGrid.get(i + Game.GRID_SIZE).isDiscovered())
                    findEmptyCells(i + Game.GRID_SIZE); // Bottom
                if(!cellGrid.get(i - Game.GRID_SIZE + 1).isDiscovered())
                    findEmptyCells(i - Game.GRID_SIZE + 1); // Top-right
                if(!cellGrid.get(i + Game.GRID_SIZE + 1).isDiscovered())
                    findEmptyCells(i + Game.GRID_SIZE + 1); // Bottom-right
            } else if (isFirstRow(i)) {
                if(!cellGrid.get(i - 1).isDiscovered())
                    findEmptyCells(i - 1); // left
                if(!cellGrid.get(i + 1).isDiscovered())
                    findEmptyCells(i + 1); // right
                if(!cellGrid.get(i + Game.GRID_SIZE).isDiscovered())
                    findEmptyCells(i + Game.GRID_SIZE); // Bottom
                if(!cellGrid.get(i + Game.GRID_SIZE - 1).isDiscovered())
                    findEmptyCells(i + Game.GRID_SIZE - 1); // Bottom-left
                if(!cellGrid.get(i + Game.GRID_SIZE + 1).isDiscovered())
                    findEmptyCells(i + Game.GRID_SIZE + 1); // Bottom-right
            } else if (isLastRow(i)) {
                if(!cellGrid.get(i - 1).isDiscovered())
                    findEmptyCells(i - 1); // left
                if(!cellGrid.get(i + 1).isDiscovered())
                    findEmptyCells(i + 1); // right
                if(!cellGrid.get(i - Game.GRID_SIZE).isDiscovered())
                    findEmptyCells(i - Game.GRID_SIZE); // Top
                if(!cellGrid.get(i - Game.GRID_SIZE - 1).isDiscovered())
                    findEmptyCells(i - Game.GRID_SIZE - 1); // Top-left
                if(!cellGrid.get(i - Game.GRID_SIZE + 1).isDiscovered())
                    findEmptyCells(i - Game.GRID_SIZE + 1); // Top-right
            } else {
                if(!cellGrid.get(i - 1).isDiscovered())
                    findEmptyCells(i - 1); // left
                if(!cellGrid.get(i + 1).isDiscovered())
                    findEmptyCells(i + 1); // right
                if(!cellGrid.get(i - Game.GRID_SIZE).isDiscovered())
                    findEmptyCells(i - Game.GRID_SIZE); // Top
                if(!cellGrid.get(i + Game.GRID_SIZE).isDiscovered())
                    findEmptyCells(i + Game.GRID_SIZE); // Bottom
                if(!cellGrid.get(i - Game.GRID_SIZE - 1).isDiscovered())
                    findEmptyCells(i - Game.GRID_SIZE - 1); // Top-left
                if(!cellGrid.get(i - Game.GRID_SIZE + 1).isDiscovered())
                    findEmptyCells(i - Game.GRID_SIZE + 1); // Top-right
                if(!cellGrid.get(i + Game.GRID_SIZE - 1).isDiscovered())
                    findEmptyCells(i + Game.GRID_SIZE - 1); // Bottom-left
                if(!cellGrid.get(i + Game.GRID_SIZE + 1).isDiscovered())
                    findEmptyCells(i + Game.GRID_SIZE + 1); // Bottom-right
            }
        }
        else if(cell.getBombCount() > 0){
            cell.setIcon(cell.getCellIcon());
            cell.setDiscovered(true);
            return;
        }
        else {
            return;
        }
    }
}
