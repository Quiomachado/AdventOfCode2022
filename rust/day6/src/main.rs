use std::{fs, collections::HashSet};

const FILE_NAME: &str = "input.txt";

fn parse_file() -> String {
    let content = fs::read_to_string(FILE_NAME).expect("Something went wrong while reading the file");
    for line in content.lines() {
        return line.to_string();
    }
    return "".to_string();
}

fn has_duplicate(input: &str) -> bool {
    let mut set = HashSet::new();
    for c in input.chars() {
        if set.contains(&c) {
            return true;
        }
        set.insert(c);
    }
    return false;
}

fn first_unique(input: String, step: i32) -> i32 {
    for i in 0..input.len() {
        if !has_duplicate(&input[i..i + (step as usize)]) {
            return i as i32 + step;
        }
    }
    return -1;
}

fn part_one() -> i32 {
    let input = parse_file();
    return first_unique(input, 4);
}

fn part_two() -> i32 {
    let input = parse_file();
    return first_unique(input, 14);
}

fn main() {
    println!("{}", part_one());
    println!("{}", part_two());
}
