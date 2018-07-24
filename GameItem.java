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


//GameItem class demonstrates symbol that will display on the board  
public class GameItem {
	private char c; // set symbol is a char.
	public GameItem(char c) {
		this.c = c;
	}
	public char display() {//display method will deliver symbol to game class's display method
		return this.c;
	}
}