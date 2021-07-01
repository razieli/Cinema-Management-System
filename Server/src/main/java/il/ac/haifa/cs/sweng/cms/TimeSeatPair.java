package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Screening;

import java.util.GregorianCalendar;

public class TimeSeatPair {

    private Screening screening;
    private int row;
    private int col;
    private GregorianCalendar blockingTime;

    protected TimeSeatPair(Screening screening, int row, int col, GregorianCalendar blockingTime) {
        this.screening = screening;
        this.row = row;
        this.col = col;
        this.blockingTime = blockingTime;
    }

    public Screening getScreening() {
        return screening;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public GregorianCalendar getBlockingTime() {
        return blockingTime;
    }
}
