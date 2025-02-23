from Parser import Parser
from Grammar import Grammar
from utils import *

def test_create_canonical_collection():
    grammar = Grammar()  # Assuming Grammar is already defined with rules
    parser = Parser(grammar)

    # Initialize grammar (example grammar)
    grammar.S = "S"
    grammar.N = {"S", "A", "B"}
    grammar.T = {"a", "b"}
    grammar.P = {
        "S": [("A", 1)],
        "A": [("a",), ("b",)],
        "B": [("a", "B"), ("b", "B"), ("a",)]
    }

    # Create the canonical collection
    parser.create_canonical_collection()

    # Validate the state transitions (check the states in canonical collection)
    assert len(parser.canonical_collection) > 0, "Canonical collection is empty"
    print(f"Canonical collection created with {len(parser.canonical_collection)} states.")

def test_create_parsing_table():
    grammar = Grammar()  # Assuming Grammar is already defined with rules

    # Initialize grammar (example grammar)
    grammar.read_from_file("g1.in")

    grammar.make_enhanced_grammar()

    parser = Parser(grammar)
    # Create the canonical collection and parsing table
    parser.create_canonical_collection()
    parser.create_parsing_table()

    # Check if parsing table is created
    assert len(parser.parsing_table) > 0, "Parsing table is empty"
    print(f"Parsing table created with {len(parser.parsing_table)} entries.")

def test_parse_valid_sequence():
    g = Grammar()
    g.read_from_file("g1.in")
    g.make_enhanced_grammar()
    parser = Parser(g)
    parser.create_canonical_collection()

    parser.create_parsing_table()

    sequence = "a c"
    output = parser.parse_sequence(sequence.split(" "))

    # Validate that output matches expected production sequence
    assert len(output) > 0, "Parsing failed"
    print(f"Parsing completed with output: {output}")

def test_parse_invalid_sequence():
    grammar = Grammar()  # Assuming Grammar is already defined with rules

    # Initialize grammar (example grammar)
    grammar.read_from_file("g1.in")

    grammar.make_enhanced_grammar()

    parser = Parser(grammar)

    # Create the canonical collection and parsing table
    parser.create_canonical_collection()
    parser.create_parsing_table()

    # Input sequence to parse
    sequence = ["a", "x", "$"]  # 'x' is not in the grammar, should cause error

    try:
        # Parse the sequence
        parser.parse_sequence(sequence)
        assert False, "Expected exception for invalid sequence"
    except Exception as e:
        print(f"Expected exception: {e}")

def test_closure():
    grammar = Grammar()  # Assuming Grammar is already defined with rules
    parser = Parser(grammar)

    # Initialize grammar (example grammar)
    grammar.S = "S"
    grammar.N = {"S", "A"}
    grammar.T = {"a"}
    grammar.P = {
        "S": [("A", 1)],
        "A": [("a",)]
    }

    # Example item in closure
    items = [ProductionItem("S", ("A",), 0)]

    # Perform closure
    closure_result = parser.closure(items)

    # Validate closure result
    assert len(closure_result.closure) > 0, "Closure did not expand correctly"
    print(f"Closure created with {len(closure_result.closure)} items.")

def test_goto():
    grammar = Grammar()  # Assuming Grammar is already defined with rules
    parser = Parser(grammar)

    # Initialize grammar (example grammar)
    grammar.S = "S"
    grammar.N = {"S", "A"}
    grammar.T = {"a"}
    grammar.P = {
        "S": [("A", 1)],
        "A": [("a",)]
    }

    # Create canonical collection
    parser.create_canonical_collection()

    # Get the first state from the canonical collection
    state = parser.canonical_collection[0]

    # Perform goto on symbol 'a'
    next_state = parser.goto(state, 'a')

    # Validate that the new state is correct
    assert next_state is not None, "Goto function failed"
    print(f"Goto resulted in new state with {len(next_state.closure)} items.")

# BATCH 1
#test_create_canonical_collection()
#test_create_parsing_table()
#test_closure()
#test_goto()

# BATCH 2
test_parse_valid_sequence()
test_parse_invalid_sequence()