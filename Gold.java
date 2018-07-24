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

//Golds class inherit from GameItem and use 'g' symbol to replace GameItem's 'c' symbol
public class Gold extends GameItem {
	public Gold() {
		super('g');
	}
}