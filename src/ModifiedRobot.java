import kareltherobot.*;

public class ModifiedRobot extends Robot implements Directions {
    public ModifiedRobot(int street, int avenue, Direction facing, int beepers) {
        super(street, avenue, facing, beepers);
    }

    public void go(int times) {
        for (int i = 0; i < times; i++) {
            move();
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
        if(turns < 0) {
            turns+=4;
        }
        turn(turns);
    }

    public void moveDiagonal(int times, Direction[] directions, boolean withBeeper) {
        if (directions.length != 2) {
            throw new IllegalArgumentException("Exactly 2 directions must be passed in");
        }
        for (int i = 0; i < times; i++) {
            for(int x = 0; x < directions.length; x++) {
                turnTowards(directions[x]);
                move();
            }
            if(withBeeper) {
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
}
