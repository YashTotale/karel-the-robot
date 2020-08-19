import java.util.Scanner;

import kareltherobot.Directions;
import kareltherobot.Robot;
import kareltherobot.World;

public class RobotStarter implements Directions {

    private static int length = 10;

    public static void main(String[] args) {
        //Create our robot
        ModifiedRobot bot = new ModifiedRobot(length, 1, North, length * 4 - 3);
        //Configure World
        World.setVisible(true); //Allows us to see the world
        World.setSize(length * 3, length * 3); //Sets the size of the world
        World.showSpeedControl(true); //Shows a slider that controls the speed
        //World.setDelay(0) // <- Enable when you want the finished result as soon as you run the program


        bot.drawDiamond(length);
    }
}
