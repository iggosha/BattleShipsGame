package org.igor;

public interface GameActions {

    void startGame();

    void printAllyAndEnemyBattlefields(int[][] allyBattlefield, int[][] enemyBattlefield);

    int takeTurn(String cellToShoot);

    void endGame();

}
