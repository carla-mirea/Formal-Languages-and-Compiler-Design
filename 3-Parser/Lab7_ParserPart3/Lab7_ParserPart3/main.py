from Grammar import Grammar
from Parser import Parser
from ParserOutput import ParserOutput

def parse_pif_file(pif_file):
    sequence = []
    with open(pif_file, "r") as f:
        for line in f:
            token = line.split(":")[0].strip()
            if token != "":
                sequence.append(token)
    return sequence

def run_parser(grammar_file, sequence_file, output_file):
    g = Grammar()
    g.read_from_file(grammar_file)
    print("Grammar:")
    print(g)

    g.make_enhanced_grammar()
    print("Enhanced Grammar:")
    print(g)

    parser = Parser(g)
    parser.create_canonical_collection()
    print("\nCanonical Collection:")
    for state in parser.canonical_collection:
        print(state)

    parser.create_parsing_table()
    print("\nParsing Table:")
    print(parser.parsing_table)

    if grammar_file == "g2.in":
        sequence = parse_pif_file(sequence_file)
    else:
        with open(sequence_file, "r") as f:
            sequence = f.read().strip().split()

    print("\nSequence to Parse:", sequence)

    try:
        output_band = parser.parse_sequence(sequence)
        print("\nParsing Output Band:", output_band)

        parserOutput = ParserOutput(output_band, g)
        parserOutput.compute_parsing_tree()
        print("\nParsing Tree:")
        for item in parserOutput.parsing_tree:
            print(item)

        parserOutput.print_to_file(output_file)
        print(f"\nParsing tree saved to {output_file}")
    except Exception as e:
        print(f"\nError during parsing: {e}")

def main():
    print("Choose the grammar to use:")
    print("1. g1.in")
    print("2. g2.in")
    choice = input("Enter 1 or 2: ").strip()

    if choice == "1":
        grammar_file = "g1.in"
        sequence_file = "seq.txt"
        output_file = "out1.txt"
    elif choice == "2":
        grammar_file = "g2.in"
        sequence_file = "PIF.out"
        output_file = "out2.txt"
    else:
        print("Invalid choice. Exiting.")
        return

    run_parser(grammar_file, sequence_file, output_file)

if __name__ == '__main__':
    main()
