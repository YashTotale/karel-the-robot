import kareltherobot.*;

public class ModifiedRobot extends Robot implements Directions {
    public ModifiedRobot(int street, int avenue, Direction facing, int beepers) {
        super(street, avenue, facing, beepers);
    }

    // Base Functions

    public void go(int times, boolean withBeeper) {
        for (int i = 0; i < times; i++) {
            move();
            if (withBeeper) {
                putBeeper();
            }
        }
    }

    public void turn(int times) {
        for (int i = 0; i < times; i++) {
            turnLeft();
        }
    }

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

    public void drawSquare(int length) {
        Direction[] directions = {East, South, West, North};
        putBeeper();
        for (int i = 0; i < directions.length; i++) {
            turnTowards(directions[i]);
            go(length - 1, true);
        }
    }

    public void drawRect(int base, int height) {
        Direction[] directions = {East, South, West, North};
        putBeeper();
        for (int i = 0; i < directions.length; i++) {
            turnTowards(directions[i]);
            go(i % 2 == 0 ? base - 1 : height - 1, true);
        }
    }

    public void drawDiamond(int length) {
        putBeeper();
        moveDiagonal(length - 1, new Direction[]{North, East}, true);
        moveDiagonal(length - 1, new Direction[]{South, East}, true);
        moveDiagonal(length - 1, new Direction[]{South, West}, true);
        moveDiagonal(length - 1, new Direction[]{North, West}, true);
    }
}
