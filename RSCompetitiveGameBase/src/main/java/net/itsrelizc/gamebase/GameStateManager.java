package net.itsrelizc.gamebase;

import net.itsrelizc.gamebase.scoreboard.BoardManager;
import net.itsrelizc.gamebase.scoreboard.BoardManager.BoardState;

public class GameStateManager {
	
	public static void start() {
		BoardManager.changeWaitingState();
	}
	
}
