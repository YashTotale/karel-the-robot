import kareltherobot.Directions;
import kareltherobot.World;

import javax.swing.JOptionPane;

public class RobotStarter implements Directions {

    public static void main(String[] args) {
        String answer = JOptionPane.showInputDialog("Enter the radius of the circle");
        int radius = Integer.parseInt(answer);

        //Configure World
        World.setVisible(true); //Allows us to see the world
        World.setSize(10, 10); //Sets the size of the world
        World.showSpeedControl(false); //Shows a slider that controls the speed
        World.setDelay(1); // <- Sets the delay between each robot action

        //Create our robot
        ModifiedRobot bot = new ModifiedRobot(1, 1, North, 100);
        bot.drawCircle(radius, 5, 5);

    }
}
