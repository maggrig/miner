package mytest;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
 * The type Graphic panel.
 */
public class GraphicPanel extends JPanel {
    /**
     * The Count.
     */
    int count = 0;
    private Cell new_desk[][];
    private int step = 20;
    private Color graphicColor = Color.green;
    private int width;
    private int height;
    private int width_y;//y cell
    private int height_x;//x cell
    private int x1, y1;
    private Graphics g;

    public void paint(Graphics g) {
        super.paint(g);
        width = getWidth();
        height = getHeight();
        drawGrid(g);
//        drawGraphic(g);
        MyMouseHandler handler = new MyMouseHandler();
        this.addMouseListener(handler);
        this.addMouseMotionListener(handler);
    }
    /**
     * Draw grid
     *
     * @param g Graphics
     */
    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);  //задаем серый цвет
        int width_y = width / step;
        int height_x = height / step;
        for (int x = 0; x < width_y * step+1; x += step) {  // цикл от центра до правого края
            g.drawLine(x, 0, x, height_x * step);    // вертикальная линия
        }
//        for (int x = 0; x < width_y * step; x -= step) {  // цикл от центра до леваого края
//            g.drawLine(x, 0, x, height_x * step);   // вертикальная линия
//        }
        for (int y = 0; y < height_x * step+1; y += step) {  // цикл от центра до верхнего края
            g.drawLine(0, y, width_y * step, y);    // горизонтальная линия
        }

//        for (int y = 0; y < height_x * step; y -= step) {  // цикл от центра до леваого края
//            g.drawLine(0, y, width_y * step, y);    // горизонтальная линия
//        }
    }
    private void drawGraphic(Graphics g) {
        g.setColor(graphicColor); // устанавливаем цвет графика

//        for(int x=0; x<width; x++){           // делаем цикл с левой стороны экрана до правой
//            int realX = x - width/2;   // так, как слева от оси OX минус, то отнимаем от текущей точки центральную точку
//            double rad = realX/30.0;   // переводим текущую коориднату в радианы, 30 пикселей по ширине == 1 радиану
//            double sin = Math.atan(rad);       // вычисляем синус угла
//            int y = height/2 +  (sin * 90);  // переводим значение синуса в координату нашей системы
//
//            g.drawOval(x, y, 2, 2);   // рисуем кружок в этой точке
//        }
    }

    /**
     * Sets name and color.
     *
     * @param name  the name
     * @param color the color
     */
    public void setNameAndColor(String name, String color) {
        try {
            this.graphicColor = Color.decode(color);
        } catch (Exception e) {
            this.graphicColor = Color.RED;
        }
        repaint();
    }

    /**
     * Init game
     *
     * @param height the height
     * @param width  the width
     */
    public void init_game(int height, int width) {

        height_x = height / step - 1;
        width_y = width / step - 1;
        new_desk = new Cell[height_x + 1][width_y + 1];
        int bombx = 0;
        int bomby = 0;
        // create desk with cells
        for (int x = 0; x < height_x + 1; x++) {
            for (int y = 0; y < width_y + 1; y++)
                new_desk[x][y] = new Cell(false, x, y, 0);
        }
        /**
         * put bombs randomly
         */
        for (int bombs = 0; bombs < 20; bombs++) {
            bombx = (int) (1 +  (Math.random() *  (height_x - 1)));
            bomby = (int) (1 +  (Math.random() *  (width_y - 1)));
            drop_bomb(bombx, bomby);
        }
        // for tests
        for (int xx = 0; xx < height_x + 1; xx++) {
            count++;
            for (int yy = 0; yy < width_y + 1; yy++)
                System.out.print(new_desk[xx][yy].getCount() + "|");
            System.out.println();
        }
        //for tests
    }
    /**
     * init bombs on the desk
     *
     * @param x the x
     * @param y the y
     */
    public void drop_bomb(int x, int y) {
        try {
            System.out.println("x-" + x + ", y-" + y);
            if (!new_desk[x][y].isBomb()) {
                new_desk[x][y].setBomb(true);
                new_desk[x][y].setCount(9);
                // x-1,y-1
                if ((x - 1 >= 0 || y - 1 >= 0) && !new_desk[x - 1][y - 1].isBomb())
                    new_desk[x - 1][y - 1].setCount((new_desk[x - 1][y - 1].getCount()) + 1);
                // x-1,y
                if ((x - 1 >= 0) && !new_desk[x - 1][y].isBomb())
                    new_desk[x - 1][y].setCount(new_desk[x - 1][y].getCount() + 1);
                // x-1,y+1
                if ((y + 1 <= width_y + 1 || x - 1 >= 0) && !new_desk[x - 1][y + 1].isBomb())
                    new_desk[x - 1][y + 1].setCount(new_desk[x - 1][y + 1].getCount() + 1);
                // x,y-1
                if ((y - 1 >= 0) && !new_desk[x][y - 1].isBomb())
                    new_desk[x][y - 1].setCount(new_desk[x][y - 1].getCount() + 1);
                // x,y+1
                if ((y + 1 <= width_y + 1) && !new_desk[x][y + 1].isBomb())
                    new_desk[x][y + 1].setCount(new_desk[x][y + 1].getCount() + 1);
                //x+1,y-1
                if ((x + 1 <= height_x + 1 || y - 1 >= 0) && !new_desk[x + 1][y - 1].isBomb())
                    new_desk[x + 1][y - 1].setCount(new_desk[x + 1][y - 1].getCount() + 1);
                //x+1,y
                if ((x + 1 <= height_x + 1) && !new_desk[x + 1][y].isBomb())
                    new_desk[x + 1][y].setCount(new_desk[x + 1][y].getCount() + 1);
                //x+1,y+1
                if ((y + 1 <= width_y + 1 || x + 1 <= height_x + 1) && !new_desk[x + 1][y + 1].isBomb())
                    new_desk[x + 1][y + 1].setCount(new_desk[x + 1][y + 1].getCount() + 1);

            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e);
        }
    }
    public void defeat()
    {
        BufferedImage  miner = null;
        BufferedImage  clear = null;
        BufferedImage  image_1 = null;
        BufferedImage  image_2 = null;
        BufferedImage  image_3 = null;
        BufferedImage  image_4 = null;
        BufferedImage  image_5 = null;
        BufferedImage  image_6 = null;
        BufferedImage  image_7 = null;
        BufferedImage  image_8 = null;
        BufferedImage  image_0 = null;
        File image_file = new File("miner20.png");
        File image0 = new File("clear0.png");
        File image1 = new File("clear1.png");
        File image2 = new File("clear2.png");
        File image3 = new File("clear3.png");
        File image4 = new File("clear4.png");
        File image5 = new File("clear5.png");
        File image6 = new File("clear6.png");
        File image7 = new File("clear7.png");
        File image8 = new File("clear8.png");
        File image_claer = new File("clear20.png");

        try {
            clear=ImageIO.read(image_claer);
            miner=ImageIO.read(image_file);
            image_0 = ImageIO.read(image0);
            image_1 = ImageIO.read(image1);
            image_2 = ImageIO.read(image2);
            image_3 = ImageIO.read(image3);
            image_4 = ImageIO.read(image4);
            image_5 = ImageIO.read(image5);
            image_6 = ImageIO.read(image6);
            image_7 = ImageIO.read(image7);
            image_8 = ImageIO.read(image8);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        height_x=height/step;
        width_y=width/step;
        g = getGraphics();
        for (int x = 0; x < height_x ; x++) {
            System.out.print("\n");
        for (int y = 0; y < width_y ; y++) {
                switch (new_desk[x][y].getCount()) {
                    case 0:
                        g.drawImage(image_0,  y * step+1,  x * step, null);
                        System.out.print(new_desk[x][y].getCount()+"|");
                        break;
                    case 1:
                        g.drawImage(image_1,  y * step+1,  x * step, null);
                        System.out.print(new_desk[x][y].getCount()+"|");
                        break;
                    case 2:
                        g.drawImage(image_2,  y * step+1,  x * step, null);
                        System.out.print(new_desk[x][y].getCount()+"|");
                        break;
                    case 3:
                        g.drawImage(image_3,  y * step+1,  x * step, null);
                        System.out.print(new_desk[x][y].getCount()+"|");
                        break;
                    case 4:
                        g.drawImage(image_4,  y * step+1,  x * step, null);
                        System.out.print(new_desk[x][y].getCount()+"|");
                        break;
                    case 5:
                        g.drawImage(image_5,  y * step+1,  x * step, null);
                        System.out.print(new_desk[x][y].getCount()+"|");
                        break;
                    case 6:
                        g.drawImage(image_6,  y * step+1,  x * step, null);
                        System.out.print(new_desk[x][y].getCount()+"|");
                        break;
                    case 7:
                        g.drawImage(image_7,  y * step+1,  x * step, null);
                        System.out.print(new_desk[x][y].getCount()+"|");
                        break;
                    case 8:
                        g.drawImage(image_8,  y * step+1,  x * step, null);
                        System.out.print(new_desk[x][y].getCount()+"|");
                        break;
                    case 9:
                        g.drawImage(miner,  y * step+1,  x * step, null);
                        System.out.print(new_desk[x][y].getCount()+"|");
                        break;
                }
            }
        }
    }
    /**
     * mousePressed
     */
    private class MyMouseHandler extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            x1 = e.getX();
            y1 = e.getY();
            /* Create an ARGB BufferedImage */
            File image_bomb_file = new File("miner20.png");
            File image_file = new File("clear20.png");
            BufferedImage img_bomb = null, image_clear = null;
            try {
                img_bomb = ImageIO.read(image_bomb_file);
                image_clear = ImageIO.read(image_file);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
             g = getGraphics();
            if (e.getButton() == MouseEvent.BUTTON1) {
                g.drawImage(image_clear,  (x1 / step) * step,  (y1 / step) * step, null);
                                System.out.println(x1+":"+y1);
//                g.draw3DRect(x1, y1, step, step, true);
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
                g.drawImage(img_bomb,  (x1 / step) * step,  (y1 / step) * step, null);
            }
        }
        public void mouseDragged(MouseEvent e) {
        }
    }
}
