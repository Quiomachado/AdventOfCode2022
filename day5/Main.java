import java.util.*;
import java.io.*;
import java.util.regex.*;

public class Main {
    private static final String INPUT_FILE = "input.txt";
    private static Map<Integer, List<Character>> stacks = new HashMap<>();
    private static List<List<Integer>> moves = new ArrayList<>();

    public static void parseFile() throws IOException {
        stacks.clear();
        moves.clear();
        List<String> lines = new ArrayList<>();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            while (!((line = reader.readLine()).isEmpty())) {
                lines.add(line);
            }
            for (String l : lines.get(lines.size() - 1).trim().split("\\s+")) {
                stacks.put(Integer.parseInt(l), new ArrayList<>());
            }
            lines.remove(lines.size() - 1);
            for (int k = lines.size() - 1; k >= 0; k--) {
                String l = lines.get(k);
                for (int i = 1, j = 1; i < l.length(); i += 4, j++) {
                    if (l.charAt(i) == ' ') {
                        continue;
                    }
                    stacks.get(j).add(l.charAt(i));
                }
            }
            lines.clear();
            while ((line = reader.readLine()) != null) {
                List<Integer> move = new ArrayList<>();
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    int number = Integer.parseInt(matcher.group());
                    move.add(number);
                }
                moves.add(move);
            }
        }
    }

    public static String partOne() throws IOException {
        parseFile();
        String result = "";
        for (List<Integer> move : moves) {
            int count = move.get(0);
            int from = move.get(1);
            int to = move.get(2);
            for (int i = 0; i < count; i++) {
                char c = stacks.get(from).remove(stacks.get(from).size() - 1);
                stacks.get(to).add(c);
            }
        }
        for (int i = 1; i <= stacks.size(); i++) {
            result += stacks.get(i).get(stacks.get(i).size() - 1);
        }
        return result;
    }

    public static String partTwo() throws IOException {
        parseFile();
        String result = "";
        for (List<Integer> move : moves) {
            int count = move.get(0);
            int from = move.get(1);
            int to = move.get(2);
            int size = stacks.get(from).size();
            for (int i = 0; i < count; i++) {
                char c = stacks.get(from).remove(size - count);
                stacks.get(to).add(c);
            }
        }
        for (int i = 1; i <= stacks.size(); i++) {
            result += stacks.get(i).get(stacks.get(i).size() - 1);
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Part One: " + partOne());
        System.out.println("Part Two: " + partTwo());
    }
}