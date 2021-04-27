import javax.swing.*;

public class Cell extends JButton {

    /* Type :
       0 --> EMPTY
       1 --> MINE
       2 --> NUMBER */
    private final int type;
    private int position;
    private boolean discovered;
    private boolean flagged;

    public Cell(int type, boolean discovered, boolean flagged) {
        this.type = type;
        this.discovered = discovered;
        this.flagged = flagged;
    }

    public int getType() {
        return type;
    }

    public int getPosition() {
        return position;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

}
