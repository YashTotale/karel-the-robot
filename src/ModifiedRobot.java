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
            nonRepeatBeeper();
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
     * Puts a beeper if a beeper is not already placed
     */
    public void nonRepeatBeeper() {
        if(!nextToABeeper()) {
            putBeeper();
        }
    }

    /**
     *
     * @return The number of moves till the next wall
     */
    public int tillWall() {
        int i = 0;
        while(frontIsClear()) {
            move();
            i++;
        }
        return i;
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
     * @param centerHor The x-coordinate of the center
     * @param centerVert The y-coordinate of the center
     */
    public void drawCircle(int radius, int centerHor, int centerVert) {
        //Starting x value
        int startX = centerHor - radius;

        //Move to the start, place beeper, turn East
        moveTo(centerVert, startX, false);
        turnTowards(East);

        //Loop through twice (for top and bottom semi circles)
        for(int i = 0; i < 2; i++) {
            int previousY = centerVert;
            //The start and ends are reversed for top and bottom semis
            int start = startX;
            int end = radius * 2 + startX;
            //Go until the semi circle is finished
            for (int x = i == 0 ? start : end; i == 0 ? x <= end : x >= start; x = i== 0 ? x+1 : x-1) {
                //The y value as an int for the x value
                int yValue = circleEq(radius, x, centerHor, centerVert, i != 0);
                //Move to it and place beeper
                moveTo(yValue, x, true);
                //If there are y gaps in between y values
                if (Math.abs(yValue - previousY) > 1) {
                    //Get the min and max of them
                    int min = Math.min(yValue, previousY);
                    int max = Math.max(yValue, previousY);
                    //Loop through the gaps
                    for (int y = min + 1; y < max; y++) {
                        //Place a beeper for the x value corresponding to the y gap in the gaps
                        int xVal = circleEq(radius, y, centerVert, centerHor, x < centerHor);
                        moveTo(y, xVal, true);
                    }
                }
                previousY = yValue;
            }
        }
    }

    /**
     * @param radius Radius of the circle
     * @param xOrY The value of the x or y coordinate in the equation
     * @param centerVal The corresponding vertical/horizontal shift to the x or y value
     * @param otherCenterVal The other vertical/horizontal shift
     * @param isNegative Whether the square root is negative
     * @return An x or y coordinate on the circle
     */
    private static final int circleEq(int radius, int xOrY, int centerVal, int otherCenterVal, boolean isNegative) {
        //Radius squared
        final int radiusSq = (radius * radius);
        //Distance squared (x-h)^2 or (y-k)^2
        final double distSq =  Math.pow((xOrY - centerVal), 2);
        //Positive or negative square root
        final double sqRoot = (isNegative ? -1 : 1) * Math.sqrt( radiusSq - distSq);
        //Add shift
        final double withDecimals = sqRoot + otherCenterVal;
        //Convert to int
        return Math.toIntExact(Math.round(withDecimals));
    }
}
