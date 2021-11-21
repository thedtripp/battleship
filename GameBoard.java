import java.util.ArrayList; // import the ArrayList class
import java.util.Random;

public class GameBoard {
    
    static final int ROWS = 11;
	static final int COLS = 11;
	static final int NUM_SHIPS = 5;
	private int shipCount = 0;
	private Random rand;
	private int currentGuess;
	private int buildHere;
	private int[] shipSizes = {5,4,3,3,2};
	private boolean allSunk = false;

	private Point[][] grid = new Point[ROWS][COLS]; 

	// Points available for ship construction
	private ArrayList<Point> buildShip = new ArrayList<Point>();

	// Points that have not already been guessed will be stored in an array list.
	// Once guessed a point will be removed from the array list.
	private ArrayList<Point> guesses = new ArrayList<Point>();

	private ArrayList<Ship> ships = new ArrayList<Ship>(); // Create an ArrayList object

    	public GameBoard() {
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) {
				grid[r][c] = new Point(r, c, this, false);
				if (r > 0 && c > 0) {
					guesses.add(grid[r][c]);
					buildShip.add(grid[r][c]);
				}
			}
		}
    	}
    
    // Display the grid
	public void display() {
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) {
				System.out.print(" " + grid[r][c].displayCharacter());		
			}
			System.out.println();
		}
    }
    
    public Point[][] getGrid() {
        return grid;
	}
	
	public Point getPoint(int r, int c) {
		return grid[r][c];
	}

	public ArrayList<Ship> getShips() {
		return ships;
	}

	public void addShip(Ship s) {
		ships.add(s);
		this.incShipCount();
	}

	public void removeShip(Ship s) {
		ships.remove(s);
		shipCount--;
	}

	public int getShipCount() {
		return shipCount;
	}

	//increment ship count
	public void incShipCount() {
		shipCount++;
	}

	public ArrayList<Point> getGuesses() {
		return guesses;
	}

	public ArrayList<Point> getBuildShip() {
		return buildShip;
	}

	public boolean getAllSunk() {
		return allSunk;
	}
	//computer guess
	public void guess() {
		rand = new Random();
		currentGuess = rand.nextInt(guesses.size());
		guesses.get(currentGuess).toString();
		shotFired(guesses.get(currentGuess));
		System.out.println("I will shoot here: " + guesses.get(currentGuess));
		guesses.remove(currentGuess);
		System.out.println(guesses.size());

	}

	public void shotFired(Point p) {
		// iterate through arraylist of ships.
		boolean sunkFlag = true;
		boolean shotFlag = false;
		for (int i = 0; i < ships.size(); i++) {
			// iterate through list of points in a ship.
			for (Point sp: ships.get(i).getPoints()) {
				// hit if point is on any ship.
				if (p.equals(sp)) {
					sp.setHit(true);
					this.ships.get(i).hit();
					this.getPoint(p.getRow(), p.getColumn()).setHit(true);
					shotFlag =true;
					if (shotFlag) {
						System.out.println("HIT!");
					}
				}
			} 
			if (!ships.get(i).isSunk()) {
				sunkFlag = false;
			} ships.get(i).announce();
		} if (!shotFlag) {
			System.out.println("MISS");
		}
		if (sunkFlag == true) {
			allSunk = true;
		}
		// if not hit, miss.
		if (!this.getPoint(p.getRow(), p.getColumn()).getHit()) {
			this.getPoint(p.getRow(), p.getColumn()).setMiss(true);
		}
	}

	public void placeShips() {
		rand = new Random();
		//System.out.println("Number of ships: " + ships.size());
		for (int i = 0; i < NUM_SHIPS; i++) {
			
			// get a point
			buildHere = rand.nextInt(buildShip.size());
			Point p = buildShip.get(buildHere); //try building at this point
			Ship aShip = new Ship(p, rand.nextBoolean(), shipSizes[i], this );
			//System.out.println("Number of ships: " + ships.size());
			if (ships.size() > 1) {
				for (int j = 0; j < ships.size() - 1; j++) {
					//check for collision
					if (aShip.collidesWith(ships.get(j))) {
						
						for (Point sp: aShip.getPoints()) {
							boolean keepPoint = false; //should keep
							//if point belong to a previously placed ship, keep the point.
							//if this point doesn't belong to a previously placed ship, remove
							//check if this point belongs to any previously placed ships.
							for (int k = 0; k < ships.size() - 1; k++) {
								for (int l = 0; l < ships.get(k).getPoints().length ; l++) {
									//check
									if (ships.get(k).getPoints()[l].getRow() == sp.getRow() && ships.get(k).getPoints()[l].getColumn() == sp.getColumn()) {
										keepPoint = true;								
									}
								}	
							} if (keepPoint == false) {
								sp.setIsShip(false);
							}
						}
						this.removeShip(aShip);
						i--;
					} 
				}
			}
		}
	}

	public void clearBoard() {

		guesses = new ArrayList<Point>();
		ships = new ArrayList<Ship>(); 
	
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) {
				grid[r][c] = new Point(r, c, this, false);
				if (r > 0 && c > 0) {
					guesses.add(grid[r][c]);
					buildShip.add(grid[r][c]);
				}
			}
		}
	}

	//display all ship points to facilitate testing.
	public void cheat() {
		System.out.println("Totally cheating");
		for (int i = 0; i < getShips().size(); i++) {
			System.out.println(getShips().get(i).printPoints());
		}
	}

}
