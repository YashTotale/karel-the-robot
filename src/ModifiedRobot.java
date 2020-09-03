import kareltherobot.Robot;
import kareltherobot.Directions;

public class ModifiedRobot extends Robot implements Directions {
    public ModifiedRobot(int street, int avenue, Direction facing, int beepers) {
        super(street, avenue, facing, beepers);
    }

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
     * @param street The vertical value
     * @param avenue The horizontal value
     * @param withBeeper Should put beeper after moves are finished
     */
    public void moveTo(int street, int avenue, boolean withBeeper) {
        int currentStreet = street();
        int currentAvenue = avenue();

        int verticalMoves = street - currentStreet;
        Direction verticalDirection = verticalMoves >= 0 ? North : South;

        turnTowards(verticalDirection);
        go(Math.abs(verticalMoves), false);

        int horizontalMoves = avenue - currentAvenue;
        Direction horizontalDirection = horizontalMoves >= 0 ? East : West;

        turnTowards(horizontalDirection);
        go(Math.abs(horizontalMoves), false);

        if(withBeeper) {
            putBeeper();
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
        //The integer values of the current and towards directions
        int currentVal = getDirectionVal(direction());
        int towardsVal = getDirectionVal(towards);
        //The integer number of turns
        int turns = currentVal - towardsVal;
        //Turns a negative turn value to a positive one
        if (turns < 0) {
            turns += 4;
        }
        //Turn
        turn(turns);
    }

    /**
     * @param times The number of times the robot should move diagonally
     * @param directions 2 directions that specify the direction of movement
     * @param withBeeper Whether the robot should put a beeper each time it moves diagonally
     * @throws IllegalArgumentException Exactly 2 directions are not given
     * @throws IllegalArgumentException The 2 directions are the same
     */
    public void moveDiagonal(int times, Direction[] directions, boolean withBeeper) {
        if (directions.length != 2) {
            throw new IllegalArgumentException("Exactly 2 directions must be passed in");
        }
        if(directions[0] == directions[1]) {
            throw new IllegalArgumentException("The directions must be unique");
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
     * @param drawTowards The direction to draw towards initially
     */
    public void drawSquare(int length, Direction drawTowards) {
        Direction[] directions = {East, South, West, North};
        putBeeper();
        for (int i = 0; i < directions.length; i++) {
            int index = i + getDirectionVal(drawTowards);
            Direction direction = index < directions.length ? directions[index] : directions[index - directions.length];
            turnTowards(direction);
            go(length - 1, true);
        }
    }

    /**
     * @param base The base of the rectangle
     * @param height The height of the rectangle
     * @param drawTowards The direction to draw towards initially
     */
    public void drawRect(int base, int height, Direction drawTowards) {
        Direction[] directions = {East, South, West, North};
        putBeeper();
        for (int i = 0; i < directions.length; i++) {
            int index = i + getDirectionVal(drawTowards);
            Direction direction = index < directions.length ? directions[index] : directions[index - directions.length];
            turnTowards(direction);
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

    /**
     * @param length Length of the sides of the cross (will be converted to an odd number)
     */
    public void drawCross(int length) {
        int half = length /2;
        putBeeper();
        turnTowards(East);
        go(2 * half, true);
        turnTowards(West);
        go(half, false);
        turnTowards(North);
        go(half + 1, false);
        turnTowards(South);
        go(half * 2 + 1, true);
    }

    /**
     * @param radius The radius of the circle
     * @param centerHor The x-coordinate of the circle
     * @param centerVert The y-coordinate of the circle
     */
    public void drawCircle(int radius, int centerHor, int centerVert) {
        int startX = centerHor - radius;

        moveTo(centerVert, startX, true);
        turnTowards(East);

        for(int i = 0; i < 2; i++) {
            int previousY = centerVert;
            for (int x = startX + 1; x <= (radius * 2) + startX; x++) {
                int yValue = circleEq(radius, x, centerHor, centerVert, i != 0);
                moveTo(yValue, x, true);
                if (Math.abs(yValue - previousY) > 1) {
                    int min = Math.min(yValue, previousY);
                    int max = Math.max(yValue, previousY);
                    for (int y = min + 1; y < max; y++) {
                        int xVal = circleEq(radius, y, centerVert, centerHor, x < centerHor);
                        moveTo(y, xVal, true);
                    }
                }
                previousY = yValue;
            }
        }
    }

    private int circleEq(int radius, int xOrY, int centerVal, int otherCenterVal, boolean isNegative) {
        final int radiusSq = (radius * radius);
        final int distSq = (xOrY - centerVal) * (xOrY - centerVal);
        final double sqRoot = isNegative ? -1 * Math.sqrt( radiusSq - distSq) : Math.sqrt( radiusSq - distSq);
        final double withDecimals = sqRoot + otherCenterVal;
        return Math.toIntExact(Math.round(withDecimals));
    }
}
