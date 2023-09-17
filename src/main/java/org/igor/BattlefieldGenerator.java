package org.igor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.awt.Point;

import static org.igor.CoordinateConstants.*;

public class BattlefieldGenerator {

    int[][] battlefield;

    public int[][] generateBattlefield(BattleshipsGame game) {
        do {
            battlefield = createEmptyBattlefield();
            placeAllShips();
        } while (game.getShipCellsAmount(battlefield) < 20);
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
                Point coordinate = new Point(startVert, startHoriz);
                if (isValidForPlacing(coordinate, ship)) {
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

    private boolean isValidForPlacing(Point coordinate, Ship ship) {
        if (ship.isVertical()) {
            for (int i = coordinate.x; i < coordinate.x + ship.getLength(); i++) {
                if (i > 9 || hasShipNear(new Point(i, coordinate.y))) {
                    return false;
                }
            }
        } else {
            for (int i = coordinate.y; i < coordinate.y + ship.getLength(); i++) {
                if (i > 9 || hasShipNear(new Point(coordinate.x, i))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasShipNear(Point coordinate) throws IndexOutOfBoundsException {
        // current
        if (battlefield[coordinate.x][coordinate.y] == SHIP_CELL) {
            return true;
        }
        //  right
        else if (coordinate.x < BATTLEFIELD_SIZE - 1 && battlefield[coordinate.x + 1][coordinate.y] == SHIP_CELL) {
            return true;
        } //left
        else if (coordinate.x > 0 && battlefield[coordinate.x - 1][coordinate.y] == SHIP_CELL) {
            return true;
        }
        // top
        else if (coordinate.y < BATTLEFIELD_SIZE - 1 && battlefield[coordinate.x][coordinate.y + 1] == SHIP_CELL) {
            return true;
        } // bot
        else if (coordinate.y > 0 && battlefield[coordinate.x][coordinate.y - 1] == SHIP_CELL) {
            return true;
        } // top right
        else if (coordinate.y < BATTLEFIELD_SIZE - 1 && coordinate.x < BATTLEFIELD_SIZE - 1 && battlefield[coordinate.x + 1][coordinate.y + 1] == SHIP_CELL) {
            return true;
        } // bot right
        else if (coordinate.x < BATTLEFIELD_SIZE - 1 && coordinate.y > 0 && battlefield[coordinate.x + 1][coordinate.y - 1] == SHIP_CELL) {
            return true;
        } // top left
        else if (coordinate.x > 0 && coordinate.y < BATTLEFIELD_SIZE - 1 && battlefield[coordinate.x - 1][coordinate.y + 1] == SHIP_CELL) {
            return true;
        } //  bot left
        else
            return coordinate.x > 0 && coordinate.y > 0 && battlefield[coordinate.x - 1][coordinate.y - 1] == SHIP_CELL;
    }

}
