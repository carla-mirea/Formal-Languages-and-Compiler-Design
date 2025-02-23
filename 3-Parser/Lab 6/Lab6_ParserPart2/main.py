from Grammar import Grammar
from Parser import Parser

if __name__ == '__main__':
    grammar = Grammar()
    grammar.load_from_file("g3.in")
    print(str(grammar))
    grammar.apply_enhancement()
    parser = Parser(grammar)
    parser.create_canonical_collection()

    # if g.check_if_CFG():
    #     print("The grammar is a CFG")
    # else:
    #     print("The grammar is not a CFG")
    #
    # g.get_productions_for_non_terminal()

    for state in parser.canonical_collection:
        print(state)
