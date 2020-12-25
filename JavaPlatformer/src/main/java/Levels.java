import java.io.*;
import java.awt.*;
import java.awt.Color;


public class Levels {

    private int x;
    private int y;
    private int[][] level;
    private int levelSize;
    private int levelWidth;
    private int levelHeight;
    public static final Color SKY_BLUE = new Color(51,204,255);
    public static final Color WATER = new Color(0,0,153);
    public static final Color GRASS = new Color(0,204,0);
    public static final Color DIRT_PERIMETER = new Color(153,51,0);


    public Levels(String s, int levelSize) {
        this.levelSize = levelSize;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(s));
            levelWidth = Integer.parseInt(bufferedReader.readLine());
            levelHeight = Integer.parseInt(bufferedReader.readLine());
            level = new int[levelHeight][levelWidth];

            for (int i = 0; i < levelHeight; i++) {
                String line = bufferedReader.readLine();
                String[] splitLine = line.split(" ");
                for (int j = 0; j < levelWidth; j++) {
                    level[i][j] = Integer.parseInt(splitLine[j]);

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

    }

    public void draw(Graphics2D graphic) {

        for (int i = 0; i < levelHeight; i++) {
            for (int j = 0; j < levelWidth; j++) {
                int levelColor = level[i][j];

                if (levelColor == 0) {
                    graphic.setColor(DIRT_PERIMETER);
                } else if (levelColor == 1) {
                    graphic.setColor(WATER);
                } else if (levelColor == 2) {
                    graphic.setColor(GRASS);
                } else if (levelColor == 3) {
                    graphic.setColor(SKY_BLUE);
                } else if (levelColor == 4) {
                    graphic.setColor(Color.WHITE);
                }
                graphic.fillRect(x + j * levelSize, y + i * levelSize, levelSize, levelSize);
            }
        }
    }

    public void setX(int i) {
        x = i;
    }
    public void setY(int i) {
        y = i;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColumn(int x) {
        return x / levelSize;
    }

    public int getRow(int y) {
        return y / levelSize;
    }

    public int getLevel(int y, int x) {
        return level[y][x];
    }

    public int getLevelSize() {
        return levelSize;
    }
}
