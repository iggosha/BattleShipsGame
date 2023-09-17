package org.igor;

import static org.igor.CoordinateConstants.*;

public class BattleshipsGame {


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
        allyBattlefield = battlefieldGenerator.generateBattlefield(this);
        enemyBattlefield = battlefieldGenerator.generateBattlefield(this);
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
            System.out.println(ANSI_RED + "Вы проиграли!");
        } else {
            System.out.println(ANSI_GREEN + "Вы победили!");
        }
    }

    public void printAllyAndEnemyBattlefields() {
        printCoordinateLetters();
        for (int currentString = 0; currentString < BATTLEFIELD_SIZE; currentString++) {
            printBattlefield(this.allyBattlefield, currentString);
            printBattlefield(this.enemyBattlefieldToPrint, currentString);
            System.out.println();
        }
        System.out.println("Палуб кораблей на вашем поле: " + getShipCellsAmount(allyBattlefield) +
                "           Палуб кораблей на поле противника: " + getShipCellsAmount(enemyBattlefield));
    }

    private void printCoordinateLetters() {
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
    }

    private void printBattlefield(int[][] battlefield, int currentString) {
        System.out.print(currentString + " |");
        for (int currentColumn = 0; currentColumn < BATTLEFIELD_SIZE; currentColumn++) {
            if (battlefield[currentString][currentColumn] == SHIP_CELL) {
                System.out.printf(ANSI_BLUE + "%-2s|" + ANSI_RESET, battlefield[currentString][currentColumn]);
            } else if (battlefield[currentString][currentColumn] == DESTROYED_CELL) {
                System.out.printf(ANSI_RED + "%-2s|" + ANSI_RESET, battlefield[currentString][currentColumn]);
            } else if (battlefield[currentString][currentColumn] == EMPTY_CELL) {
                System.out.printf(ANSI_GREEN + "%-2s|" + ANSI_RESET, battlefield[currentString][currentColumn]);
            } else {
                System.out.printf("%-2s|", battlefield[currentString][currentColumn]);
            }
        }
        System.out.printf("%-10s", "");
    }

    public int getShipCellsAmount(int[][] battlefieldToShoot) {
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