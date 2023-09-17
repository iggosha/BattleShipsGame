package org.igor;

public class BattleshipsGame {

    // 0 - empty, 1 - ship (part), -1 - destroyed
    public static final int BATTLEFIELD_SIZE = 10;
    public static final int EMPTY_CELL = 0;
    public static final int SHIP_CELL = 11;
    public static final int DESTROYED_CELL = -1;
    public static final int SHOT_CELL = 8;

    public int[][] allyBattlefield;
    public int[][] enemyBattlefield;
    public int[][] enemyBattlefieldToPrint;


    public static void main(String[] args) {
        BattleshipsGame game = new BattleshipsGame();
        game.startGame();
        game.takeTurn();
        game.endGame();
    }

    public void startGame() {
        BattlefieldGenerator battlefieldGenerator = new BattlefieldGenerator();
        allyBattlefield = battlefieldGenerator.generateBattlefield();
        enemyBattlefield = battlefieldGenerator.generateBattlefield();
        enemyBattlefieldToPrint = battlefieldGenerator.createEmptyBattlefield();
        System.out.println("Игра началась");
    }

    public void takeTurn() {
        BattlefieldModifier battlefieldModifier = new BattlefieldModifier();
        battlefieldModifier.startTurns(this);
    }

    public void endGame() {
        printAllyAndEnemyBattlefields();
        System.out.println("Игра окончена");
        if (getShipCellsAmount(allyBattlefield) == 0) {
            System.out.println("Вы проиграли!");
        } else {
            System.out.println("Вы победили!");
        }
    }

    public void printAllyAndEnemyBattlefields() {
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
                System.out.printf("%-2s|", enemyBattlefieldToPrint[i][j]);
            }
            System.out.println();
        }
        System.out.println("Палуб кораблей на вашем поле: " + getShipCellsAmount(allyBattlefield) +
                "           Палуб кораблей на поле противника: " + getShipCellsAmount(enemyBattlefield));
    }

    private int getShipCellsAmount(int[][] battlefieldToShoot) {
        int counter = 0;
        for (int i = 0; i < BATTLEFIELD_SIZE; i++) {
            for (int j = 0; j < BATTLEFIELD_SIZE; j++) {
                if (battlefieldToShoot[i][j] == SHIP_CELL) {
                    counter++;
                }
            }
        }
        return counter;
    }
}