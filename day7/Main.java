import java.util.*;
import java.io.*;

interface Type {
    public String getName();

    public String getPath();

    public int getSize();

    public int getSize(Map<String, Integer> dirSizes);

    public void print(BufferedWriter writer);
}

class Directory implements Type {
    private String _name;
    private List<Type> _children;
    private Directory _parent;

    public Directory(String Name, Directory parent) {
        _name = Name;
        _children = new ArrayList<>();
        _parent = parent;
    }

    public void addChild(Type type) {
        _children.add(type);
    }

    public Type getChild(String name) throws Exception {
        for (Type type : _children) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public List<Type> getChildren() {
        return _children;
    }

    public Directory getParent() {
        return _parent;
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public String getPath() {
        if (_parent == null) {
            return "/";
        } else {
            return _parent.getPath() + _name + "/";
        }
    }

    @Override
    public int getSize() {
        int size = 0;
        for (Type type : _children) {
            size += type.getSize();
        }
        return size;
    }

    @Override
    public int getSize(Map<String, Integer> dirSizes) {
        int size = 0;
        for (Type type : _children) {
            size += type.getSize(dirSizes);
        }
        dirSizes.put(getPath(), size);
        return size;
    }

    @Override
    public String toString() {
        return " - " + _name + " (dir)";
    }

    @Override
    public void print(BufferedWriter writer) {
        try {
            if (_parent != null) {
                for (char c : getPath().toCharArray()) {
                    if (c == '/') {
                        writer.write("  ");
                    }

                }
            }
            writer.write(this.toString());
            writer.newLine();
            for (Type type : _children) {
                type.print(writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class File implements Type {
    private String _name;
    private Directory _parent;
    private int _size;

    public File(String name, Directory parent, int size) {
        _name = name;
        _parent = parent;
        _size = size;
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public String getPath() {
        if (_parent == null) {
            return "/" + _name;
        } else {
            return _parent.getPath() + _name;
        }
    }

    @Override
    public int getSize() {
        return _size;
    }

    @Override
    public int getSize(Map<String, Integer> dirSizes) {
        return _size;
    }

    @Override
    public String toString() {
        return " - " + _name + " (file, size=" + _size + ")";
    }

    @Override
    public void print(BufferedWriter writer) {
        try {
            for (char c : getPath().toCharArray()) {
                if (c == '/') {
                    writer.write("  ");
                }

            }
            writer.write(this.toString());
            writer.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    private static final String INPUT_FILE = "input.txt";
    public Map<String, Integer> dirSizes = new HashMap<>();

    public Directory parseFile() {
        Directory root = new Directory("/", null);
        Directory current = root;
        String line;
        String[] tokens;
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            while ((line = reader.readLine()) != null) {
                tokens = line.split(" ");
                switch (tokens[0]) {
                    case "$":
                        switch (tokens[1]) {
                            case "ls":
                                break;
                            case "cd":
                                switch (tokens[2]) {
                                    case "..":
                                        current = current.getParent();
                                        break;

                                    case "/":
                                        current = root;
                                        break;

                                    default:
                                        current = (Directory) current.getChild(tokens[2]);
                                        break;
                                }
                                break;
                        }
                        break;
                    case "dir":
                        current.addChild(new Directory(tokens[1], current));
                        break;
                    default:
                        current.addChild(new File(tokens[1], current, Integer.parseInt(tokens[0])));
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    public Map<String, Integer> dirSizes(Directory root) {
        if (dirSizes.keySet().contains(root.getPath())) {
            return dirSizes;
        }
        root.getSize(dirSizes);
        return dirSizes;
    }

    public int partOne() {
        Directory root = parseFile();
        Map<String, Integer> dirSizes = dirSizes(root);
        int size = 0;
        for (Integer key : dirSizes.values()) {
            if (key < Integer.valueOf(100000)) {
                size += key;
            }
        }
        return size;
    }

    public int partTwo() {
        int size = Integer.MAX_VALUE;
        Directory root = parseFile();
        Map<String, Integer> dirSizes = dirSizes(root);
        int total_space = 70000000;
        int space_needed = 30000000;
        int space_left = total_space - dirSizes.get("/");

        if (space_left > space_needed) {
            return -1;
        }

        for (Integer key : dirSizes.values()) {
            if (key >= space_needed - space_left) {
                size = Math.min(size, key);
            }
        }
        return size;
    }

    public static void main(String[] args) {
        Main main = new Main();
        Directory root = main.parseFile();
        System.out.println(main.partOne());
        System.out.println(main.partTwo());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            root.print(writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}