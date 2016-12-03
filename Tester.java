package clueless;

public class Tester {

	public static void main(String[] args) {
		BoardGame boardGame = new BoardGame();
		boardGame.createPlayerList();
		boardGame.loadWinningCards();
		boardGame.combineDecks();
		boardGame.dealCards();
		boardGame.gamePlayLoop();
	}
}
