import java.io.*;
import java.util.*;

public class LawnMowerSimulator {
    private static int lawnWidth, lawnHeight;
    private static List<Mower> mowers = new ArrayList<>();

    public static void main(String[] args) {
        try {
            readInputFile("input.txt");
            simulateMovements();
            printResults();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readInputFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String[] lawnDimensions = reader.readLine().split(" ");
        lawnWidth = Integer.parseInt(lawnDimensions[0]);
        lawnHeight = Integer.parseInt(lawnDimensions[1]);

        String line;
        while ((line = reader.readLine()) != null) {
            String[] initialPosition = line.split(" ");
            int x = Integer.parseInt(initialPosition[0]);
            int y = Integer.parseInt(initialPosition[1]);
            char orientation = initialPosition[2].charAt(0);
            String instructions = reader.readLine();

            mowers.add(new Mower(x, y, orientation, instructions));
        }
        reader.close();
    }

    private static void simulateMovements() {
        for (Mower mower : mowers) {
            for (char instruction : mower.instructions.toCharArray()) {
                mower.executeInstruction(instruction);
            }
        }
    }

    private static void printResults() {
        for (Mower mower : mowers) {
            System.out.println(mower);
        }
    }

    static class Mower {
        int x, y;
        char orientation;
        String instructions;

        Mower(int x, int y, char orientation, String instructions) {
            this.x = x;
            this.y = y;
            this.orientation = orientation;
            this.instructions = instructions;
        }

        void executeInstruction(char instruction) {
            switch (instruction) {
                case 'D': turnRight(); break;
                case 'G': turnLeft(); break;
                case 'A': moveForward(); break;
            }
        }

        void turnRight() {
            switch (orientation) {
                case 'N': orientation = 'E'; break;
                case 'E': orientation = 'S'; break;
                case 'S': orientation = 'W'; break;
                case 'W': orientation = 'N'; break;
            }
        }

        void turnLeft() {
            switch (orientation) {
                case 'N': orientation = 'W'; break;
                case 'W': orientation = 'S'; break;
                case 'S': orientation = 'E'; break;
                case 'E': orientation = 'N'; break;
            }
        }

        void moveForward() {
            switch (orientation) {
                case 'N': if (y < lawnHeight) y++; break;
                case 'E': if (x < lawnWidth) x++; break;
                case 'S': if (y > 0) y--; break;
                case 'W': if (x > 0) x--; break;
            }
        }

        @Override
        public String toString() {
            return x + " " + y + " " + orientation;
        }
    }
}
