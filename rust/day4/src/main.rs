mod lib;
use std::fs;

const FILE_NAME: &str = "input.txt";

fn parse_file() -> Vec<String> {
    let contents = fs::read_to_string(FILE_NAME).expect("Something went wrong reading the file");

    let mut lines: Vec<String> = Vec::new();
    for line in contents.lines() {
        lines.push(line.to_string());
    }

    lines
}

fn fully_contais(pair1: (i32, i32), pair2: (i32, i32)) -> bool {
    if ((pair1.0 <= pair2.0) && (pair1.1 >= pair2.1))
        || ((pair1.0 >= pair2.0) && (pair1.1 <= pair2.1))
    {
        return true;
    }
    false
}

fn overlap(pair1: (i32, i32), pair2: (i32, i32)) -> bool {
    if (pair1.0 <= pair2.0) && (pair2.0 <= pair1.1) {
        return true;
    } else if (pair2.0 <= pair1.0) && (pair1.0 <= pair2.1) {
        return true;
    }
    false
}

fn turn_to_tuple(string: &str) -> (i32, i32) {
    let split = string.split("-").collect::<Vec<&str>>();
    (split[0].parse().unwrap(), split[1].parse().unwrap())
}

fn part_one() -> i32 {
    let lines = parse_file();
    let mut count = 0;
    for line in lines.iter() {
        let split = line.split(",").collect::<Vec<&str>>();
        let pair1: (i32, i32) = turn_to_tuple(split[0]);
        let pair2: (i32, i32) = turn_to_tuple(split[1]);
        if fully_contais(pair1, pair2) {
            count += 1;
        }
    }
    count
}

fn part_two() -> i32 {
    let lines = parse_file();
    let mut count = 0;
    for line in lines.iter() {
        let split = line.split(",").collect::<Vec<&str>>();
        let pair1: (i32, i32) = turn_to_tuple(split[0]);
        let pair2: (i32, i32) = turn_to_tuple(split[1]);
        if overlap(pair1, pair2) {
            count += 1;
        }
    }
    count
}

fn main() {
    println!("Part One: {}", part_one());
    println!("Part Two: {}", part_two());
}
