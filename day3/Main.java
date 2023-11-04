import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    private static final String INPUT_FILE = "input.txt";

    public static int getPriority(Character c) {
        if (Character.isUpperCase(c)) {
            return ((int) c - (int) 'A' + 27);
        }
        return ((int) c - (int) 'a' + 1);
    }

    public static Set<Character> turnToCharacters(String str) {
        Set<Character> set = new HashSet<Character>();
        for (char c : str.toCharArray()) {
            set.add(c);
        }
        return set;
    }

    public static char commonChar(Set<Character> set, String str) {
        char value = ' ';
        for (char c : str.toCharArray()) {
            if (set.contains(c)) {
                value = c;
            }
        }
        return value;
    }

    public static char commonChar(Set<Character> set1, Set<Character> set2, String str) {
        char value = ' ';
        for (char c : str.toCharArray()) {
            if (set1.contains(c) && set2.contains(c)) {
                value = c;
            }
        }
        return value;
    }

    public static int partOne() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                String firstHalf = line.substring(0, line.length() / 2);
                String secondHalf = line.substring(line.length() / 2);
                count += getPriority(commonChar(turnToCharacters(firstHalf), secondHalf));
            }
            return count;
        }
    }

    public static int partTwo() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            List<String> threeLines = new ArrayList<String>();
            int count = 0;
            while ((line = reader.readLine()) != null) {
                threeLines.add(line);
                if (threeLines.size() == 3) {
                    count += getPriority(
                            commonChar(turnToCharacters(threeLines.get(0)), turnToCharacters(threeLines.get(1)), line));
                    threeLines.clear();
                }
            }
            return count;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Part One: " + partOne());
        System.out.println("Part Two: " + partTwo());
    }
}