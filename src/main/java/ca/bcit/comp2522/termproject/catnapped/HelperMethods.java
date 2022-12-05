package ca.bcit.comp2522.termproject.catnapped;

import java.awt.geom.Rectangle2D;

/**
 * HelperMethods class. Contains helper methods used in our program.
 * @author Jerry and Bryan
 * @version 2022
 */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static ca.bcit.comp2522.termproject.catnapped.Constants.EnemyConstant.*;

public class HelperMethods {
    /**
     * Determines if there is a collision against a solid tile
     * @param xCoordinate a float
     * @param yCoordinate a float
     * @param width a float
     * @param height a float
     * @param levelInfo a 2D integer array
     * @return a boolean
     */
    public static boolean collisionDetection(float xCoordinate, float yCoordinate, float width, float height,
                                             int[][] levelInfo) {

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

    /**
     * Determines if the tile at the current position is solid or not.
     * @param x a float
     * @param y a float
     * @param levelInfo a 2D integer array
     * @return a boolean
     */
    private static boolean isSolidTile(float x, float y, int[][] levelInfo) {
        int maxWidth = levelInfo[0].length * Game.TEST_SIZE;
        if (x < 0 || x >= maxWidth) {
            return true;
        }
        if (y < 0 || y >= Game.GAME_WINDOW_HEIGHT) {
            return true;
        }

        float xIndex = x / Game.DEFAULT_TILE_SIZE;
        float yIndex = y / Game.DEFAULT_TILE_SIZE;

        return IsTileSolid((int) xIndex, (int) yIndex, levelInfo);

    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];

        if (value >= 48 || value < 0 || value != 11)
            return true;
        return false;
    }

    /**
     * If actor collides with a wall, place character next to it.
     * @param hitbox a Rectangle2D.Float object
     * @param xSpeed a float
     * @return a float
     */
    public static float GetActorNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / Game.DEFAULT_TILE_SIZE);
        if (xSpeed > 0) {
            //Right
            int tileXPosition = currentTile * Game.DEFAULT_TILE_SIZE;
            return tileXPosition + (Game.DEFAULT_TILE_SIZE - 1);
        } else {
            //Left
            return currentTile * Game.DEFAULT_TILE_SIZE;
        }
    }

    /**
     * Check if actor collides with ceiling or floor
     * @param playerHitbox a Rectangle2D.float object
     * @param airSpeed a float
     * @return a float
     */
    public static float CheckActorCollisionWithCeilingOrFloor(Rectangle2D.Float playerHitbox, float airSpeed) {
        int currentTile = (int) (playerHitbox.y / Game.DEFAULT_TILE_SIZE);
        if (airSpeed > 0) {
            //Falling
            int tileYPosition = currentTile * Game.DEFAULT_TILE_SIZE;
            int yOffset = (int) (Game.DEFAULT_TILE_SIZE - playerHitbox.height);
            return tileYPosition + yOffset;
        } else {
            //Jumping
            return currentTile * Game.DEFAULT_TILE_SIZE;
        }
    }

    /**
     * Checks of actor is on the floor.
     * @param hitbox a Rectangle2D.Float object
     * @param levelInfo a 2D integer array
     * @return a boolean
     */
    public static boolean IsActorOnFloor(Rectangle2D.Float hitbox, int[][] levelInfo) {
        //Check bottom left and bottom right corners
        if (!isSolidTile(hitbox.x, hitbox.y + hitbox.height + 1, levelInfo)) {
            if (!isSolidTile(hitbox.x + hitbox.width, hitbox.y, levelInfo)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the tile they are on is a floor.
     * @param hitbox a Rectangle2D.Float object
     * @param xSpeed a float
     * @param levelInfo a 2D integer array
     * @return a boolean
     */
    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] levelInfo) {
//        System.out.println("Hitbox Height: " + hitbox.height);
        return isSolidTile(hitbox.x + hitbox.width / 2 + xSpeed, hitbox.y + hitbox.height + 1, levelInfo);

//        //        System.out.println("Hitbox Height: " + hitbox.height);
//        if(xSpeed > 0)
//            return isSolidTile(hitbox.x + hitbox.width + xSpeed / 2, hitbox.y + hitbox.height + 1, levelInfo);
//        else
//            return isSolidTile(hitbox.x + xSpeed / 2, hitbox.y + hitbox.height + 1, levelInfo);
//    }
    }

    /**
     * Determines if there are no solid tiles in between two positions
     * @param levelInfo a 2D integer array
     * @param startIndex an integer
     * @param endIndex an integer
     * @param yPosition an integer
     * @return a boolean
     */
    private static boolean noTilesInBetween(int[][] levelInfo, int startIndex, int endIndex, int yPosition) {
        for (int i = startIndex; i < endIndex; i++) {
            if (isSolidTile(i, yPosition, levelInfo)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if there is a clear line of sight between two actors.
     * @param levelInfo a 2D integer array
     * @param objectHitbox a Rectangle2D.Float object
     * @param playerHitbox a Rectangle2D.Float object
     * @param objectYPosition an integer
     * @return a boolean
     */
    public static boolean ClearLineOfSight(int[][] levelInfo, Rectangle2D.Float objectHitbox,
                                           Rectangle2D.Float playerHitbox, int objectYPosition) {

        int objectXTile = (int) objectHitbox.x / Game.DEFAULT_TILE_SIZE;
        int playerXTile = (int) playerHitbox.x / Game.DEFAULT_TILE_SIZE;
//        System.out.println("Checking LOS");

        if (objectXTile > playerXTile) {
            return noTilesInBetween(levelInfo, playerXTile, objectXTile, objectYPosition);

        } else {
            return noTilesInBetween(levelInfo, objectXTile, playerXTile, objectYPosition);
        }

    }

    /**
     * Gets level data to display level
     * @param img the image of level
     * @return RGB values in 2D array levelImages
     */
    public static int[][] GetLevelData(BufferedImage img) {

        int[][] levelImages = new int[img.getHeight()][img.getWidth()];
        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48) {
                    value = 0;
                }
                levelImages[j][i] = value;
            }
        }
        return levelImages;
    }

    /**
     * Gets level data of enemies to display enemies in specific levels
     * @param img the image of level
     * @return RGB values of enemies in an arraylist.
     */
    public static ArrayList<Enemy> GetEnemies(BufferedImage img) {
        ArrayList<Enemy> enemyList = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();

                if (value == ENEMY_IDENTIFIER) {
                    enemyList.add(new Enemy(i * Game.DEFAULT_TILE_SIZE, j * Game.DEFAULT_TILE_SIZE, ENEMY_HEIGHT, ENEMY_WIDTH));
                }
            }
        }
        return enemyList;
    }

    /**
     * Gets level data of spawn to display player
     * @param img the image of level
     * @return Point of spawn
     */
        public static Point GetPlayerSpawn(BufferedImage img) {
            for (int j = 0; j < img.getHeight(); j++)
                for (int i = 0; i < img.getWidth(); i++) {
                    Color color = new Color(img.getRGB(i, j));
                    int value = color.getGreen();
                    if (value == 100)
                        return new Point(i * Game.DEFAULT_TILE_SIZE, j * Game.DEFAULT_TILE_SIZE);
                }
            return new Point(1 * Game.DEFAULT_TILE_SIZE, 1 * Game.DEFAULT_TILE_SIZE);
        }
}


