import javax.swing.*;

public class Cell extends JButton {

    private final int bombCount; // if -1 --> MineCell
    private boolean discovered;
    private Icon cellIcon;

    public Cell(int bombCount) {
        this.discovered = false;
        this.bombCount = bombCount;
    }

    public int getBombCount() {
        return bombCount;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public Icon getCellIcon() {
        return cellIcon;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public void setCellIcon(Icon cellIcon) {
        if (!this.isDiscovered())
            this.cellIcon = cellIcon;
    }
}