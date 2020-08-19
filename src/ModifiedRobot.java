import kareltherobot.*;

public class ModifiedRobot extends Robot implements Directions {
    public ModifiedRobot(int street, int avenue, Direction facing, int beepers) {
        super(street, avenue, facing, beepers);
    }

    // Base Functions

    /**
     * @param times The number of times the robot should move
     * @param withBeeper Whether the robot will put a beeper each time it moves
     */
    public void go(int times, boolean withBeeper) {
        for (int i = 0; i < times; i++) {
            move();
            if (withBeeper) {
                putBeeper();
            }
        }
    }

    /**
     * @param times The number of times the robot should turn
     */
    public void turn(int times) {
        for (int i = 0; i < times; i++) {
            turnLeft();
        }
    }

    /**
     * @param towards The direction to turn towards
     */
    public void turnTowards(Direction towards) {
        int currentVal = getDirectionVal(direction());
        int towardsVal = getDirectionVal(towards);
        System.out.println(towardsVal);
        int turns = currentVal - towardsVal;
        if (turns < 0) {
            turns += 4;
        }
        turn(turns);
    }

    /**
     * @param times The number of times the robot should move diagonally
     * @param directions 2 directions that specify the direction of movement
     * @param withBeeper Whether the robot should put a beeper each time it moves diagonally
     */
    public void moveDiagonal(int times, Direction[] directions, boolean withBeeper) {
        if (directions.length != 2) {
            throw new IllegalArgumentException("Exactly 2 directions must be passed in");
        }
        for (int i = 0; i < times; i++) {
            for (int x = 0; x < directions.length; x++) {
                turnTowards(directions[x]);
                move();
            }
            if (withBeeper) {
                putBeeper();
            }
        }
    }


    /**
     * @param direction The direction to get the value for
     * @return The integer representation of the direction
     */
    private static int getDirectionVal(Direction direction) {
        if (direction == East) {
            return EastVal;
        }
        if (direction == South) {
            return SouthVal;
        }
        if (direction == West) {
            return WestVal;
        }
        return NorthVal;
    }

    // Shapes

    /**
     * @param length The length of each side of the square
     */
    public void drawSquare(int length) {
        Direction[] directions = {East, South, West, North};
        putBeeper();
        for (int i = 0; i < directions.length; i++) {
            turnTowards(directions[i]);
            go(length - 1, true);
        }
    }

    /**
     * @param base The base of the rectangle
     * @param height The height of the rectangle
     */
    public void drawRect(int base, int height) {
        Direction[] directions = {East, South, West, North};
        putBeeper();
        for (int i = 0; i < directions.length; i++) {
            turnTowards(directions[i]);
            go(i % 2 == 0 ? base - 1 : height - 1, true);
        }
    }

    /**
     * @param length The length of 2 sides of the triangle
     */
    public void drawTriangle(int length) {
        putBeeper();
        moveDiagonal(length - 1, new Direction[]{South, East}, true);
        turnTowards(West);
        go(length * 2 - 2, true);
        moveDiagonal(length - 1, new Direction[]{North, East}, true);
    }


    /**
     * @param length The length of each side of the diamond
     */
    public void drawDiamond(int length) {
        putBeeper();
        moveDiagonal(length - 1, new Direction[]{North, East}, true);
        moveDiagonal(length - 1, new Direction[]{South, East}, true);
        moveDiagonal(length - 1, new Direction[]{South, West}, true);
        moveDiagonal(length - 1, new Direction[]{North, West}, true);
    }
}
