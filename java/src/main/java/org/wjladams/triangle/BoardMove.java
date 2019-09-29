package org.wjladams.triangle;

import java.util.ArrayList;
import java.util.List;

public class BoardMove {
    public BoardState parent;
    public boolean isOffMove=false;
    public int posx;
    public int posy;
    public int newx;
    public int newy;
    public int midx;
    public int midy;

    private BoardMove(BoardState parent) {
        this.parent = parent;
    }

    public BoardMove(BoardState parent, int posx, int posy, int newx, int newy) {
        this(parent);
        this.posx = posx;
        this.posy = posy;
        this.newx = newx;
        this.newy = newy;
        this.midx = (posx+newx)/2;
        this.midy = (posy+newy)/2;
    }

    public static BoardMove factoryOff(BoardState parent, int posx, int posy) {
        BoardMove rval = new BoardMove(parent);
        rval.posx = posx;
        rval.posy = posy;
        rval.isOffMove = true;
        return rval;
    }

    public static List<BoardMove> factoryAllValid(BoardState parent, int posx, int posy) {
        List<BoardMove> rval = new ArrayList<BoardMove>();
        int newx, newy;
        for (int dx = -2; dx <= 2; dx = dx + 2) {
            for (int dy = -2; dy <= 2; dy += 2) {
                if (((dx != 0) || (dy != 0)) && ((dx+dy)!=0)) {
                    newx = posx + dx;
                    newy = posy + dy;
                    if (parent.legalPosition(newx, newy)) {
                        rval.add(new BoardMove(parent, posx, posy, newx, newy));
                    }
                }
            }
        }
        return rval;
    }

    public String toString() {
        int size = parent.getSize();
        if (isOffMove) {
            String rval ="";
            for(int row=0; row < size; row++) {
                for (int col=0; col <=row ; col++) {
                    if ((posx == row) && (posy==col)) {
                        rval += "X ";
                    } else {
                        rval += "o ";
                    }
                }
                rval+="\n";
            }
            return rval;
        }
        String rval = "";
        for(int row=0; row < size; row++) {
            for (int col=0; col <=row ; col++) {
                if ((posx == row) && (posy==col)) {
                    rval += "S ";
                } else if ((midx == row) && (midy == col)) {
                    rval += "J ";
                } else if ((newx == row) && (newy == col)) {
                    rval += "E ";
                } else {
                    rval += "o ";
                }
            }
            rval+="\n";
        }
        return rval;
    }
}
