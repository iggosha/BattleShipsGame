package org.igor;

import java.awt.Point;
import java.util.Scanner;

import static org.igor.BattleshipsGame.*;

public class BattlefieldModifier {

    public void startTurns(BattleshipsGame game) {
        while (hasAnyShips(game)) {
            game.printAllyAndEnemyBattlefields();
            Point coordinate = getCoordinateToShoot(getCoordinateInput());
            try {
                if (isAbleToShoot(game.enemyBattlefield, coordinate)) {
                    shoot(game.enemyBattlefield, game.enemyBattlefieldToPrint, coordinate);
                } else {
                    throw new IllegalArgumentException("Клетка " + coordinate.y + ", "+ coordinate.x + " недоступна для выстрела");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Point getCoordinateToShoot(String coordinate) {
        return new Point(coordinate.toUpperCase().charAt(0) - 'А', coordinate.charAt(1) - '0');
    }

    public String getCoordinateInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите координату для выстрела: ");
        return scanner.nextLine();
    }

    public boolean isAbleToShoot(int[][] battlefield, Point coordinate) {
        if (coordinate.x < 0 || coordinate.x > BATTLEFIELD_SIZE) {
            return false;
        } else if (coordinate.y < 0 || coordinate.y > BATTLEFIELD_SIZE) {
            return false;
        } else if (battlefield[coordinate.y][coordinate.x] == DESTROYED_CELL) {
            return false;
        } else if (battlefield[coordinate.y][coordinate.x] == SHOT_CELL) {
            return false;
        }
        return true;
    }

    public void shoot(int[][] battlefieldToShoot, int[][] battlefieldToShow, Point coordinate) {
        if (battlefieldToShoot[coordinate.y][coordinate.x] == SHIP_CELL) {
            battlefieldToShoot[coordinate.y][coordinate.x] = DESTROYED_CELL;
            battlefieldToShow[coordinate.y][coordinate.x] = DESTROYED_CELL;
        } else {
            battlefieldToShoot[coordinate.y][coordinate.x] = SHOT_CELL;
            battlefieldToShow[coordinate.y][coordinate.x] = SHOT_CELL;
        }
    }

    public boolean hasAnyShips(BattleshipsGame game) {
        return getShipCellsAmount(game.allyBattlefield) > 0 && getShipCellsAmount(game.enemyBattlefield) > 0;
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
