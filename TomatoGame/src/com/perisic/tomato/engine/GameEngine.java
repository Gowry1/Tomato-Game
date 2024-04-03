package com.perisic.tomato.engine;

import java.awt.image.BufferedImage;

/**
 * Main class where the games are coming from.
 *
 */
public class GameEngine {
	private String player;
	String thePlayer = player;
	 private int remainingLives=3;
	

	/**
	 * Each player has their own game engine.
	 * 
	 * @param player
	 */
	public GameEngine(String player) {
		thePlayer = player;
	}
	
	
	public void startNewLevel() {
        // Reset the score to 0 at the beginning of each level
        score = 0;
        
        remainingLives = 3;
        // Other logic to set up the level...
    }

	int counter = 0;
	int score = 0;
	GameServer theGames = new GameServer();
	Game current = null;

	/**
	 * Retrieves a game. This basic version only has two games that alternate.
	 */
	public BufferedImage nextGame() {
		current = theGames.getRandomGame();
		return current.getImage();

	}
	
	 public void decreaseLife() {
	        remainingLives--;
	    }

	/**
	 * Checks if the parameter i is a solution to the game URL. If so, score is
	 * increased by one.
	 * 
	 * @param game
	 * @param i
	 * @return
	 */
	public boolean checkSolution( int i) {
		if (i == current.getSolution()) {
			score++;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Retrieves the score.
	 * 
	 * @param player
	 * @return
	 */
	public int getScore() {
		return score;
	}
	
	public int getRemainingLives() {
        return remainingLives;
    }
	
	
	
	

}
