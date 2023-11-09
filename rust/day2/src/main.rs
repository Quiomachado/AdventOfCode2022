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

#[derive(PartialEq, Eq, Clone)]
enum Move {
    ROCK,
    PAPER,
    SCISSORS,
}

impl Move {
    fn beats(&self) -> Move {
        match self {
            Move::ROCK => Move::SCISSORS,
            Move::PAPER => Move::ROCK,
            Move::SCISSORS => Move::PAPER,
        }
    }

    fn loses_to(&self) -> Move {
        match self {
            Move::ROCK => Move::PAPER,
            Move::PAPER => Move::SCISSORS,
            Move::SCISSORS => Move::ROCK,
        }
    }

    fn from_str(s: &str) -> Move {
        match s {
            "A" | "X" => Move::ROCK,
            "B" | "Y" => Move::PAPER,
            "C" | "Z" => Move::SCISSORS,
            _ => panic!("Invalid move"),
        }
    }

    fn move_score(&self) -> i32 {
        match self {
            Move::ROCK => 1,
            Move::PAPER => 2,
            Move::SCISSORS => 3,
        }
    }
}

fn calc_outcome(my_move: Move, opp_move: Move) -> i32 {
    if my_move.loses_to() == opp_move {
        return 0;
    } else if my_move.beats() == opp_move {
        return 6;
    } else {
        return 3;
    }
}

fn part_one() -> i32 {
    let lines = parse_file();
    let mut total_score = 0;

    for line in lines.iter() {
        let opp_move = Move::from_str(&line[..1]);
        let my_move = Move::from_str(&line[2..]);
        total_score += my_move.move_score();
        total_score += calc_outcome(my_move, opp_move);
    }
    total_score
}

fn part_two() -> i32 {
    let moves = parse_file();
    let mut total_score = 0;

    for line in moves.iter() {
        let opp_move = Move::from_str(&line[..1]).clone();
        let outcome = &line[2..];
        let my_move;
        match outcome {
            "X" => my_move = opp_move.beats(),
            "Y" => my_move = opp_move.clone(),
            "Z" => my_move = opp_move.loses_to(),
            _ => panic!("Invalid outcome"),
        }
        total_score += my_move.move_score();
        total_score += calc_outcome(my_move, opp_move);
    }

    total_score
}

fn main() {
    println!("Part one: {}", part_one());
    println!("Part two: {}", part_two());
}
