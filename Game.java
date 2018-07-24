/**
* The Wumpus Game program is an application that users can play a role on the board.
* Users can select 1,2,3,4 to move their position on the board. 
* There are some items such as wumpus, pits and golds on the board. 
* When users play the game and meet pit and wumpus on the board, the game is over.
* When users collect all golds on the board, users win the game.(numbers of gold is random)
* When users move their position, application will give them hints to help them to move. 
* @author  Chih Yuan Chen S3585502
* @author  You  Hao       S3583715
* @version 1.0
* @since   2016-09-25
*/

import java.util.*;
//Game is setting variables that this application needs.
public class Game {
	private static final String title = "=====Wumpus===="; //set menu title
	
	//set choice array to store menu's options
	private static final String[] choice = { 
			"1. Move player left",
			"2. Move player right",
			"3. Move player up",
			"4. Move player down",
			"5. Quit",
			};
	
	private int row = 4; //assign row is an integer variable to set board's row 
	private int column = 4;//assign column is an integer variable to set board's column 
	private int score = 0; //assign score is an integer to display users's score
	private GameItem[][] board = new GameItem[row][column];//use array to set entire board 
	private int xxx; // set an integer variable for xxx axis. 
	private int yyy; // set an integer variable for yyy axis.
	private boolean quit; //set boolean variable to distinguish conditions.
	Random randomGenerator = new Random(); //set a random variable to assign the position of user and game items.
	private int GoldsNo = randomGenerator.nextInt(3); //set GoldsNo is random. 
	private boolean getscore;// set get score is boolean variable to acquire users score. 

//runGame is a method to conduct entire application	
//This part is using do, while and switch to distinguish conditions.
	public void runGame() {
		Scanner reader = new Scanner(System.in); //set scanner reader to distinguish input values
		int choice;  //choice is an integer variable to get input values from scanner reader
		boolean goon = true; //set goon is a boolean variable to control the game's process.
		setBoard(); //call setBoard method.
		
		do{
			display(); //call setBoard method.
			senseNearby();//call senseNearby method.
			menu();//call menu method.
			do {
		        try{
		            choice = reader.nextInt();//set choice's value equals to scanner reader's
		            
		            // use if statement to distinguish valid input value
		            if (choice > 5 || choice < 1) {
		            	System.out.println("Invalid input, please choose again: ");
		            	continue;
		            }
		            // use switch to move player's position and process game. 
		            switch (choice) {
					case 1:
						yyy = (yyy + 3)%4;
						break;
					case 2:
						yyy = (yyy + 1)%4;
						break;
					case 3:
						xxx = (xxx + 3)%4;
						break;
					case 4:
						xxx = (xxx + 1)%4;
						break;
					case 5:
						quit = false;
						System.out.println("Goodbye!");
						break;
					default:
						System.out.println("Please enter your choice: ");
						break;
					}
		            goon = false;
		        }
		        catch (Exception e) {//catch general exception to show error message to user    
		        	System.out.println("Invalid input, please choose again: ");
		            reader.nextLine();		       
		        }
			}while (goon);
			
			//use if statement to update goldsno,score and board.
			if (board[xxx][yyy] instanceof Gold) { 
				board[xxx][yyy] = new ClearGround();
				GoldsNo--;
				score++;
				getscore = true;
			}
			//extend if statement to show game over message when user meet wumpus
			else if (board[xxx][yyy] instanceof Wumpus) {
				quit = false;
				System.out.println("Wumpus killed you! Game Over!");
				System.out.println("Your score is " + score + ".");
			}
			//extend if statement to show game over message when user fail in a pit
			else if (board[xxx][yyy] instanceof Pit) { 
				quit = false;
				System.out.println("You failed in a pit! Game Over!");
				System.out.println("Your score is " + score + ".");
			}
			//use if statement to show you win message when user collected all golds.
			if (GoldsNo == -1) {
				quit = false;
				System.out.println("All golds were collected! You Win! Your score is " + score + ".");
			}
			goon = true;
		}while(quit);
	}
	
	
//setBoard is a method to set board and set items's positions on the board.
	private void setBoard() {
		int bxxx;
		int byyy;
		quit = true;

		//Set wumpus's position randomly
		bxxx = randomGenerator.nextInt(row);
		byyy = randomGenerator.nextInt(column);
		Wumpus wp = new Wumpus();
		board[bxxx][byyy] = wp;

		//Set golds' position randomly
		for (int i=0; i<=GoldsNo; i++) {
			int x = randomGenerator.nextInt(row);
			int y = randomGenerator.nextInt(column);
			if (board[x][y]==null) {
				Gold g = new Gold();
				board[x][y] = g;
			}
			else {
				i--;
			}
		}
		
		//Set pits' position randomly
		for (int i=0; i<3; i++) {
			int x = randomGenerator.nextInt(row);
			int y = randomGenerator.nextInt(column);
			if (board[x][y]==null) {
				Pit p = new Pit();
				board[x][y] = p;
			}
			else {
				i--;
			}
		}
		
		//Set player's position randomly
		do{
			xxx = randomGenerator.nextInt(row);
			yyy = randomGenerator.nextInt(column);	
		}while(board[xxx][yyy]!=null);
		
		// Set ClearGrounds 
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++) {
				if (board[i][j]==null) {
					ClearGround cg = new ClearGround();
					board[i][j] = cg;
				}
			}
		}
	}


//display is a method to illustrate a board that helps users to see their position and items positions.	
	private void display() {
		System.out.println("+---+---+---+---+");
		for (int i=0; i<4; i++) {
			System.out.printf("|");
			for (int j=0; j<4; j++) {
				if (i==xxx && j==yyy) {
					System.out.printf(" * |");	
				}
				else {
					System.out.printf(" %c |",board[i][j].display());
				}
			}
			System.out.printf("\n");
			if (i<3) {
				System.out.println("+---+---+---+---+");
			}
		}
		System.out.println("+---+---+---+---+");
	}
	
	//senseNearby is a method that give users hints to play the game
	private void senseNearby() {
		boolean senseW = false,
				senseG = false,
				senseP = false;
	//use if statement and accord different conditions to give users hints. 	
		GameItem a = board[xxx][(yyy+3)%4];
			if (a instanceof Gold) {
				senseG = true;
			}
			else if (a instanceof Wumpus) {
				senseW = true;
			}
			else if (a instanceof Pit) {
				senseP = true;
			}
		if (senseW) {
			System.out.println("Left: a vale smell!");
			senseW = false;
		}
		if (senseP) {
			System.out.println("Left: a breeze!");
			senseP = false;
		}
		if (senseG) {
			System.out.println("Left: a faint glitter!");
			senseG = false;
		}
		
		GameItem b = board[xxx][(yyy+1)%4];
			if (b instanceof Gold) {
				senseG = true;
			}
			else if (b instanceof Wumpus) {
				senseW = true;
			}
			else if (b instanceof Pit) {
				senseP = true;
			}
		if (senseW) {
			System.out.println("Right: a vale smell!");
			senseW = false;
		}
		if (senseP) {
			System.out.println("Right: a breeze!");
			senseP = false;
		}
		if (senseG) {
			System.out.println("Right: a faint glitter!");
			senseG = false;
		}
		
		GameItem c = board[(xxx+3)%4][yyy];
			if (c instanceof Gold) {
				senseG = true;
			}
			else if (c instanceof Wumpus) {
				senseW = true;
			}
			else if (c instanceof Pit) {
				senseP = true;
			}
		if (senseW) {
			System.out.println("Up: a vale smell!");
			senseW = false;
		}
		if (senseP) {
			System.out.println("Up: a breeze!");
			senseP = false;
		}
		if (senseG) {
			System.out.println("Up: a faint glitter!");
			senseG = false;
		}
		
		GameItem d = board[(xxx+1)%4][yyy];
			if (d instanceof Gold) {
				senseG = true;
			}
			else if (d instanceof Wumpus) {
				senseW = true;
			}
			else if (d instanceof Pit) {
				senseP = true;
			}
		if (senseW) {
			System.out.println("Down: a vale smell!");
			senseW = false;
		}
		if (senseP) {
			System.out.println("Down: a breeze!");
			senseP = false;
		}
		if (senseG) {
			System.out.println("Down: a faint glitter!");
			senseG = false;
		}

		if (getscore == true && GoldsNo != -1) {
			System.out.println("Congratulations! You've collected a gold!");
			getscore = false;
		}
		System.out.println("Score: " + score);
		System.out.println();
	}
	
	//menu is a method to run the menu function to users 
	private void menu() {
		System.out.println(title);
		for (int i=0; i<choice.length; i++) {
			System.out.println(choice[i]);
		}
	}
}
