package org.igor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.awt.Point;

import static org.igor.BattleshipsGame.*;

public class BattlefieldGenerator {

    int[][] battlefield;

    public int[][] generateBattlefield() {
        do {
            battlefield = createEmptyBattlefield();
            placeAllShips();
        } while (getShipCellsAmount(battlefield) < 20);
        return battlefield;
    }


    public int[][] createEmptyBattlefield() {
        int[][] emptyBattlefield = new int[BATTLEFIELD_SIZE][BATTLEFIELD_SIZE];
        for (int i = 0; i < BATTLEFIELD_SIZE; i++) {
            Arrays.fill(emptyBattlefield[i], EMPTY_CELL);
        }
        return emptyBattlefield;
    }

    private void placeAllShips() {
        List<Ship> ships = new ArrayList<>();
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4 - j; i++) {
                ships.add(new Ship(j + 1));
            }
        }
        Collections.shuffle(ships);
        for (Ship ship : ships) {
            placeShip(ship);
        }
    }

    private void placeShip(Ship ship) {
        for (int startVert = 0; startVert < BATTLEFIELD_SIZE; startVert++) {
            for (int startHoriz = 0; startHoriz < BATTLEFIELD_SIZE; startHoriz += 4) {
                if (startHoriz == 0) {
                    startHoriz += (int) Math.round(Math.random());
                }
                Point point = new Point(startVert, startHoriz);
                if (isValidForPlacing(point, ship)) {
                    if (ship.isVertical()) {
                        for (int i = startVert; i < startVert + ship.getLength(); i++) {
                            battlefield[i][startHoriz] = SHIP_CELL;
                        }
                    } else {
                        for (int i = startHoriz; i < startHoriz + ship.getLength(); i++) {
                            battlefield[startVert][i] = SHIP_CELL;
                        }
                    }
                    return;
                }
            }
        }
    }

    private boolean isValidForPlacing(Point point, Ship ship) {
        if (ship.isVertical()) {
            for (int i = point.x; i < point.x + ship.getLength(); i++) {
                if (i > 9 || hasShipNear(new Point(i, point.y))) {
                    return false;
                }
            }
        } else {
            for (int i = point.y; i < point.y + ship.getLength(); i++) {
                if (i > 9 || hasShipNear(new Point(point.x, i))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasShipNear(Point point) throws IndexOutOfBoundsException {
        // current
        if (battlefield[point.x][point.y] == SHIP_CELL) {
            return true;
        }
        //  right
        else if (point.x < BATTLEFIELD_SIZE - 1 && battlefield[point.x + 1][point.y] == SHIP_CELL) {
            return true;
        } //left
        else if (point.x > 0 && battlefield[point.x - 1][point.y] == SHIP_CELL) {
            return true;
        }
        // top
        else if (point.y < BATTLEFIELD_SIZE - 1 && battlefield[point.x][point.y + 1] == SHIP_CELL) {
            return true;
        } // bot
        else if (point.y > 0 && battlefield[point.x][point.y - 1] == SHIP_CELL) {
            return true;
        } // top right
        else if (point.y < BATTLEFIELD_SIZE - 1 && point.x < BATTLEFIELD_SIZE - 1 && battlefield[point.x + 1][point.y + 1] == SHIP_CELL) {
            return true;
        } // bot right
        else if (point.x < BATTLEFIELD_SIZE - 1 && point.y > 0 && battlefield[point.x + 1][point.y - 1] == SHIP_CELL) {
            return true;
        } // top left
        else if (point.x > 0 && point.y < BATTLEFIELD_SIZE - 1 && battlefield[point.x - 1][point.y + 1] == SHIP_CELL) {
            return true;
        } //  bot left
        else return point.x > 0 && point.y > 0 && battlefield[point.x - 1][point.y - 1] == SHIP_CELL;
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
