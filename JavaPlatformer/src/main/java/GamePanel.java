import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;



public class GamePanel extends JPanel implements Runnable, KeyListener {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    private Graphics2D graphic;
    private int FPS = 30;
    private int targetTime = 1000/FPS;
    private Levels levels;
    private Player lordBuzzworth;


    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }



    public void run() {
        init();

        long startTime;
        long urdTime;
        long waitTime;

        while(running) {
            startTime = System.nanoTime();

            update();
            render();
            draw();

            urdTime = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - urdTime;

            try {
                Thread.sleep(waitTime);
            } catch (Exception e) {

            }

        }
    }

    private void init() {
        running = true;
        image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
        graphic = (Graphics2D) image.getGraphics();
        levels = new Levels("Level1.txt",32);
        lordBuzzworth = new Player(levels);
        lordBuzzworth.setX(50);
        lordBuzzworth.setY(50);
    }


    private void update() {
        levels.update();
        lordBuzzworth.update();
    }

    private void render() {

        levels.draw(graphic);
        lordBuzzworth.draw(graphic);

    }

    private void draw() {
        Graphics graphics = getGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_LEFT) {
            lordBuzzworth.setLeft(true);
        }

        if (code == KeyEvent.VK_RIGHT) {
            lordBuzzworth.setRight(true);
        }

        if (code == KeyEvent.VK_W) {
            lordBuzzworth.setJumping(true);
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_LEFT) {
            lordBuzzworth.setLeft(false);
        }
        if (code == KeyEvent.VK_RIGHT) {
            lordBuzzworth.setRight(false);
        }

    }
}
