public class Point {
    
    private int row;
    private int column;
    private boolean isShip;
    private boolean hit = false;
    private boolean miss = false;
    //private char

    protected GameBoard board;

    public Point(int r, int c, GameBoard b, boolean s) {
		row = r;
		column = c;
        board = b;
        isShip = s;
    }
    
    public char displayCharacter() {
        
        // label axes
        if (this.getRow() == 0 && this.getColumn() == 0) {
            return ' ';
        } else if (this.getRow() == 0 && this.getColumn() > 0) {
            //int a = 64 + this.getColumn(); // print 'A' - 'J'
            //labels
            int a = 47 + this.getColumn();
            //int a = 47 + 1 + this.getColumn();
            return (char)a;
        } else if (this.getColumn() == 0 && this.getRow() > 0) {
            //labels
            int a = 47 + this.getRow();
            //int a = 47 + 1 + this.getRow();
            return (char)a;
        } else if (this.getHit()) {
            return 'X';
        } else if (this.getMiss()) {
            return '.';
        }
        // display ships
        else if (this.getIsShip()) {
            //hide ships
            return '~';
            //show ships
            //return 'O';
        }
        // empty water
		return '~';
    }
    
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean getIsShip() {
        return isShip;
    }

    public void setIsShip(boolean b) {
        isShip = b;
    }

    public boolean getHit() {
        return hit;
    }

    public void setHit(boolean b) {
        hit = b;
    }
    public boolean getMiss() {
        return miss;
    }

    public void setMiss(boolean b) {
        miss = b;
    }

    public String toString() {
        return "(" + this.getRow() + ", " + this.getColumn() + ")";
    }

    public boolean equals(Point p) {
        if (this.getRow() == p.getRow() && this.getColumn() == p.getColumn()) {
            return true;
        } else {
            return false;
        }
    }

}