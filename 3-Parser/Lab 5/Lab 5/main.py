from Grammar import Grammar

if __name__ == '__main__':
    g = Grammar()
    g.load_from_file("g2.in")
    print(str(g))
    if g.check_if_CFG():
        print("The grammar is a CFG")
    else:
        print("The grammar is not a CFG")

    g.get_productions_for_non_terminal()
