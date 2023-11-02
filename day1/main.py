def getMaxCalories(filename: str) -> int:
    elf_calories = process_calories(filename)
    current_max = 0
    for elf in elf_calories:
        if current_max <= sum(elf):
            current_max = sum(elf)
    return current_max


def getTopThree(filename: str) -> int:
    elf_calories = process_calories(filename)
    summed_calories = [sum(elf) for elf in elf_calories]
    return sum(sorted(summed_calories, reverse=True)[:3])


def process_calories(filename: str) -> list[list[int]]:
    with open(filename) as f:
        elves = f.read().split("\n\n")
    return [list(map(int, elf.strip().split("\n"))) for elf in elves]


if __name__ == "__main__":
    print("Part 1")
    print(getMaxCalories("input.txt"))
    print("Part 2")
    print(getTopThree("input.txt"))
