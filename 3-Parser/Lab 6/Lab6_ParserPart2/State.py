import itertools


class State:
    id = itertools.count()

    def __init__(self, closure_items, closure):
        self.id = next(self.id)
        self.closure_items = closure_items
        self.closure = closure

    def get_symbols_after_dot(self):
        symbols = []
        for item in self.closure:
            if item.dot_position < len(item.rhs):
                symbols.append(item.rhs[item.dot_position])
        return symbols

    def __eq__(self, other):
        return self.closure_items == other.closure_items

    def __str__(self):
        result = f"state{self.id} = closure({{"

        # Adjusted string formatting
        result += ", ".join(str(item) for item in self.closure_items) + "}) = {"
        result += ", ".join(str(item) for item in self.closure) + "}"

        return result