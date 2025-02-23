import itertools
from enum import Enum

class ACTION(Enum):
    SHIFT = 1
    ACCEPT = 2
    REDUCE = 3
    REDUCE_REDUCE_CONFLICT = 4
    SHIFT_REDUCE_CONFLICT = 5


class State:
    id = itertools.count()

    def __init__(self, closure_items, closure, enrichedSymbol):
        self.action = None
        self.id = next(self.id)
        self.closure_items = closure_items
        self.closure = closure
        self.set_action(enrichedSymbol)

    def set_action(self, enrichedSymbol):
        if len(self.closure) == 1 and \
                len(self.closure[0].rhs) == self.closure[0].dot_position and \
                self.closure[0].lhs == enrichedSymbol:
            self.action = ACTION.ACCEPT
        elif len(self.closure) == 1 and self.closure[0].dot_position == len(self.closure[0].rhs):
            self.action = ACTION.REDUCE
        elif len(self.closure) != 0 and self.check_all_not_dot_end():
            self.action = ACTION.SHIFT
        else:
            if len(self.closure) > 1 and self.check_all_dot_end():
                self.action = ACTION.REDUCE_REDUCE_CONFLICT
            else:
                self.action = ACTION.SHIFT_REDUCE_CONFLICT

    def check_all_not_dot_end(self) -> bool:
        for c in self.closure:
            if len(c.rhs) <= c.dot_position:
                return False
        return True

    def check_all_dot_end(self) -> bool:
        for c in self.closure:
            if len(c.rhs) > c.dot_position:
                return False
        return True

    def get_all_symbols_after_dot(self):
        result = []
        for item in self.closure:
            if item.dot_position < len(item.rhs):
                result.append(item.rhs[item.dot_position])
        return result

    def __eq__(self, other):
        return self.closure_items == other.closure_items

    def __str__(self):
        result = "s" + str(self.id) + " = closure({"
        for item in self.closure_items:
            result += str(item) + ", "
        result = result[:-2] + "}) = {"
        for item in self.closure:
            result += str(item) + ", "
        result = result[:-2] + "}"
        return result

class Connection:
    def __init__(self, starting_state: State, final_state: State, symbol: str):
        self.starting_state = starting_state
        self.final_state = final_state
        self.symbol = symbol

    def __str__(self):
        return "Starting state: " + str(self.starting_state) + " " + \
                "Final state: " + str(self.final_state) + " " + \
                "Symbol: " + str(self.symbol)


class ProductionItem:
    def __init__(self, lhs: str, rhs: list, dot_position: int):
        self.lhs = lhs
        self.rhs = rhs
        self.dot_position = dot_position

    def __eq__(self, other):
        return (self.lhs == other.lhs and
                self.rhs == other.rhs and
                self.dot_position == other.dot_position)

    def __str__(self):
        result = f"[{self.lhs} -> "
        result += " ".join(
            (". " if idx == self.dot_position else "") + symbol
            for idx, symbol in enumerate(self.rhs)
        )
        if self.dot_position == len(self.rhs):
            result += " ."
        return result.strip() + "]"