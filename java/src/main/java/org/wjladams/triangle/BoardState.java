package org.wjladams.triangle;

import java.util.*;

public class BoardState {
    public boolean[][] states;
    public HashMap<Integer, int[]> indexToPos = new HashMap<Integer, int[]>();
    public HashSet<Integer> onIndices = new HashSet<Integer>();
    public List<List<BoardMove>> legalMovesByIndex = new ArrayList<List<BoardMove>>();
    public List<BoardMove> appliedMoves = new ArrayList<BoardMove>();
    private  Random rnd = new Random();
    private int size;

    public BoardState(int size) {
        this.size = size;
        int index=0;
        states=new boolean[size][];
        for(int i=0; i < size; i++) {
            states[i] = new boolean[i+1];
            for(int j=0; j < i+1; j++) {
                int pos[] = {i,j};
                states[i][j]=true;
                indexToPos.put(index, pos);
                onIndices.add(index);
                legalMovesByIndex.add(BoardMove.factoryAllValid(this, i, j));
                index++;
            }
        }
    }

    public void turnOff(int row, int col) {
        states[row][col] = false;
        onIndices.remove(getIndex(row, col));
        addMove(BoardMove.factoryOff(this, row, col));
    }

    private void addMove(BoardMove mv) {
        appliedMoves.add(mv);
    }

    private int getIndex(int row, int col) {
        return (row*(row+1)/2)+col;
    }

    public void applyMove(BoardMove mv) {
        if (!canApply(mv)) {
            throw new IllegalStateException("Cannot do");
        }
        removePiece(mv.posx, mv.posy);
        removePiece(mv.midx, mv.midy);
        addPiece(mv.newx, mv.newy);
        addMove(mv);
    }

    private void removePiece(int posx, int posy) {
        states[posx][posy] = false;
        onIndices.remove(getIndex(posx, posy));
    }

    private void addPiece(int posx, int posy) {
        states[posx][posy] = true;
        onIndices.add(getIndex(posx, posy));
    }

    public boolean canApply(BoardMove mv) {
        if (isAlive(mv.posx, mv.posy)) {
            if (isAlive(mv.midx, mv.midy)) {
                if (!isAlive(mv.newx, mv.newy)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isAlive(int posx, int posy) {
        return states[posx][posy];
    }

    public boolean legalPosition(int x, int y) {
        if ((x<0) || (x >= size))
            return false;
        else if ((y<0) || (y>x))
            return false;
        else
            return true;
    }

    public void randomAlgorithm() {
        //Okay we should just continue this loop forever, we break out later
        int []startPos;
        List<BoardMove> tmpMoves = new ArrayList<BoardMove>(10);
        while(true) {
            boolean foundValid = false;
            //Look for a random on position to move
            List<Integer> tmpOnindices = new ArrayList<Integer>(onIndices);
            Collections.shuffle(tmpOnindices, rnd);
            for(int startIndex : tmpOnindices) {
                startPos = indexToPos.get(startIndex);
                //Pick a random move for this position
                tmpMoves.clear();
                tmpMoves.addAll(legalMovesByIndex.get(startIndex));
                while(tmpMoves.size()>0) {
                    BoardMove mv = tmpMoves.remove(rnd.nextInt(tmpMoves.size()));
                    if (canApply(mv)) {
                        //Found a valid move
                        //apply it
                        foundValid = true;
                        applyMove(mv);
                        break;
                    }
                }
                if (foundValid)
                    break;
            }
            if (!foundValid) {
                //No valid moves left, stop
                return;
            }
        }
    }

    public String toString() {
        String rval = "";
        for(int row=0; row < size; row++) {
            for(int col=0; col <= row; col++) {
                rval+=states[row][col]+" ";
            }
            rval+="\n";
        }
        int count=0;
        for(BoardMove mv : appliedMoves) {
            rval+="Move: "+count+"\n"+mv.toString();
            count++;
        }
        return rval;
    }
    public static void main(String args[]) {
        Random rng = new Random(0);
        for(int count=0; count < 10000; count++) {
            BoardState bs = new BoardState(5);
            bs.rnd.setSeed(rng.nextLong());
            bs.turnOff(0, 0);
            bs.randomAlgorithm();
            if (bs.countOn()<1) {
                System.out.println(bs.countOn());
                System.out.println(bs);
                System.out.println("End soln:");
            } else if (bs.countOn()==8) {
                System.out.println(bs.countOn());
                System.out.println(bs);
                System.out.println("End soln:");
            }
        }
    }

    private int countOn() {
        int rval=0;
        for(int row=0; row < size; row++) {
            for (int col = 0; col <= row; col++) {
                if (states[row][col]) {
                    rval++;
                }
            }
        }
        return rval;

    }

    public int getSize() {
        return size;
    }
}
