import kareltherobot.Directions;
import kareltherobot.World;

import javax.swing.JOptionPane;

public class CircleDrawer implements Directions {

    public static void main(String[] args) {
        final String worldHeightStr = JOptionPane.showInputDialog("Height of the world ('auto' for auto calculation):");
        final String worldLengthStr = JOptionPane.showInputDialog("Length of the world ('auto' for auto calculation):");
        final String radiusStr = JOptionPane.showInputDialog("Radius of the circle:");
        final String centerXStr = JOptionPane.showInputDialog("X-coordinate of the center of the circle (MUST be greater than radius):");
        final String centerYStr = JOptionPane.showInputDialog("Y-coordinate of the center of the circle (MUST be greater than radius):");

        final int radius = Integer.parseInt(radiusStr);
        final int centerX = Integer.parseInt(centerXStr);
        final int centerY = Integer.parseInt(centerYStr);
        final int worldHeight = worldHeightStr.equals("auto") ? centerY * 2 : Integer.parseInt(worldHeightStr);
        final int worldLength = worldLengthStr.equals("auto") ? centerX * 2 : Integer.parseInt(worldLengthStr);

        //Configure World
        World.showSpeedControl(false); //Shows a slider that controls the speed
        World.setVisible(true); //Allows us to see the world
        World.setSize(worldHeight, worldLength); //Sets the size of the world
        World.setDelay(1); // <- Sets the delay between each robot action

        //Create our robot
        ModifiedRobot bot = new ModifiedRobot(1, 1, North, Math.toIntExact(Math.round(Math.PI * radius * 2)));
        bot.drawCircle(radius, centerX, centerY);
        bot.moveTo(1, 1, false);
    }
}
