public class Ship {

    private int length;
    private Point[] shipPoints; 
    private int hitCount;
    private boolean announcedSinking = false;
    
    public Ship(Point origin, boolean isVertical, int length, GameBoard b) {
        this.length = length;

        // store points that belong to the ship in an array
        shipPoints = new Point[length]; 

        // set point of origin
        // set additional points based on length and orientation
        
        // vertical ship
        if (isVertical) {
            if (origin.getRow() + length <= 10) {
                for (int i = 0; i < length; i ++) {
                    b.getGrid()[origin.getRow() + i][origin.getColumn()].setIsShip(true);
                    this.shipPoints[i] = b.getPoint(origin.getRow() + i, origin.getColumn());
                    //int index = ((origin.getRow() + i - 1) * 10) + origin.getColumn() - 1;

                }
            } else {
                for (int i = 0; i < length; i ++) {
                    b.getGrid()[origin.getRow() - i][origin.getColumn()].setIsShip(true);
                    this.shipPoints[i] = b.getPoint(origin.getRow() - i, origin.getColumn());
                }
            }
        }
        // horizontal ship
        else {
            if (origin.getColumn() + length <= 10) {
                for (int i = 0; i < length; i ++) {
                    b.getGrid()[origin.getRow()][origin.getColumn() + i].setIsShip(true);
                    this.shipPoints[i] = b.getPoint(origin.getRow(), origin.getColumn() + i);
                    //int index = ((origin.getRow() + i - 1) * 10) + origin.getColumn() - 1;
                }
            } else {
                for (int i = 0; i < length; i ++) {
                    b.getGrid()[origin.getRow()][origin.getColumn() - i].setIsShip(true);
                    this.shipPoints[i] = b.getPoint(origin.getRow(), origin.getColumn() - i);
                }
            }

        }
        // perform bounds checking
        //add ship to game board
        b.addShip(this);
    }

    public boolean containsPoint(Point p) {
        for (Point sp: shipPoints) {
            if (p.getRow() == sp.getRow() && p.getColumn() == sp.getColumn()) {
                return true;
            }
        }
        return false;
    }

    public boolean collidesWith(Ship s) {
        for (Point sp: s.getPoints()) {
            if (this.containsPoint(sp)) {
                return true;
            }
        }
        return false;
    }

    public int getHitCount() {
        return hitCount;
    }

    // when a ship is hit (GameBoard.shotFired()), we will increment the hit count.
    public void hit() {
        hitCount++;
    }

    public boolean isSunk() {
        if (this.getHitCount() >= length) {
            return true;
        } else {
            return false;
        }
    }

    public Point[] getPoints() {
        return shipPoints;
    }

    public String printPoints() {
        String pointList = "Ship of length " + length + ":";
        for (Point p: shipPoints) {
            int row = p.getRow() - 1;
            int col = p.getColumn() - 1;
            pointList += " (" + row + ", " + col + ")";
        }
        pointList += ".";
        return pointList;
    }

    public void announce() {
        if (announcedSinking == false && isSunk()) {
            System.out.println("You sank a ship of length: " + length);
            announcedSinking = true;
        }
    }
}
