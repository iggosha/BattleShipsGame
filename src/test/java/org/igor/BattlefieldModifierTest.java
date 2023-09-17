package org.igor;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.igor.CoordinateConstants.*;


class BattlefieldModifierTest {

    static int counterLetter = 0;
    static int counterNumber = 0;

    @Test
    void runOnlyPlayerGame() {
        BattleshipsGame game = new BattleshipsGame();
        game.startGame();
        BattlefieldModifier battlefieldModifier = new BattlefieldModifier();
        while (battlefieldModifier.hasAnyShips(game)) {
            game.printAllyAndEnemyBattlefields();
            Point coordinate = getCoordinateToShoot();
            if (coordinate.y >= 10) return;
            try {
                if (battlefieldModifier.isAbleToShoot(game.enemyBattlefield, coordinate)) {
                    if (coordinate.x > 0 && game.enemyBattlefield[coordinate.y][coordinate.x - 1] == DESTROYED_CELL
                            && game.enemyBattlefield[coordinate.y][coordinate.x] == EMPTY_CELL) {
                        continue;
                    }
                    battlefieldModifier.shootByPlayer(game, coordinate);
                } else {
                    throw new IllegalArgumentException("Клетка " + coordinate.y + ", " + coordinate.x + " недоступна для выстрела");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        game.endGame();
    }

    Point getCoordinateToShoot() {
        Point point = new Point();
        point.y = counterLetter;
        point.x = counterNumber;
        counterNumber++;
        if (counterNumber >= 10) {
            counterNumber = 0;
            counterLetter++;
        }
        return point;
    }
}