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

    }

    public void moveDiagonal(int times, Direction[] directions) {
        if (directions.length != 2) {
            throw new IllegalArgumentException("directions must be of length 2");
        }
        for (int i = 0; i < times; i++) {
            System.out.println(i);
        }
    }
}
