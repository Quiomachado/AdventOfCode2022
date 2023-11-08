use std::env;
use std::fs;

fn main() {
    let args: String = env::args().nth(1).expect("No argument given");
    println!("Reading file: {}", args);

    let contents = match fs::read_to_string(args) {
        Ok(contents) => contents,
        Err(args) => panic!("Error reading file: {}", args),
    };
    let contents = contents.lines();
    let mut count = 0;
    let mut counts: Vec<i32> = Vec::new();
    for line in contents {
        if line == "" {
            counts.push(count);
            count = 0;
            continue;
        }
        count += line.trim().parse::<i32>().unwrap();
    }
    println!("Max: {}", match counts.iter().max() {
        Some(max) => max,
        None => panic!("No max found"),
    });
    counts.sort_by(|a, b| b.cmp(a));
    println!("Top3: {}", counts.iter().take(3).sum::<i32>());
}
