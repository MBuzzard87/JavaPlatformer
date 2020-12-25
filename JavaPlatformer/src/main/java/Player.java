import java.awt.*;


public class Player {

    private double x;
    private double y;
    private double dx;
    private double dy;

    private int width;
    private int height;

    private boolean left;
    private boolean right;
    private boolean jumping;
    private boolean falling;

    private double moveSpeed;
    private double maxSpeed;
    private double maxFallingSpeed;
    private double stopSpeed;
    private double jumpStart;
    private double gravity;
    private Levels levels;
    private boolean topLeft;
    private boolean topRight;
    private boolean bottomLeft;
    private boolean bottomRight;


    public Player(Levels level) {
        levels = level;

        width = 20;
        height = 20;
        moveSpeed = 0.6;
        maxSpeed = 4.2;
        maxFallingSpeed = 12;
        stopSpeed = 0.3;
        jumpStart = -11;
        gravity = 0.7;
    }


    public void setX(int i) {
        x = i;
    }

    public void setY(int i) {
        y = i;
    }

    public void setLeft(boolean l) {
        left = l;
    }

    public void setRight(boolean r) {
        right = r;
    }

    public void setJumping(boolean j) { jumping = j; }

    private void corners(double x, double y) {
        int leftTile = levels.getColumn((int) (x - width / 2));
        int rightTile = levels.getColumn((int) (x + width / 2) - 1);
        int topTile = levels.getRow((int) (y - height / 2));
        int bottomTile = levels.getRow((int) (y + height / 2) - 1);
        topLeft = levels.getLevel(topTile,leftTile) == 0;
        topRight = levels.getLevel(topTile, rightTile) == 0;
        bottomLeft = levels.getLevel(bottomTile, leftTile) == 0;
        bottomRight = levels.getLevel(bottomTile, rightTile) == 0;
    }

    public void update() {
        if(left) {
            dx -= moveSpeed;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else if (right) {
            dx += moveSpeed;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else {
            if (dx > 0) {
                dx -= stopSpeed;
                if(dx < 0) {
                    dx = 0;
                }
            } else if (dx < 0) {
                dx += stopSpeed;
                if (dx > 0) {
                    dx = 0;
                }
            }
        }

        if (jumping) {
            dy = jumpStart;
            falling = true;
            jumping = false;
        }

        if (falling) {
            dy += gravity;
            if (dy > maxFallingSpeed) {
                dy = maxFallingSpeed;
            }
        } else {
            dy = 0;
        }

        int currentCol = levels.getColumn((int)x);
        int currentRow = levels.getRow((int)y);

        double toX = x + dx;
        double toY = y + dy;

        double tempX = x;
        double tempY = y;

        corners(x, toY);
        if (dy < 0) {
            if(topLeft || topRight) {
                dy = 0;
                tempY = (currentRow * levels.getLevelSize()) + (height / 2);
            } else {
                tempY += dy;
            }
        }

        if (dy > 0) {
            if (bottomLeft || bottomRight) {
                dy = 0;
                tempY = ((currentRow + 1) * levels.getLevelSize()) - (height / 2);
            } else {
                tempY += dy;
            }
        }

        corners(toX,y);
        if (dx < 0) {
            if(topLeft || bottomLeft) {
                dx = 0;
                tempX = (currentCol * levels.getLevelSize()) + (width / 2);
            } else {
                tempX += dx;
            }
        }
        if (dx > 0) {
            if(topRight || bottomRight) {
                dx = 0;
                tempX = ((currentCol + 1) * levels.getLevelSize()) - (width / 2);
            } else {
                tempX += dx;
            }
        }

        if (!falling) {
            corners(x,y + 1);
            if (!bottomLeft && !bottomRight) {
                falling = true;
            }
        }

        x = tempX;
        y = tempY;

        levels.setX((int) (GamePanel.WIDTH / 2 - x));
        levels.setY((int) (GamePanel.HEIGHT / 2 - y));

    }

    public void draw(Graphics2D graphics2D) {
        int levelX = levels.getX();
        int levelY = levels.getY();

        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect((int)(levelX + x - width / 2), (int)(levelY + y - height / 2), width, height);
    }

}

