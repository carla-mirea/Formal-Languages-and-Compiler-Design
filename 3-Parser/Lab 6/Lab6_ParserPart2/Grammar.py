class Grammar:
    EPSILON = "epsilon"
    STARTING_SYMBOL = "S'"


    def __init__(self, is_enhanced=False):
        self.non_terminals = []
        self.terminals = []
        self.start_symbol = ""
        self.productions = {}
        self.is_enhanced = is_enhanced

    @staticmethod
    def __extract_symbols(line: str):
        # Extract what comes after the '='
        return line.strip().split(' ')[2:]

    def load_from_file(self, file_name: str):
        with open(file_name) as file:
            self.non_terminals = self.__extract_symbols(file.readline())
            self.terminals = self.__extract_symbols(file.readline())
            self.start_symbol = self.__extract_symbols(file.readline())[0]

            file.readline()  # Skip the 'P =' line

            rules = {}
            for line in file:
                parts = line.strip().split('->')
                lhs = parts[0].strip()
                rhs = parts[1].strip().split()

                if lhs in rules:
                    rules[lhs].append(rhs)
                else:
                    rules[lhs] = [rhs]

            self.productions = rules

    def check_if_CFG(self):
        has_starting_symbol = False
        for lhs in self.productions.keys():
            if lhs == self.start_symbol:
                has_starting_symbol = True
            if lhs not in self.non_terminals:
                return False

        if not has_starting_symbol:
            return False

        for rhs_list in self.productions.values():
            for rhs in rhs_list:
                for value in rhs:
                    if value not in self.non_terminals and value not in self.terminals and value != Grammar.EPSILON:
                        print(value)
                        return False
        return True

    def get_productions_for_non_terminal(self):
        non_terminal = input("Enter a non-terminal: ").strip()
        if non_terminal not in self.non_terminals:
            print(f"{non_terminal} is not a valid non-terminal.")
            return

        if non_terminal not in self.productions:
            print(f"No productions found for {non_terminal}.")
            return

        print(f"Productions for {non_terminal}:")
        for rhs in self.productions[non_terminal]:
            print(f"{non_terminal} -> {' '.join(rhs)}")

    # methods for Part 2: Deliverables

    # validate whether a grammar is enhanced
    def check_enhanced(self):
        # ensure the start symbol has exactly one production
        if len(self.productions[self.start_symbol]) != 1:
            return False

        # ensure the start symbol does not appear on the RHS of any production
        for rhs_list in self.productions.values():
            for rhs in rhs_list:
                if self.start_symbol in rhs:
                    return False

        return True

    # if needed, introduce a new starting symbol
    def apply_enhancement(self):
        if not self.is_enhanced:
            # add a new non-terminal symbol 'S' for the new starting point
            self.non_terminals.append(Grammar.STARTING_SYMBOL)

            # create a new production rule S' -> S to start the enhanced grammar
            self.productions[Grammar.STARTING_SYMBOL] = [[self.start_symbol]]

            # change the starting symbol
            self.start_symbol = Grammar.STARTING_SYMBOL

            self.is_enhanced = True

    def __str__(self):
        output = f"N = {str(self.non_terminals)}\n"
        output += f"E = {str(self.terminals)} \n"
        output += f"S = {str(self.start_symbol)} \n"
        output += f"P = {str(self.productions)} \n"
        return output