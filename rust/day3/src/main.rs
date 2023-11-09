use std::fs;

const FILE_NAME: &str = "input.txt";

fn parse_file() -> Vec<String> {
    let contents = fs::read_to_string(FILE_NAME).expect("Something went wrong reading the file");
    let mut lines = Vec::new();
    for line in contents.lines() {
        lines.push(line.to_string());
    }
    return lines;
}

fn get_priority(c: char) -> i32{
    if c.is_uppercase() {
        return (c as i32) - ('A' as i32) + 27;
    } else {
        return (c as i32) - ('a' as i32) + 1;
    }
}

fn check_duplicates(s: &str) -> char {
    let s1 = &s[..s.len()/2];
    let s2 = &s[s.len()/2..];
    let mut seen_chars = Vec::new();

    for c in s1.chars() {
        if s2.contains(c) {
            return c;
        }
        seen_chars.push(c);
    }
    return ' ';
}

fn part_one() -> i32 {
    let lines = parse_file();
    let mut count = 0;
    for line in lines.iter() {
        count += get_priority(check_duplicates(line));
    }
    count
}

fn part_two() -> i32 {
    let lines = parse_file();
    let mut count = 0;
    for line in lines.chunks(3) {
        for c in line[0].chars() {
            if line[1].contains(c) && line[2].contains(c) {
                count += get_priority(c);
                break;
            }
        }
    }
    count
}

fn main() {
    println!("Part One: {}", part_one());
    println!("Part Two: {}", part_two());
}
