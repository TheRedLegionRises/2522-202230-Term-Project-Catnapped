package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;

public class HelperMethods {
    public static boolean collisionDetection(float xCoordinate, float yCoordinate, float width, float height,
                                             int[][] levelInfo) {
//
//        width /= 3;
//        xCoordinate += 40;

        if(!isSolidTile(xCoordinate, yCoordinate, levelInfo)) {
            if(!isSolidTile(xCoordinate + width, yCoordinate + height, levelInfo)) {
                if(!isSolidTile(xCoordinate + width, yCoordinate, levelInfo)) {
                    if(!isSolidTile(xCoordinate, yCoordinate + height, levelInfo))
                        return true;
                }
            }
        }
        return false;
    }

    private static boolean isSolidTile(float x, float y, int[][] levelInfo) {
        if (x < 0 || x >= Game.GAME_WINDOW_WIDTH) {
            return true;
        }
        if (y < 0 || y >= Game.GAME_WINDOW_HEIGHT) {
            return true;
        }

        float xIndex = x / Game.DEFAULT_TILE_SIZE;
        float yIndex = y / Game.DEFAULT_TILE_SIZE;

        int value = levelInfo[(int) yIndex][(int) xIndex];

        if(value >= 48 || value < 0 || value != 11) {
            return true;
        }
        return false;

    }

//    public static float GetActorNextToWall(Rectangle playerHitbox, float xSpeed) {
//        if (xSpeed > 0) {
//            //Right
//        } else {
//            //Left
//        }
//    }
}
