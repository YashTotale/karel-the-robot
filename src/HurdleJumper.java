import kareltherobot.Directions;
import kareltherobot.World;

import javax.swing.*;

public class HurdleJumper implements Directions {
        ModifiedRobot hurdler = new ModifiedRobot(1,1, East, 0);

        public static void main(String[] args) {
            new HurdleJumper().start();
        }

        public void start() {
            int hurdles = loadWorld();

            int maxHeight = 0;
            int maxWidth = 0;
            int minHeight = Integer.MAX_VALUE;

            for(int i = 0; i < hurdles; i ++) {
                int w = findHurdle();
                int h = climbHurdle();
                clearHurdle();
                if(h > maxHeight) {
                    maxHeight = h;
                }
                if(h < minHeight) {
                    minHeight = h;
                }
                if(w > maxWidth) {
                    maxWidth = w;
                }
            }

            System.out.println("The highest hurdle had a height of: " + maxHeight);
            System.out.println("The largest distance between hurdles was: " + maxWidth);
            System.out.println("The smallest hurdle had a height of: " + minHeight);

        }

        /**
         * This method assumes the Robot is named hurdler and is facing East
         * This moves hurdler to the next wall (hurdle).
         * @return The number of moves it took to get to the next hurdle
         */
        private int findHurdle() {
            return hurdler.tillWall();
        }

        /**
         * This method assumes the Robot is named hurdler, is facing East and
         * is at the base of the hurdle.
         * This moves the Robot to the top of the hurdle so that it can clear
         * the wall.
         * @return The number of steps to get above the hurdle (height)
         */
        private int climbHurdle() {
            int i = 0;
            while(!hurdler.frontIsClear()) {
                hurdler.turnTowards(North);
                hurdler.move();
                hurdler.turnTowards(East);
                i++;
            }
            return i;
        }

        /**
         * Moves the Robot (hurdler) over the wall and moves it to the ground so
         * that the Robot has its back to the hurdle and is facing the next one.
         */
        private void clearHurdle() {
            hurdler.move();
            hurdler.turnTowards(South);
            while(hurdler.frontIsClear()) {
                hurdler.move();
            }
            hurdler.turnTowards(East);
        }

        private int loadWorld() {
            String hurdlesStr = JOptionPane.showInputDialog("Enter the number of hurdles");
            int hurdles = Integer.parseInt(hurdlesStr);
            int maxHeight = 0;
            int first = 5;
            for(int i = 0; i < hurdles; i++) {
                String heightStr = JOptionPane.showInputDialog("Enter the height of hurdle #" + (i+1));
                int height = Integer.parseInt(heightStr);
                if(maxHeight < height) {
                    maxHeight = height;
                }
                World.placeNSWall(1, first + (i * 5), height);
            }
            World.setVisible(true);
            World.setSize(maxHeight + 2, 2 + hurdles*5);
            World.setDelay(5);
            return hurdles;
    }
}
