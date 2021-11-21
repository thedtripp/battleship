import java.util.*;

public class BattleShipMain {

	static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
				
		GameBoard board = new GameBoard();
		
		System.out.println("Welcome to BATTLESHIP.");
		System.out.println("Placing ships...");
		board.placeShips();	
		board.display();


		while (!board.getAllSunk()) {
            		System.out.println("Select coordinates.");
            
            //handle input mismatch exception
            try {
                int row;
                do {
                System.out.print("ROW ( Enter a digit 0-9): " );
                row = 1 + input.nextInt();
                input.nextLine();
                } while (row < 1 || row > 10);
                //handle input mismatch exception
                int column;
                do {
                System.out.print("COLUMN ( Enter a digit 0-9): " );
                column = 1 + input.nextInt();
                input.nextLine();
                } while (column < 1 || column > 10);

                //check that point has not already been shot
                if (!(board.getPoint(row, column).getHit() || board.getPoint(row, column).getMiss())) {
                    //get point by row and colum
                    board.shotFired(board.getPoint(row, column));
                } else {
                    System.out.println("This point has already been shot. Select a different point.");
                }
                board.display();
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid character entry. Try again.");
                input.nextLine();
                continue;
            } 
	} System.out.println("Congratulations! \nYou have sunk all of the ships! \nGame over");

	}
}
