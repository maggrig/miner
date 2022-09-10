package mytest;

import java.awt.*;

/**
 * The type Cell.
 */
public class Cell {
      private boolean bomb=false;
    /**
     *   The Cell.
     */
    Rectangle cell=null;
      private int x;
      private int y;
      private int count=0;

    /**
     * Instantiates a new Cell.
     */
    public Cell() {
    }

    /**
     * Is bomb boolean.
     *
     * @return the boolean
     */
    public boolean isBomb() {
        return bomb;
    }

    /**
     * Sets bomb.
     *
     * @param bomb the bomb
     */
    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets count.
     *
     * @param count the count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Instantiates a new Cell.
     *
     * @param bomb  the bomb
     * @param x     the x
     * @param y     the y
     * @param count the count
     */
    public Cell(boolean bomb, int x, int y, int count) {
        this.bomb = bomb;
        this.x = x;
        this.y = y;
        this.count = count;
    }

    /**
     * Gets cell.
     *
     * @return the cell
     */
    public Rectangle getCell() {
        return cell;
    }

    /**
     * Sets cell.
     *
     * @param cell the cell
     */
    public void setCell(Rectangle cell) {
        this.cell = cell;
    }
}
