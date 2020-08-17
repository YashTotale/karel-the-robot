import java.util.Scanner;

import kareltherobot.Directions;
import kareltherobot.Robot;
import kareltherobot.World;

public class RobotStarter  implements Directions{
	
	public static void main(String[] args) {
		Robot bot = new Robot(3,1,East,30);

		World.setVisible(true);
		bot.move();
		bot.turnLeft();
		bot.putBeeper();
		bot.move();
		
	}

}
