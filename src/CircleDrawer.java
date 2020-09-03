import kareltherobot.Directions;
import kareltherobot.World;

import javax.swing.JOptionPane;

public class CircleDrawer implements Directions {

    public static void main(String[] args) {
        String answer = JOptionPane.showInputDialog("Enter the radius of the circle");
        final int radius = Integer.parseInt(answer);

        //Configure World
        World.showSpeedControl(false); //Shows a slider that controls the speed
        World.setVisible(true); //Allows us to see the world
        World.setSize((radius * 2) + 2, (radius * 2) + 2); //Sets the size of the world
        World.setDelay(4); // <- Sets the delay between each robot action

        //Create our robot
        ModifiedRobot bot = new ModifiedRobot(1, 1, North, Math.toIntExact(Math.round(Math.PI * radius * 2)));
        bot.drawCircle(radius, radius + 1, radius + 1);

    }
}
