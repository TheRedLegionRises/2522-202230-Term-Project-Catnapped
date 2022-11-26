package ca.bcit.comp2522.termproject.catnapped;

import java.awt.geom.Rectangle2D;

import java.awt.*;

public class HelperMethods {
    public static boolean collisionDetection(float xCoordinate, float yCoordinate, float width, float height,
                                             int[][] levelInfo) {
//
//        width /= 3;
//        xCoordinate += 40;
//        yCoordinate -= 32;

        if (!isSolidTile(xCoordinate, yCoordinate, levelInfo)) {
            if (!isSolidTile(xCoordinate + width, yCoordinate + height, levelInfo)) {
                if (!isSolidTile(xCoordinate + width, yCoordinate, levelInfo)) {
                    if (!isSolidTile(xCoordinate, yCoordinate + height, levelInfo))
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

        if (value >= 48 || value < 0 || value != 11) {
            return true;
        }
        return false;

    }

    public static float GetActorNextToWall(Rectangle2D.Float playerHitbox, float xSpeed) {
        int currentTile = (int) (playerHitbox.x / Game.DEFAULT_TILE_SIZE);
        if (xSpeed > 0) {
            //Right
            int tileXPosition = currentTile * Game.DEFAULT_TILE_SIZE;
            int xOffset = (int) (Game.DEFAULT_TILE_SIZE - playerHitbox.width / 3);
            return tileXPosition + 31;
        } else {
            //Left
            return currentTile * Game.DEFAULT_TILE_SIZE;
        }
    }

    public static float CheckActorCollisionWithCeilingOrFloor(Rectangle2D.Float playerHitbox, float airSpeed) {
        int currentTile = (int) (playerHitbox.y / Game.DEFAULT_TILE_SIZE);
        if (airSpeed > 0) {
            //Falling
            int tileYPosition = currentTile * Game.DEFAULT_TILE_SIZE;
            int yOffset = (int) (Game.DEFAULT_TILE_SIZE - playerHitbox.height);
            return tileYPosition + yOffset + 31;
        } else {
            //Jumping
            return currentTile * Game.DEFAULT_TILE_SIZE;
        }
    }

    public static boolean IsActorOnFloor(Rectangle2D.Float hitbox, int[][] levelInfo) {
        //Check bottom left and bottom right corners
        if (!isSolidTile(hitbox.x, hitbox.y + hitbox.height, levelInfo)) {
            if (!isSolidTile(hitbox.x + hitbox.width, hitbox.y, levelInfo)) {
                return false;
            }
        }
        return true;
    }
}

