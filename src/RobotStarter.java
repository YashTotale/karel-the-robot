import java.util.Scanner;

import kareltherobot.Directions;
import kareltherobot.Robot;
import kareltherobot.World;

public class RobotStarter implements Directions {

    private static int length = 5;

    public static void main(String[] args) {
        //Create our robot
        ModifiedRobot bot = new ModifiedRobot(length, 1, East, length * 4 - 3);
        //Configure World
        World.setVisible(true);
        World.setSize(length * 3, length * 3);
        World.showSpeedControl(true);

        bot.putBeeper();
        moveDiagonal(North, East, length -1, true, bot);
        moveDiagonal(South, East, length -1, true, bot);
        moveDiagonal(South, West, length -1, true, bot);
        moveDiagonal(North, West, length -1, true, bot);
    }

    private static void turn(int times, Robot bot) {
        for (int i = 0; i < times; i++) {
            bot.turnLeft();
        }
    }

    private static void move(int times, Robot bot) {
        for (int i = 0; i < times; i++) {
            bot.move();
        }
    }

    private static void turnTowards(Direction towards, Robot bot) {
        int turns = getTurnsTowardsDirection(getDirection(bot), towards);
        turn(turns, bot);
    }

    private static void moveDiagonal(Direction vertically, Direction horizontally, int times, boolean withBeepers, Robot bot) {
        for (int i = 0; i < times; i++) {
            turnTowards(vertically, bot);
            move(1, bot);
            turnTowards(horizontally, bot);
            move(1, bot);
            if (withBeepers) {
                bot.putBeeper();
            }
        }
    }

    private static Direction getDirection(Robot bot) {
        return bot.facingNorth() ? North : bot.facingSouth() ? South : bot.facingEast() ? East : West;
    }

    private static int getIntValForDirection(Direction direction) {
        return East.equals(direction) ? 0 : North.equals(direction) ? 1 : West.equals(direction) ? 2 : 3;
    }

    private static int getTurnsTowardsDirection(Direction current, Direction towards) {
        int currentInt = getIntValForDirection(current);
        int towardsInt = getIntValForDirection(towards);
        int difference = towardsInt - currentInt;
        if (difference >= 0) {
            return difference;
        }
        return 4 + difference;
    }
}
