import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static final String INPUT_FILE = "input.txt";

    public static boolean isContained(String str1, String str2) {
        String strPair[] = str1.split("-");
        int pair1[] = new int[2];
        pair1[0] = Integer.parseInt(strPair[0]);
        pair1[1] = Integer.parseInt(strPair[1]);
        strPair = str2.split("-");
        int pair2[] = new int[2];
        pair2[0] = Integer.parseInt(strPair[0]);
        pair2[1] = Integer.parseInt(strPair[1]);
        if ((pair1[0] <= pair2[0] && pair1[1] >= pair2[1]) || (pair1[0] >= pair2[0] && pair1[1] <= pair2[1])) {
            return true;
        }
        return false;
    }

    public static boolean areOverlapping(String str1, String str2) {
        String strPair[] = str1.split("-");
        int pair1[] = new int[2];
        pair1[0] = Integer.parseInt(strPair[0]);
        pair1[1] = Integer.parseInt(strPair[1]);
        strPair = str2.split("-");
        int pair2[] = new int[2];
        pair2[0] = Integer.parseInt(strPair[0]);
        pair2[1] = Integer.parseInt(strPair[1]);
        if ((pair1[0] <= pair2[0] && pair2[0] <= pair1[1]) || (pair2[0] <= pair1[0] && pair1[0] <= pair2[1])) {
            return true;
        }
        return false;
    }

    public static int partOne() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            int count = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                String str[] = line.split(",");
                if (isContained(str[0], str[1])) {
                    count++;
                }
            }
            return count;
        }
    }

    public static int partTwo() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            int count = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                String str[] = line.split(",");
                if (areOverlapping(str[0], str[1])) {
                    count++;
                }
            }
            return count;
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("Part One: " + partOne());
            System.out.println("Part Two: " + partTwo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}