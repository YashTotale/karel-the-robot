import kareltherobot.Directions;
import kareltherobot.World;

import java.util.Scanner;

public class RobotStarter implements Directions {

    public static void main(String[] args) {
        //Prompts the user for the length of the diamond
        System.out.println("Enter the size of the diamond you want: ");
        Scanner keyboard = new Scanner(System.in);
        /**
         * The length of each side of the diamond
         */
        int length = keyboard.nextInt();
        keyboard.close();
        System.out.println("You selected: " + length);

        //Create our robot
        ModifiedRobot bot = new ModifiedRobot(length, 1, North, length * 4 - 3);
        //Configure World
        World.setVisible(true); //Allows us to see the world
        World.setSize(length * 3, length * 3); //Sets the size of the world
        World.showSpeedControl(false); //Shows a slider that controls the speed
        World.setDelay(1); // <- Sets the delay between each robot action

        bot.drawDiamond(length); //Draws a diamond
    }
}
