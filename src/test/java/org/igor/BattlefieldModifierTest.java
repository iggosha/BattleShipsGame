package org.igor;

import org.junit.jupiter.api.Test;

import java.awt.*;

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
                    battlefieldModifier.shoot(game.enemyBattlefield, game.enemyBattlefieldToPrint, coordinate);
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