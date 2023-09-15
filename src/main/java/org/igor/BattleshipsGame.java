package org.igor;

public class BattleshipsGame implements GameActions {

    // 0 - empty, 1 - ship (part), -1 - destroyed
    public static final int BATTLEFIELD_SIZE = 10;
    public static final int EMPTY_CELL = 0;
    public static final int SHIP_CELL = 11;
    public static final int DESTROYED_CELL = -1;
    private int[][] enemyBattlefield;
    private int[][] allyBattlefield;
    private int[][] enemyBattlefieldToPrint;


    public static void main(String[] args) {
        BattleshipsGame game = new BattleshipsGame();
        game.startGame();
        game.printAllyAndEnemyBattlefields(game.allyBattlefield, game.enemyBattlefield);
    }

    @Override
    public void startGame() {
        BattlefieldGenerator battlefieldGenerator = new BattlefieldGenerator();
        enemyBattlefield = battlefieldGenerator.generateBattlefield();
        allyBattlefield = battlefieldGenerator.generateBattlefield();
        enemyBattlefieldToPrint = battlefieldGenerator.createEmptyBattlefield();
    }

    @Override
    public int takeTurn(String cellToShoot) {
        return 0;
    }

    @Override
    public void endGame() {

    }

    @Override
    public void printAllyAndEnemyBattlefields(int[][] allyBattlefield, int[][] enemyBattlefield) {
        System.out.printf("%-50s", "        Ваше поле:");
        System.out.printf("%-50s\n", "Поле противника:");
        for (int j = 0; j < 2; j++) {
            System.out.print("  |");
            for (char i = 'А'; i <= 'Й'; i++) {
                System.out.print(i + " |");
            }
            System.out.printf("%-10s", "");

        }
        System.out.println();
        for (int i = 0; i < BATTLEFIELD_SIZE; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < BATTLEFIELD_SIZE; j++) {
                System.out.printf("%-2s|", allyBattlefield[i][j]);
            }
            System.out.printf("%-10s", "");
            System.out.print(i + " |");
            for (int j = 0; j < BATTLEFIELD_SIZE; j++) {
                System.out.printf("%-2s|", enemyBattlefield[i][j]);
            }
            /*System.out.printf("%-10s", "");
            System.out.print(i + " |");
            for (int j = 0; j < BATTLEFIELD_SIZE; j++) {
                System.out.printf("%-2s|", enemyBattlefieldToPrint[i][j]);
            }*/
            System.out.println();
        }
    }
}
// TODO Ход игры, отображение поля противника, завершение игры, тесты