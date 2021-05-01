import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static javax.swing.SwingUtilities.isRightMouseButton;

public class Grid extends JPanel {

    private final int bound = Game.grid_size * Game.grid_size;
    private final ArrayList<Integer> mines = new ArrayList<>(); // Position of mines
    private final static ArrayList<Cell> cellGrid = new ArrayList<>();

    public Grid(GridLayout gridLayout) {
        super(gridLayout);
        createCell();
        addCells();
    }

    public void createCell() {
        boolean picked = false;
        for (int i = 1; i <= Game.mine_count; i++) {
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
                Cell cell = new Cell(-1);
                addMineCellAction(cell);
                cellGrid.add(cell);
            } else if (countBomb(i) == 0) {
                Cell cell = new Cell(countBomb(i));
                addEmptyCellAction(cell);
                cellGrid.add(cell);
            } else {
                Cell cell = new Cell(countBomb(i));
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

    private void finishGame(boolean gameWon) {
        if (!gameWon) {
            Game.getStatusbar().setText("You Lost");
        } else {
            Game.getStatusbar().setText("You Win");
            return;
        }
        for (Cell cell : cellGrid) {
            cell.setIcon(cell.getCellIcon());
            cell.setDiscovered(true);
            if (cell.getBombCount() == 0)
                cell.setBackground(Color.LIGHT_GRAY);
        }
    }

    private static Icon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(Game.WIDTH / Game.grid_size, Game.WIDTH / Game.grid_size, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private void setIcons() {
        ImageIcon bombIcon = new ImageIcon("./art/bomb.png");
        for (Cell cell : cellGrid) {
            if (cell.getBombCount() == -1) {
                cell.setCellIcon(resizeIcon(bombIcon));
            } else if (cell.getBombCount() > 0) {
                String imagePath = "./art/" + cell.getBombCount() + ".png";
                cell.setCellIcon(resizeIcon(new ImageIcon(imagePath)));
            }
        }
    }

    private void setBackgroundColor(Color color) {
        for (Cell cell : cellGrid) {
            cell.setBackground(color);
            cell.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
    }

    private int countBomb(int position) {

        if (isFirstRow(position) && isLeftEdge(position)) {
            int R = mines.contains(position + 1) ? 1 : 0;
            int B = mines.contains(position + Game.grid_size) ? 1 : 0;
            int BR = mines.contains(position + Game.grid_size + 1) ? 1 : 0;
            return R + B + BR;
        } else if (isFirstRow(position) && isRightEdge(position)) {
            int L = mines.contains(position - 1) ? 1 : 0;
            int BL = mines.contains(position + Game.grid_size - 1) ? 1 : 0;
            int B = mines.contains(position + Game.grid_size) ? 1 : 0;
            return L + BL + B;
        } else if (isLastRow(position) && isLeftEdge(position)) {
            int T = mines.contains(position - Game.grid_size) ? 1 : 0;
            int TR = mines.contains(position - Game.grid_size + 1) ? 1 : 0;
            int R = mines.contains(position + 1) ? 1 : 0;
            return T + TR + R;
        } else if (isLastRow(position) && isRightEdge(position)) {
            int TL = mines.contains(position - Game.grid_size - 1) ? 1 : 0;
            int T = mines.contains(position - Game.grid_size) ? 1 : 0;
            int L = mines.contains(position - 1) ? 1 : 0;
            return TL + T + L;
        } else if (isRightEdge(position)) {
            int TL = mines.contains(position - Game.grid_size - 1) ? 1 : 0;
            int T = mines.contains(position - Game.grid_size) ? 1 : 0;
            int L = mines.contains(position - 1) ? 1 : 0;
            int BL = mines.contains(position + Game.grid_size - 1) ? 1 : 0;
            int B = mines.contains(position + Game.grid_size) ? 1 : 0;
            return TL + T + L + BL + B;
        } else if (isLeftEdge(position)) {
            int T = mines.contains(position - Game.grid_size) ? 1 : 0;
            int TR = mines.contains(position - Game.grid_size + 1) ? 1 : 0;
            int R = mines.contains(position + 1) ? 1 : 0;
            int B = mines.contains(position + Game.grid_size) ? 1 : 0;
            int BR = mines.contains(position + Game.grid_size + 1) ? 1 : 0;
            return T + TR + R + B + BR;
        } else if (isFirstRow(position)) {
            int L = mines.contains(position - 1) ? 1 : 0;
            int R = mines.contains(position + 1) ? 1 : 0;
            int BL = mines.contains(position + Game.grid_size - 1) ? 1 : 0;
            int B = mines.contains(position + Game.grid_size) ? 1 : 0;
            int BR = mines.contains(position + Game.grid_size + 1) ? 1 : 0;
            return L + R + BL + B + BR;
        } else if (isLastRow(position)) {
            int TL = mines.contains(position - Game.grid_size - 1) ? 1 : 0;
            int T = mines.contains(position - Game.grid_size) ? 1 : 0;
            int TR = mines.contains(position - Game.grid_size + 1) ? 1 : 0;
            int L = mines.contains(position - 1) ? 1 : 0;
            int R = mines.contains(position + 1) ? 1 : 0;
            return TL + T + TR + L + R;
        } else {
            int TL = mines.contains(position - Game.grid_size - 1) ? 1 : 0;
            int T = mines.contains(position - Game.grid_size) ? 1 : 0;
            int TR = mines.contains(position - Game.grid_size + 1) ? 1 : 0;
            int L = mines.contains(position - 1) ? 1 : 0;
            int R = mines.contains(position + 1) ? 1 : 0;
            int BL = mines.contains(position + Game.grid_size - 1) ? 1 : 0;
            int B = mines.contains(position + Game.grid_size) ? 1 : 0;
            int BR = mines.contains(position + Game.grid_size + 1) ? 1 : 0;
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

    private boolean isRightEdge(int position) {
        return position % Game.grid_size == Game.grid_size - 1;
    }

    private boolean isLeftEdge(int position) {
        return position % Game.grid_size == 0;
    }

    private boolean isLastRow(int position) {
        return position < bound && position >= bound - Game.grid_size;
    }

    private boolean isFirstRow(int position) {
        return position < Game.grid_size && position >= 0;
    }

    private void addFlagAction(Cell cell) {
        cell.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isRightMouseButton(e)) {
                    ImageIcon flagIcon = new ImageIcon("./art/flag.png");
                    if (!cell.isDiscovered()) {
                        if (cell.getIcon() == null) {
                            cell.setIcon(resizeIcon(flagIcon));
                        } else {
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
                finishGame(false);
            }
        });
    }

    private void addEmptyCellAction(Cell cell) {
        cell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findEmptyCells(cellGrid.indexOf(cell));
                if (isLastCell())
                    finishGame(true);
            }
        });
    }

    private void addNumberCellAction(Cell cell) {
        cell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cell.setIcon(cell.getCellIcon());
                cell.setDiscovered(true);
                if (isLastCell())
                    finishGame(true);
            }
        });
    }

    private void findEmptyCells(int i) {
        Cell cell = cellGrid.get(i);
        if (cell.getBombCount() == 0) {
            cell.setBackground(Color.LIGHT_GRAY);
            cell.setIcon(null);
            cell.setDiscovered(true);

            if (isFirstRow(i) && isLeftEdge(i)) {
                if (!cellGrid.get(i + 1).isDiscovered())
                    findEmptyCells(i + 1); // right
                if (!cellGrid.get(i + Game.grid_size).isDiscovered())
                    findEmptyCells(i + Game.grid_size); // Bottom
                if (!cellGrid.get(i + Game.grid_size + 1).isDiscovered())
                    findEmptyCells(i + Game.grid_size + 1); // Bottom-right
            } else if (isFirstRow(i) && isRightEdge(i)) {
                if (!cellGrid.get(i - 1).isDiscovered())
                    findEmptyCells(i - 1); // left
                if (!cellGrid.get(i + Game.grid_size).isDiscovered())
                    findEmptyCells(i + Game.grid_size); // Bottom
                if (!cellGrid.get(i + Game.grid_size - 1).isDiscovered())
                    findEmptyCells(i + Game.grid_size - 1); // Bottom-left
            } else if (isLastRow(i) && isLeftEdge(i)) {
                if (!cellGrid.get(i + 1).isDiscovered())
                    findEmptyCells(i + 1); // right
                if (!cellGrid.get(i - Game.grid_size).isDiscovered())
                    findEmptyCells(i - Game.grid_size); // Top
                if (!cellGrid.get(i - Game.grid_size + 1).isDiscovered())
                    findEmptyCells(i - Game.grid_size + 1); // Top-right
            } else if (isLastRow(i) && isRightEdge(i)) {
                if (!cellGrid.get(i - 1).isDiscovered())
                    findEmptyCells(i - 1); // left
                if (!cellGrid.get(i - Game.grid_size).isDiscovered())
                    findEmptyCells(i - Game.grid_size); // Top
                if (!cellGrid.get(i - Game.grid_size - 1).isDiscovered())
                    findEmptyCells(i - Game.grid_size - 1); // Top-left
            } else if (isRightEdge(i)) {
                if (!cellGrid.get(i - 1).isDiscovered())
                    findEmptyCells(i - 1); // left
                if (!cellGrid.get(i - Game.grid_size).isDiscovered())
                    findEmptyCells(i - Game.grid_size); // Top
                if (!cellGrid.get(i + Game.grid_size).isDiscovered())
                    findEmptyCells(i + Game.grid_size); // Bottom
                if (!cellGrid.get(i - Game.grid_size - 1).isDiscovered())
                    findEmptyCells(i - Game.grid_size - 1); // Top-left
                if (!cellGrid.get(i + Game.grid_size - 1).isDiscovered())
                    findEmptyCells(i + Game.grid_size - 1); // Bottom-left
            } else if (isLeftEdge(i)) {
                if (!cellGrid.get(i + 1).isDiscovered())
                    findEmptyCells(i + 1); // right
                if (!cellGrid.get(i - Game.grid_size).isDiscovered())
                    findEmptyCells(i - Game.grid_size); // Top
                if (!cellGrid.get(i + Game.grid_size).isDiscovered())
                    findEmptyCells(i + Game.grid_size); // Bottom
                if (!cellGrid.get(i - Game.grid_size + 1).isDiscovered())
                    findEmptyCells(i - Game.grid_size + 1); // Top-right
                if (!cellGrid.get(i + Game.grid_size + 1).isDiscovered())
                    findEmptyCells(i + Game.grid_size + 1); // Bottom-right
            } else if (isFirstRow(i)) {
                if (!cellGrid.get(i - 1).isDiscovered())
                    findEmptyCells(i - 1); // left
                if (!cellGrid.get(i + 1).isDiscovered())
                    findEmptyCells(i + 1); // right
                if (!cellGrid.get(i + Game.grid_size).isDiscovered())
                    findEmptyCells(i + Game.grid_size); // Bottom
                if (!cellGrid.get(i + Game.grid_size - 1).isDiscovered())
                    findEmptyCells(i + Game.grid_size - 1); // Bottom-left
                if (!cellGrid.get(i + Game.grid_size + 1).isDiscovered())
                    findEmptyCells(i + Game.grid_size + 1); // Bottom-right
            } else if (isLastRow(i)) {
                if (!cellGrid.get(i - 1).isDiscovered())
                    findEmptyCells(i - 1); // left
                if (!cellGrid.get(i + 1).isDiscovered())
                    findEmptyCells(i + 1); // right
                if (!cellGrid.get(i - Game.grid_size).isDiscovered())
                    findEmptyCells(i - Game.grid_size); // Top
                if (!cellGrid.get(i - Game.grid_size - 1).isDiscovered())
                    findEmptyCells(i - Game.grid_size - 1); // Top-left
                if (!cellGrid.get(i - Game.grid_size + 1).isDiscovered())
                    findEmptyCells(i - Game.grid_size + 1); // Top-right
            } else {
                if (!cellGrid.get(i - 1).isDiscovered())
                    findEmptyCells(i - 1); // left
                if (!cellGrid.get(i + 1).isDiscovered())
                    findEmptyCells(i + 1); // right
                if (!cellGrid.get(i - Game.grid_size).isDiscovered())
                    findEmptyCells(i - Game.grid_size); // Top
                if (!cellGrid.get(i + Game.grid_size).isDiscovered())
                    findEmptyCells(i + Game.grid_size); // Bottom
                if (!cellGrid.get(i - Game.grid_size - 1).isDiscovered())
                    findEmptyCells(i - Game.grid_size - 1); // Top-left
                if (!cellGrid.get(i - Game.grid_size + 1).isDiscovered())
                    findEmptyCells(i - Game.grid_size + 1); // Top-right
                if (!cellGrid.get(i + Game.grid_size - 1).isDiscovered())
                    findEmptyCells(i + Game.grid_size - 1); // Bottom-left
                if (!cellGrid.get(i + Game.grid_size + 1).isDiscovered())
                    findEmptyCells(i + Game.grid_size + 1); // Bottom-right
            }
        } else if (cell.getBombCount() > 0) {
            cell.setIcon(cell.getCellIcon());
            cell.setDiscovered(true);
        }
    }

    private boolean isLastCell() {
        for (Cell cell : cellGrid)
            if (cell.getBombCount() != -1 && !cell.isDiscovered())
                return false;
        return true;
    }
}