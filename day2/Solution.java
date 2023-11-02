import java.util.Map;
import java.util.HashMap;
import java.io.FileReader;
import java.io.BufferedReader;

enum Option {
    ROCK, PAPER, SCISSORS;

    private static final Map<Option, Option> WIN_MAP = new HashMap<>();
    static {
        WIN_MAP.put(ROCK, SCISSORS);
        WIN_MAP.put(PAPER, ROCK);
        WIN_MAP.put(SCISSORS, PAPER);
    }

    public Option beats() {
        return WIN_MAP.get(this);
    }

    public boolean beats(Option other) {
        return WIN_MAP.get(this) == other;
    }

    private static final Map<Option, Option> LOSE_MAP = new HashMap<>();
    static {
        LOSE_MAP.put(ROCK, PAPER);
        LOSE_MAP.put(PAPER, SCISSORS);
        LOSE_MAP.put(SCISSORS, ROCK);
    }

    public Option losesTo() {
        return LOSE_MAP.get(this);
    }

    public boolean losesTo(Option other) {
        return LOSE_MAP.get(this) == other;
    }

    private static final Map<Option, Integer> POINTS_MAP = new HashMap<>();
    static {
        POINTS_MAP.put(ROCK, 1);
        POINTS_MAP.put(PAPER, 2);
        POINTS_MAP.put(SCISSORS, 3);
    }

    public int getPoints() {
        return POINTS_MAP.get(this);
    }

    public static Option fromString(String s) {
        switch (s) {
            case "X":
            case "A":
                return ROCK;
            case "Y":
            case "B":
                return PAPER;
            case "Z":
            case "C":
                return SCISSORS;
            default:
                throw new IllegalArgumentException("Invalid option: " + s);
        }
    }
}

public class Solution {
    private static final String INPUT_FILE = "input.txt";

    static int calcOutcome(Option elf, Option player) {
        if (elf == player) {
            return 3;
        } else if (elf.beats(player)) {
            return 0;
        } else {
            return 6;
        }
    }

    static int partOne() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                Option elf = Option.fromString(tokens[0]);
                Option player = Option.fromString(tokens[1]);
                count += Solution.calcOutcome(elf, player);
                count += player.getPoints();
            }
            return count;
        }
    }

    static int partTwo() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            int count = 0;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                Option elf = Option.fromString(tokens[0]);
                Option player = null;
                switch (tokens[1]) {
                    case "X":
                        player = elf.beats();
                        break;
                    case "Y":
                        player = elf;
                        break;
                    case "Z":
                        player = elf.losesTo();
                        break;
                }
                count += Solution.calcOutcome(elf, player);
                count += player.getPoints();
            }
            return count;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Part one: " + Solution.partOne());
        System.out.println("Part two: " + Solution.partTwo());
    }

}