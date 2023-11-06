import java.util.*;
import java.io.*;

public class Main {
    private static final String FILENAME = "input.txt";

    public static boolean hasDuplicate(String stream) {
        Set<Character> set = new HashSet<>();

        for (char c : stream.toCharArray()) {
            if (set.contains(c)) {
                return true;
            }
            set.add(c);
        }
        return false;
    }

    public static int firstUnique(String stream, int step) {
        for (int i = 0; i < stream.length(); i++) {
            if (!hasDuplicate(stream.substring(i, i + step))) {
                return i + step;
            }
        }
        return 0;
    }

    public static int partOne() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line = reader.readLine();
            return firstUnique(line, 4);
        }
    }

    public static int partTwo() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line = reader.readLine();
            return firstUnique(line, 14);
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Part One: " + partOne());
        System.out.println("Part Two: " + partTwo());
    }
}