import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cell extends JButton {

    private int position;
    private int bombCount; // if -1 --> MineCell
    private boolean discovered;
    private boolean flagged;
    private Icon cellIcon;

    public Cell(int bombCount) {
        this.discovered = false;
        this.flagged = false;
        this.bombCount = bombCount;
    }

    public int getBombCount() {
        return bombCount;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public Icon getCellIcon() {
        return cellIcon;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public void setCellIcon(Icon cellIcon) {
        if(!this.isDiscovered())
            this.cellIcon = cellIcon;
    }
}
