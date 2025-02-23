from Grammar import Grammar
from State import State
from ProductionItem import ProductionItem


class Parser:
    def __init__(self, grammar: Grammar):
        self.grammar = grammar
        self.canonical_collection = list()

    @staticmethod
    def is_item_in_closure(item, closure):
        for closure_item in closure:
            if item.lhs == closure_item.lhs and \
                    item.rhs == closure_item.rhs and \
                    item.dot_position == closure_item.dot_position:
                return True
        return False

    def closure(self, items: list) -> State:
        current_closure = items.copy()

        finished = False
        while not finished:
            old_closure = current_closure.copy()
            for closure_item in current_closure:
                if closure_item.dot_position < len(closure_item.rhs) and \
                        closure_item.rhs[closure_item.dot_position] in self.grammar.non_terminals:
                    for production in self.grammar.productions[closure_item.rhs[closure_item.dot_position]]:
                        if not self.is_item_in_closure(
                                ProductionItem(
                                    closure_item.rhs[closure_item.dot_position],
                                    production,
                                    0
                                ), current_closure):
                            current_closure.append(
                                ProductionItem(
                                    closure_item.rhs[closure_item.dot_position],
                                    production,
                                    0
                                )
                            )
            if current_closure == old_closure:
                finished = True

        return State(items, current_closure)

    def goto(self, state: State, symbol: str) -> State:
        items_for_symbol = []
        for item in state.closure:
            if item.dot_position < len(item.rhs) \
                    and item.rhs[item.dot_position] == symbol:
                items_for_symbol.append(ProductionItem(item.lhs, item.rhs, item.dot_position + 1))

        for existing_state in self.canonical_collection:
            if existing_state.closure_items == items_for_symbol:
                return existing_state

        return self.closure(items_for_symbol)

    def create_canonical_collection(self):
        self.canonical_collection = [
            self.closure(
                [ProductionItem(
                    self.grammar.start_symbol,
                    self.grammar.productions[self.grammar.start_symbol][0],
                    0
                )]
            )
        ]
        index = 0
        while index < len(self.canonical_collection):
            state = self.canonical_collection[index]
            symbols = state.get_symbols_after_dot()
            for symbol in symbols:
                new_state = self.goto(state, symbol)
                if new_state not in self.canonical_collection:
                    self.canonical_collection.append(new_state)
            index += 1