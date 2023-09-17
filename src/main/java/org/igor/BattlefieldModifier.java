package org.igor;

import java.awt.Point;
import java.util.Scanner;

import static org.igor.CoordinateConstants.*;

public class BattlefieldModifier {

    public void startTurns(BattleshipsGame game) {
        while (hasAnyShips(game)) {
            game.printAllyAndEnemyBattlefields();
            try {
                Point coordinate = getCoordinateToShoot(getCoordinateInput());
                if (isAbleToShoot(game.enemyBattlefield, coordinate)) {
                    shootByPlayer(game, coordinate);
                } else {
                    throw new IllegalArgumentException("Клетка недоступна для выстрела");
                }
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                System.err.flush();
            }
        }
    }

    public String getCoordinateInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите координату для выстрела: ");
        return scanner.nextLine();
    }

    public Point getCoordinateToShoot(String coordinate) throws IllegalArgumentException {
        try {
            return new Point(coordinate.toUpperCase().charAt(0) - 'А', coordinate.charAt(1) - '0');
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Неверно введена точка для выстрела");
        }
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

    public void shootByPlayer(BattleshipsGame game, Point coordinate) {
        if (game.enemyBattlefield[coordinate.y][coordinate.x] == SHIP_CELL) {
            game.enemyBattlefield[coordinate.y][coordinate.x] = DESTROYED_CELL;
            game.enemyBattlefieldToPrint[coordinate.y][coordinate.x] = DESTROYED_CELL;
            System.out.println(ANSI_GREEN + "Есть попадание!" + ANSI_RESET);
        } else {
            game.enemyBattlefield[coordinate.y][coordinate.x] = SHOT_CELL;
            game.enemyBattlefieldToPrint[coordinate.y][coordinate.x] = SHOT_CELL;
            System.out.println(ANSI_RED +"Промах!" + ANSI_RESET);
            waitAfterShot();
            shootByComputer(game);
        }
    }

    public void shootByComputer(BattleshipsGame game) {
        for (int i = 0; i < BATTLEFIELD_SIZE; i++) {
            for (int j = 0; j < BATTLEFIELD_SIZE; j++) {
                if (j > 0 && game.allyBattlefield[i][j - 1] == DESTROYED_CELL && game.allyBattlefield[i][j] == EMPTY_CELL) {
                    continue;
                }
                Point coordinate = new Point(j, i);
                if (isAbleToShoot(game.allyBattlefield, coordinate)) {
                    if (game.allyBattlefield[coordinate.y][coordinate.x] == SHIP_CELL) {
                        game.allyBattlefield[coordinate.y][coordinate.x] = DESTROYED_CELL;
                        System.out.println(ANSI_BLUE + "Компьютер:" + ANSI_RED +" попал в корабль в точке "
                                + (char) ((int) 'А' + coordinate.x) + coordinate.y + ANSI_RESET);
                        waitAfterShot();
                    } else {
                        game.allyBattlefield[coordinate.y][coordinate.x] = SHOT_CELL;
                        System.out.println(ANSI_BLUE + "Компьютер: " + ANSI_GREEN +  "промахнулся, выстрелив в точку " +
                                (char) ((int) 'А' + coordinate.x) + coordinate.y + ANSI_RESET);
                        waitAfterShot();
                        return;
                    }
                }
            }
        }
    }

    private void waitAfterShot() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasAnyShips(BattleshipsGame game) {
        return game.getShipCellsAmount(game.allyBattlefield) > 0 && game.getShipCellsAmount(game.enemyBattlefield) > 0;
    }
}
