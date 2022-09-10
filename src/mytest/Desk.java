package mytest;

import java.util.ArrayList;

/**
 * The type Desk.
 */
public class Desk {
    private int height = 10;
    private int width = 10;
    private Cell deskboard[][];
//    private

    /**
     * Instantiates a new Desk.
     *
     * @param height the height
     * @param width  the width
     */
    public Desk(int height,int width) {
        this.deskboard = new Cell[height][width];
    }
}

