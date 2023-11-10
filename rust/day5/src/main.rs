use std::{
    collections::{HashMap, VecDeque},
    fs,
};

const INPUT: &str = "input.txt";

fn parse_input() -> (HashMap<i32, VecDeque<char>>, Vec<String>) {
    let contents = fs::read_to_string(INPUT).expect("Something went wrong reading the file");
    let contents = contents.split_at(contents.find("\n\n").unwrap());
    let mut stacks_list = contents.0.split("\n").collect::<Vec<&str>>();
    let mut stacks: HashMap<i32, VecDeque<char>> = HashMap::new();
    for c in stacks_list[stacks_list.len() - 1].chars() {
        if c.is_digit(10) {
            stacks.insert(c.to_digit(10).unwrap() as i32, VecDeque::new());
        }
    }
    stacks_list.pop();
    for stack in stacks_list {
        for c in stack.chars().enumerate() {
            if c.1.is_alphabetic() {
                stacks
                    .get_mut(&((c.0 as i32 + 3) / 4))
                    .unwrap()
                    .push_front(c.1);
            }
        }
    }
    let instructions = contents
        .1
        .split("\n")
        .map(|s| s.trim().to_string())
        .collect::<Vec<String>>();
    let instructions = instructions
        .iter()
        .filter(|s| !s.is_empty())
        .cloned()
        .collect::<Vec<String>>();
    (stacks, instructions)
}

fn part_one() -> String {
    let (mut stacks, instructions) = parse_input();
    let mut result = String::new();
    for instruction in instructions {
        let count = instruction.split(" ").collect::<Vec<&str>>()[1]
            .parse::<i32>()
            .unwrap();
        let from = instruction.split(" ").collect::<Vec<&str>>()[3]
            .parse::<i32>()
            .unwrap();
        let to = instruction.split(" ").collect::<Vec<&str>>()[5]
            .parse::<i32>()
            .unwrap();
        for _ in 0..count {
            let c = stacks.get_mut(&from).unwrap().pop_back().unwrap();
            stacks.get_mut(&to).unwrap().push_back(c);
        }
    }
    for stack in 1..=stacks.len() {
        let c = stacks.get_mut(&(stack as i32)).unwrap().back().unwrap();
        result.push(*c);
    }
    result
}

fn part_two() -> String {
    let (mut stacks, instructions) = parse_input();
    let mut result = String::new();
    for instruction in instructions {
        let count = instruction.split(" ").collect::<Vec<&str>>()[1]
            .parse::<i32>()
            .unwrap();
        let from = instruction.split(" ").collect::<Vec<&str>>()[3]
            .parse::<i32>()
            .unwrap();
        let to = instruction.split(" ").collect::<Vec<&str>>()[5]
            .parse::<i32>()
            .unwrap();
        let size = stacks.get_mut(&from).unwrap().len() as i32;
        for _ in 0..count {
            let c = stacks.get_mut(&from).unwrap().remove((size - count) as usize).unwrap();
            stacks.get_mut(&to).unwrap().push_back(c);
        }
    }
    for stack in 1..=stacks.len() {
        let c = stacks.get_mut(&(stack as i32)).unwrap().back().unwrap();
        result.push(*c);
    }
    result
}

fn main() {
    println!("Part One: {}", part_one());
    println!("Part Two: {}", part_two());
}
